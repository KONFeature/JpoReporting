package com.nivelais.supinfo.jporating.presentation.ui.interrogation.launch

import androidx.lifecycle.ViewModel
import com.nivelais.supinfo.domain.usecases.SendInterrogationsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

class LaunchInterrogationViewModel(
    private val sendInterrogationsUseCase: SendInterrogationsUseCase
) : ViewModel() {

    /*
    * Job and context for coroutines
    */
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    init {
    }

    /**
     * Send all the interrogations done on this device
     */
    fun sendInterrogations() {
        sendInterrogationsUseCase.invoke(scope, Unit)
    }


    override fun onCleared() {
        // Cancel all of our pending work
        coroutineContext.cancelChildren()
        super.onCleared()
    }
}