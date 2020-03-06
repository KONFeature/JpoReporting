package com.nivelais.supinfo.jporating.presentation.di

import com.nivelais.supinfo.domain.repositories.AnswerRepository
import com.nivelais.supinfo.domain.repositories.InterrogationRepository
import com.nivelais.supinfo.domain.repositories.QuestionRepository
import com.nivelais.supinfo.domain.usecases.*
import com.nivelais.supinfo.jporating.data.db.ObjectBox
import com.nivelais.supinfo.jporating.data.repositories.AnswerRepositoryImpl
import com.nivelais.supinfo.jporating.data.repositories.InterrogationRepositoryImpl
import com.nivelais.supinfo.jporating.data.repositories.QuestionRepositoryImpl
import com.nivelais.supinfo.jporating.presentation.ui.interrogation.InterrogationViewModel
import com.nivelais.supinfo.jporating.presentation.ui.interrogation.answering.AnsweringInterrogationViewModel
import com.nivelais.supinfo.jporating.presentation.ui.interrogation.launch.LaunchInterrogationViewModel
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
    single { InterrogationRepositoryImpl(get(), androidContext().filesDir) as InterrogationRepository }
    single { QuestionRepositoryImpl(get()) as QuestionRepository }
    single { AnswerRepositoryImpl(get()) as AnswerRepository }
}

/**
 * Module for all the use case
 */
val useCasesModule = module {
    single { LaunchInterrogationUseCase(get(), get()) }
    single { AnswerQuestionUseCase(get(), get()) }
    single { ResetAnswerUseCase(get()) }
    single { FinishInterrogationUseCase(get()) }
    single { SendInterrogationsUseCase(get()) }
}

/**
 * Module pour les view model
 */
val viewModelModule = module {
    viewModel { InterrogationViewModel() }
    viewModel { AnsweringInterrogationViewModel(get(), get(), get(), get()) }
    viewModel { LaunchInterrogationViewModel(get()) }
}