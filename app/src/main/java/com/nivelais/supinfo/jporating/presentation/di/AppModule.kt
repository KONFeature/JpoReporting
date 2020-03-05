package com.nivelais.supinfo.jporating.presentation.di

import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.domain.repositories.QuestionRepository
import com.nivelais.supinfo.domain.usecases.AnswerQuestionUseCase
import com.nivelais.supinfo.domain.usecases.GetQuestionUseCase
import com.nivelais.supinfo.domain.usecases.ResetAnswerUseCase
import com.nivelais.supinfo.jporating.data.db.ObjectBox
import com.nivelais.supinfo.jporating.data.repositories.InterrogationRepositoryImpl
import com.nivelais.supinfo.jporating.data.repositories.QuestionRepositoryImpl
import com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering.AnsweringInterrogationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module pour la base de donée
 */
val objectboxModule = module {
    single { ObjectBox.init(androidContext()) }
}

/**
 * Module for all the repository implementation
 */
val repositoryModule = module {
    single { InterrogationRepositoryImpl(get()) as InterrogationRepository }
    single { QuestionRepositoryImpl(get()) as QuestionRepository }
}

/**
 * Module for all the use case
 */
val useCasesModule = module {
    single { GetQuestionUseCase(get()) }
    single { AnswerQuestionUseCase(get()) }
    single { ResetAnswerUseCase(get()) }
}

/**
 * Module pour les view model
 */
val viewModelModule = module {
    viewModel { AnsweringInterrogationViewModel(get(), get(), get()) }
}