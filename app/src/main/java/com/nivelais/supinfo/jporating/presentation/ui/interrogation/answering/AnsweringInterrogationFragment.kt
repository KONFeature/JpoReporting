package com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.nivelais.supinfo.domain.entities.QuestionEntity
import com.nivelais.supinfo.jporating.R
import com.nivelais.supinfo.jporating.databinding.AnsweringInterrogationFragmentBinding
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

        // Listen to the validate button

        // Listen to the list of questions to show
        viewModel.questionsLive.observe(viewLifecycleOwner) { questions ->
            // Init the recycler view if needed
            if(binding.listQuestions.adapter == null) {
                initListQuestion(questions)
            }

            // Update the text status
//            binding.textStatus.text =
//                getString(R.string.lbl_answering_interrogation_status, 0, questions?.count() ?: 0)
        }

        // Listen to the count of answered questions
        viewModel.answeredCountLive.observe(viewLifecycleOwner) {answerCount ->
            // Update the text status
//            binding.textStatus.text =
//                getString(R.string.lbl_answering_interrogation_status, answerCount, binding.listQuestions.adapter?.itemCount?:0)
//            // If all the questions are answered we unlock the finish button
//            binding.buttonFinish.isEnabled = answerCount == binding.listQuestions.adapter?.itemCount
        }

        // Listen to the reset indicator
        viewModel.needResetLive.observe(viewLifecycleOwner) {
            // TODO : Find a way to close the fragment
            // TODO : Maybe finish button in activity (with progress) ?
        }
    }

    /**
     * Init the recycler view with the list of questions
     */
    private fun initListQuestion(questions: List<QuestionEntity>) {
        // Bind all the questions to the view
        binding.listQuestions.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AnsweringQuestionsAdapter(questions,
                { questionId, rating ->
                    viewModel.answerQuestion(questionId, rating)
                },
                { questionId ->
                    viewModel.resetAnswer(questionId)
                })
        }
    }
}