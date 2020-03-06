package com.nivelais.supinfo.jporating.presentation.ui.interrogation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nivelais.supinfo.domain.usecases.FinishInterrogationUseCase
import com.nivelais.supinfo.domain.usecases.GetQuestionUseCase
import com.nivelais.supinfo.domain.usecases.QuestionAnsweredUseCase
import com.nivelais.supinfo.jporating.presentation.utils.Event
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

class InterrogationViewModel(
    private val getQuestionUseCase: GetQuestionUseCase,
    private val questionAnsweredUseCase: QuestionAnsweredUseCase,
    private val finishInterrogationUseCase: FinishInterrogationUseCase
) : ViewModel() {

    /**
     * Live data for all the number of questions answered
     */
    val interrogationStateLive = MutableLiveData(InterrogationState(0, 0))

    /**
     * Live data for all the number of questions answered
     */
    val interrogationEventLive = MutableLiveData<Event<InterrogationFinishEvent>>()

    /*
    * Job and context for coroutines
    */
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    init {
        // Fetch the number of question
        getQuestionUseCase.invoke(scope, Unit) { getQuestionsResult ->
            // Send the questions count
            interrogationStateLive.apply {
                value?.questionsCount = getQuestionsResult.data?.size ?: 0
                postValue(value)
            }
        }

        // Listen to number of answered questions
        questionAnsweredUseCase.invoke(scope, Unit) { channelResult ->
            scope.launch {
                channelResult.data?.consumeEach { answersCount ->

                    // Update the number of answered questions
                    interrogationStateLive.apply {
                        value?.answersCount = answersCount
                        postValue(value)
                    }

                    // Check if we can unlock or not the finish button
                    if (answersCount == interrogationStateLive.value?.questionsCount)
                        interrogationEventLive.postValue(Event(InterrogationFinishEvent.UNLOCK_FINISH))
                }

            }
        }
    }

    /**
     * Validate and finish the interrogation
     */
    fun finishInterrogation() {
        scope.launch {
            finishInterrogationUseCase.run(Unit).let { finishResult ->
                if (finishResult.isSuccess()) {
                    // Ask the fragment to reset itself
                    interrogationEventLive.postValue(Event(InterrogationFinishEvent.RESET))
                }
            }
        }
    }

    override fun onCleared() {
        // Cancel all of our pending work
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    /**
     * The state of our view
     */
    data class InterrogationState(
        var questionsCount: Int,
        var answersCount: Int
    )

    /**
     * The different event we can send to the view
     */
    enum class InterrogationFinishEvent() {
        LOCK_FINISH,
        UNLOCK_FINISH,
        RESET
    }
}