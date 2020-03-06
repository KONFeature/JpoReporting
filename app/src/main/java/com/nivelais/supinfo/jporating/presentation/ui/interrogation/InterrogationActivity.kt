package com.nivelais.supinfo.jporating.presentation.ui.interrogation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nivelais.supinfo.jporating.databinding.InterrogationActivityBinding
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
    }
}