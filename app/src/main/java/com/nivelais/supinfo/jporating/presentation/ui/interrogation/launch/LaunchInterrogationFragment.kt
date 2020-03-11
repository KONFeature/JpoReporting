package com.nivelais.supinfo.jporating.presentation.ui.interrogation.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nivelais.supinfo.jporating.databinding.LaunchInterrogationFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchInterrogationFragment : Fragment() {

    companion object {
        private const val CONFIRMATION_SEND_KEY = "sendConfirmLive"
        private const val CONFIRMATION_DELETE_KEY = "deleteConfirmLive"
    }

    /**
     * Import the view model
     */
    private val viewModel: LaunchInterrogationViewModel by viewModel()

    /**
     * View Binding, relation with the layout
     */
    private lateinit var binding: LaunchInterrogationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LaunchInterrogationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Listen to pin confirmation result
        findNavController().currentBackStackEntry?.savedStateHandle?.apply {
            getLiveData<Boolean>(CONFIRMATION_SEND_KEY).observe(viewLifecycleOwner, Observer {
                if (it) viewModel.sendInterrogations()
            })
            getLiveData<Boolean>(CONFIRMATION_DELETE_KEY).observe(viewLifecycleOwner, Observer {
                if (it) viewModel.removeInterrogations()
            })
        }

        // Listen to the launch button
        binding.buttonLaunch.setOnClickListener {
            findNavController().navigate(LaunchInterrogationFragmentDirections.actionLaunchInterrogationFragmentToAnsweringInterrogationFragment())
        }

        // Listen to the send button
        binding.buttonSendReport.setOnClickListener {
            findNavController().navigate(
                LaunchInterrogationFragmentDirections.actionLaunchInterrogationFragmentToPinConfirmationDialogFragment(
                    CONFIRMATION_SEND_KEY
                )
            )
        }

        // Listen to the clear report button
        binding.buttonClearReport.setOnClickListener {
            findNavController().navigate(
                LaunchInterrogationFragmentDirections.actionLaunchInterrogationFragmentToPinConfirmationDialogFragment(
                    CONFIRMATION_DELETE_KEY
                )
            )
        }
    }
}