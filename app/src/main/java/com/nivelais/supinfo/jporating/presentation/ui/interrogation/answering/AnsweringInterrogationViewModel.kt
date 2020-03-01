package com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.usecases.GetQuestionUseCase
import com.nivelais.supinfo.domain.usecases.LaunchInterrogationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class AnsweringInterrogationViewModel(
    val getQuestionUseCase: GetQuestionUseCase
) : ViewModel() {

    /**
     * Live data for all the questions to show
     */
    val questionsLive = MutableLiveData<Set<QuestionEntity>?>()

    /*
    * Job and context for coroutines
    */
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    init {
        getQuestionUseCase.invoke(scope, Unit) {
            questionsLive.postValue(it.data)
        }
    }
}