package com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.usecases.AnswerQuestionUseCase
import com.nivelais.supinfo.domain.usecases.FinishInterrogationUseCase
import com.nivelais.supinfo.domain.usecases.LaunchInterrogationUseCase
import com.nivelais.supinfo.domain.usecases.ResetAnswerUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

class AnsweringInterrogationViewModel(
    private val launchInterrogationUseCase: LaunchInterrogationUseCase,
    private val answerQuestionUseCase: AnswerQuestionUseCase,
    private val resetAnswerUseCase: ResetAnswerUseCase,
    private val finishInterrogationUseCase: FinishInterrogationUseCase
) : ViewModel() {

    /**
     * Live data for all the questions to show
     */
    val questionsLive = MutableLiveData<List<QuestionEntity>>()

    /**
     * Live data for all the number of questions answered
     */
    val stateLive = MutableLiveData(State(0, 0, false))

    /*
    * Job and context for coroutines
    */
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    init {
        // Launch the interrogation at init
        launchInterrogationUseCase.invoke(scope, Unit) { launchResult ->
            if (launchResult.isSuccess()) {
                // Send the questions to the view
                questionsLive.postValue(launchResult.data?.questions?.sortedBy { it.position })

                // Update the question count
                stateLive.apply {
                    value?.questionsCount = launchResult.data?.questions?.size ?: 0
                    postValue(value)
                }

                // Listen to the answer count
                listenToAnswerCount(launchResult.data?.answersCountLive)
            } else {
                // TODO : Handle error
            }
        }
    }

    /**
     * Listen to the channel of the answers count
     */
    private fun listenToAnswerCount(countChannel: ReceiveChannel<Int>?) {
        scope.launch {
            countChannel?.consumeEach { answersCount ->

                // Update the number of answered questions and the finish button availability
                stateLive.apply {
                    value?.answersCount = answersCount
                    value?.finishEnabled = value?.answersCount == value?.questionsCount
                    postValue(value)
                }
            }
        }
    }

    /**
     * Answer to a question
     */
    fun answerQuestion(questionId: Long, rating: Int) {
        answerQuestionUseCase.invoke(
            scope,
            AnswerQuestionUseCase.Params(
                questionId, rating
            )
        )
    }

    /**
     * Reset the answer to a question
     */
    fun resetAnswer(questionId: Long) {
        resetAnswerUseCase.invoke(
            scope,
            ResetAnswerUseCase.Params(
                questionId
            )
        )
    }

    /**
     * Validate and finish the interrogation
     */
    fun finishInterrogation() {
        // Close the interrogation
        finishInterrogationUseCase.invoke(scope, Unit)
        // Clear all the remaining task about this interrogation
        coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        // Cancel all of our pending work
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    /**
     * The state of our view
     */
    data class State(
        var questionsCount: Int,
        var answersCount: Int,
        var finishEnabled: Boolean
    )
}