package com.nivelais.supinfo.jporating.presentation.ui.dialog.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nivelais.supinfo.jporating.databinding.PinConfirmationDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PinConfirmationDialogFragment() : BottomSheetDialogFragment() {

    /**
     * Import the view model
     */
    private val viewModel: PinConfirmationDialogViewModel by viewModel()

    /**
     * View Binding, relation with the layout
     */
    private lateinit var binding: PinConfirmationDialogBinding

    /**
     * The argument required to launch this fragment
     */
    private val arguments: PinConfirmationDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PinConfirmationDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // Listen the action live data
        viewModel.actionLive.observe(viewLifecycleOwner) { action ->
            when(action) {
                PinConfirmationDialogViewModel.ACTION.PIN_ERROR -> binding.inputConfirmationDialogPin.error = "Code PIN invalide"
                PinConfirmationDialogViewModel.ACTION.CONTINUE -> {
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(arguments.resultKey, true)
                    dismiss()
                }
                else -> dismiss()
            }
        }

        // No button clicked, we cancel the action
        binding.buttonConfirmationDialogNo.setOnClickListener {
            dismiss()
        }

        // Yes button clicked, we check the pin code validity and conitnu
        binding.buttonConfirmationDialogYes.setOnClickListener {
            viewModel.checkPinCode(binding.inputConfirmationDialogPin.editText?.text.toString())
        }
    }
}