package com.nivelais.supinfo.jporating.presentation.ui.dialog.confirmation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nivelais.supinfo.jporating.databinding.AnsweringInterrogationFragmentBinding
import com.nivelais.supinfo.jporating.databinding.PinConfirmationDialogBinding

class PinConfirmationDialogViewModel() : ViewModel() {

    companion object {
        private const val VALID_PIN_CODE = "1313"
    }

    /**
     * Live data for the actions we have to make on the UI
     */
    val actionLive = MutableLiveData<ACTION>()


    /**
     * Check the pin code match the one we want
     */
    fun checkPinCode(pinCode: String) {
        if(pinCode == VALID_PIN_CODE) actionLive.postValue(ACTION.CONTINUE)
        else actionLive.postValue(ACTION.PIN_ERROR)
    }

    enum class ACTION {
        PIN_ERROR,
        CONTINUE
    }

}