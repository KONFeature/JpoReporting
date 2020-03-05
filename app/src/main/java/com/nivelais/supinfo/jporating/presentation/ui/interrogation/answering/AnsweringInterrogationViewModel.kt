package com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.usecases.AnswerQuestionUseCase
import com.nivelais.supinfo.domain.usecases.GetQuestionUseCase
import com.nivelais.supinfo.domain.usecases.ResetAnswerUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AnsweringInterrogationViewModel(
    private val getQuestionUseCase: GetQuestionUseCase,
    private val answerQuestionUseCase: AnswerQuestionUseCase,
    private val resetAnswerUseCase: ResetAnswerUseCase
) : ViewModel() {

    /**
     * Live data for all the questions to show
     */
    val questionsLive = MutableLiveData<List<QuestionEntity>>()

    /**
     * Live data for all the number of questions answered
     */
    val answeredCountLive = MutableLiveData<Int>(0)

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
}