package ru.kpfu.itis.quiz.feature.users.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.kpfu.itis.quiz.feature.users.data.repository.UserRepositoryImpl
import ru.kpfu.itis.quiz.feature.users.data.usecase.SearchUsersUseCaseImpl
import ru.kpfu.itis.quiz.feature.users.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.users.domain.usecase.SearchUsersUseCase
import ru.kpfu.itis.quiz.feature.users.presentation.viewmodel.SearchUsersViewModel

val usersModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }

    single<SearchUsersUseCase> { SearchUsersUseCaseImpl(get()) }

    viewModel { SearchUsersViewModel(get(), get()) }
}
