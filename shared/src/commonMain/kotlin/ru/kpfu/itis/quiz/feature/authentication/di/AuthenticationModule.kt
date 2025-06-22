package ru.kpfu.itis.quiz.feature.authentication.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.kpfu.itis.quiz.feature.authentication.domain.repository.AuthenticationRepository
import ru.kpfu.itis.quiz.feature.authentication.data.usecase.GetSignedInUserUseCaseImpl
import ru.kpfu.itis.quiz.feature.authentication.data.usecase.RegisterUserUseCaseImpl
import ru.kpfu.itis.quiz.feature.authentication.data.usecase.SignInUserUseCaseImpl
import ru.kpfu.itis.quiz.feature.authentication.data.repository.AuthenticationRepositoryImpl
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.GetSignedInUserUseCase
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.RegisterUserUseCase
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.SignInUserUseCase
import ru.kpfu.itis.quiz.feature.authentication.presentation.register.viewmodel.RegisterViewModel
import ru.kpfu.itis.quiz.feature.authentication.presentation.signin.viewmodel.SignInViewModel

val authenticationModule = module {
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get()) }

    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }
    single<SignInUserUseCase> { SignInUserUseCaseImpl(get()) }
    single<GetSignedInUserUseCase> { GetSignedInUserUseCaseImpl(get()) }

    viewModel { SignInViewModel(get(), get(), get()) }
    viewModel { RegisterViewModel(get(), get(), get()) }
}