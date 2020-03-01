package com.nivelais.supinfo.jporating.presentation.ui.interrogation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nivelais.supinfo.jporating.databinding.InterrogationActivityBinding

class InterrogationActivity : AppCompatActivity() {

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