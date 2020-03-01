package com.nivelais.supinfo.jporating.presentation.ui.interrogation.launch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nivelais.supinfo.jporating.databinding.LaunchInterrogationFragmentBinding
import kotlinx.android.synthetic.main.interrogation_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchInterrogationFragment : Fragment() {

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.buttonLaunch.setOnClickListener {
            viewModel.launchInterrogation(
                binding.inputName.editText?.toString(),
                null
            )
            nav_host_fragment.findNavController().navigate(
                LaunchInterrogationFragmentDirections.actionLaunchInterrogationFragmentToAnsweringInterrogationFragment()
            )
        }
    }

}