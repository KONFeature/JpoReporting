package com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.usecases.AnswerQuestionUseCase
import com.nivelais.supinfo.domain.usecases.FinishInterrogationUseCase
import com.nivelais.supinfo.domain.usecases.GetQuestionUseCase
import com.nivelais.supinfo.domain.usecases.ResetAnswerUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AnsweringInterrogationViewModel(
    private val getQuestionUseCase: GetQuestionUseCase,
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
    val answeredCountLive = MutableLiveData(0)

    /**
     * Live data for all the number of questions answered
     */
    val needResetLive = MutableLiveData<Boolean>(false)

    /*
    * Job and context for coroutines
    */
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    init {
        // Load questions at init
        getQuestionUseCase.invoke(scope, Unit) {
            questionsLive.postValue(it.data?.sortedBy { it.position })
        }
    }

    /**
     * Answer to a question
     */
    fun answerQuestion(questionId: Long, rating: Int) {
        scope.launch {
            answerQuestionUseCase.run(
                AnswerQuestionUseCase.Params(
                    questionId, rating
                )
            ).let { answerResult ->
                if(answerResult.isSuccess()) {
                    answeredCountLive.postValue(answerResult.data)
                }
            }
        }
    }

    /**
     * Reset the answer to a question
     */
    fun resetAnswer(questionId: Long) {
        scope.launch {
            resetAnswerUseCase.run(
                ResetAnswerUseCase.Params(
                    questionId
                )
            ).let { resetResult ->
                if(resetResult.isSuccess()) {
                    answeredCountLive.postValue(resetResult.data)
                }
            }
        }
    }

    /**
     * Validate and finish the interrogation
     */
    fun finishInterrogation() {
        scope.launch {
            finishInterrogationUseCase.run(Unit).let {finishResult ->
                // Ask the fragment to reset itself
                needResetLive.postValue(finishResult.isSuccess())
            }
        }
    }

    override fun onCleared() {
        // Cancel all of our pending work
        coroutineContext.cancelChildren()
        super.onCleared()
    }
}