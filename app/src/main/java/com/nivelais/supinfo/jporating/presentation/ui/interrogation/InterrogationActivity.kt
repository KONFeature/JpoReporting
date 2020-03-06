package com.nivelais.supinfo.jporating.presentation.ui.interrogation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.nivelais.supinfo.jporating.R
import com.nivelais.supinfo.jporating.databinding.InterrogationActivityBinding
import com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering.AnsweringInterrogationFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class InterrogationActivity : AppCompatActivity() {

    /**
     * View model import
     */
    private val viewModel: InterrogationViewModel by viewModel()

    /**
     * View Binding, relation with the layout
     */
    private lateinit var binding: InterrogationActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InterrogationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listen to the finish button
        binding.buttonFinish.setOnClickListener {
            viewModel.finishInterrogation()
        }

        initLiveDataObserver()
    }

    /**
     * Create all the observer
     */
    private fun initLiveDataObserver() {
        viewModel.interrogationStateLive.observe(this, Observer {
            binding.textStatus.text =
                getString(R.string.lbl_answering_interrogation_status, it.answersCount, it.questionsCount)
        })

        viewModel.interrogationEventLive.observe(this, Observer {
            it.getContentIfNotHandled()?.let {event ->
                when(event) {
                    InterrogationViewModel.InterrogationFinishEvent.LOCK_FINISH -> binding.buttonFinish.isEnabled = false
                    InterrogationViewModel.InterrogationFinishEvent.UNLOCK_FINISH -> binding.buttonFinish.isEnabled = true
                    InterrogationViewModel.InterrogationFinishEvent.RESET -> {
                        // Disable finish button
                        binding.buttonFinish.isEnabled = false

                        // Refresh fragment
                        findNavController(R.id.nav_host_fragment).apply {
                                navigate(AnsweringInterrogationFragmentDirections.actionAnsweringInterrogationFragmentSelf())
                            }
                    }
                }
            }
        })
    }
}