package ru.kpfu.itis.quiz.feature.authentication.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.feature.authentication.domain.repository.AuthenticationRepository
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.SignInUserUseCase

class SignInUserUseCaseImpl(
    private val authenticationRepository: AuthenticationRepository,
) : SignInUserUseCase {
    override suspend operator fun invoke(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            authenticationRepository.signIn(username, password)
        }
    }
}