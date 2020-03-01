package com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.nivelais.supinfo.jporating.R
import com.nivelais.supinfo.jporating.databinding.AnsweringInterrogationFragmentBinding
import com.nivelais.supinfo.jporating.presentation.ui.interrogation.launch.LaunchInterrogationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnsweringInterrogationFragment : Fragment() {

    /**
     * Import the view model
     */
    private val viewModel: AnsweringInterrogationViewModel by viewModel()

    /**
     * View Binding, relation with the layout
     */
    private lateinit var binding: AnsweringInterrogationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AnsweringInterrogationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Listen to the list of questions to show
        viewModel.questionsLive.observe(viewLifecycleOwner) {
            // Bind all the questions to the view
            binding.listQuestions.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = AnsweringQuestionsAdapter(it!!)
            }

            // Update the text status
            binding.textStatus.text = getString(R.string.lbl_answering_interrogation_status, 0, it?.count()?:0)
        }
    }

}