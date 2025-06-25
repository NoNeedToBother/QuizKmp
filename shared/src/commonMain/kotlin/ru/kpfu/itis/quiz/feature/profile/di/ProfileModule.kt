package ru.kpfu.itis.quiz.feature.profile.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.kpfu.itis.quiz.feature.profile.data.repository.UserRepositoryImpl
import ru.kpfu.itis.quiz.feature.profile.data.usecase.GetCurrentUserUseCaseImpl
import ru.kpfu.itis.quiz.feature.profile.data.usecase.GetUserUseCaseImpl
import ru.kpfu.itis.quiz.feature.profile.data.usecase.LogoutUseCaseImpl
import ru.kpfu.itis.quiz.feature.profile.data.usecase.settings.UpdateCredentialsUseCaseImpl
import ru.kpfu.itis.quiz.feature.profile.data.usecase.settings.UpdateProfilePictureUseCaseImpl
import ru.kpfu.itis.quiz.feature.profile.data.usecase.settings.UpdateUserInfoUseCaseImpl
import ru.kpfu.itis.quiz.feature.profile.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.GetCurrentUserUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.GetUserUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.LogoutUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateCredentialsUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateProfilePictureUseCase
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateUserInfoUseCase
import ru.kpfu.itis.quiz.feature.profile.presentation.viewmodel.OtherUserProfileViewModel
import ru.kpfu.itis.quiz.feature.profile.presentation.viewmodel.ProfileViewModel

val profileModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }

    single<UpdateCredentialsUseCase> { UpdateCredentialsUseCaseImpl(get()) }
    single<UpdateProfilePictureUseCase> { UpdateProfilePictureUseCaseImpl(get()) }
    single<UpdateUserInfoUseCase> { UpdateUserInfoUseCaseImpl(get()) }
    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }
    single<GetUserUseCase> { GetUserUseCaseImpl(get()) }
    single<LogoutUseCase> { LogoutUseCaseImpl(get()) }

    viewModelOf(::ProfileViewModel)
    viewModelOf(::OtherUserProfileViewModel)
}
