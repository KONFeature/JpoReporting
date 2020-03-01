package com.nivelais.supinfo.jporating.presentation.ui.interrogation.launch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.domain.usecases.GetQuestionUseCase
import com.nivelais.supinfo.domain.usecases.LaunchInterrogationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class LaunchInterrogationViewModel(
    private val launchInterrogationUseCase: LaunchInterrogationUseCase
) : ViewModel() {

    /*
    * Job and context for coroutines
    */
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    /**
     * Start an interrogation
     */
    fun launchInterrogation(name: String?, age: Int?) {
        launchInterrogationUseCase.invoke(
            scope,
            LaunchInterrogationUseCase.Params(name, age)
        ) {
            // If we have the data we post them
        }
    }
}