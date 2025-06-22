package ru.kpfu.itis.quiz.feature.authentication.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.feature.authentication.domain.repository.AuthenticationRepository
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.RegisterUserUseCase

class RegisterUserUseCaseImpl(
    private val authenticationRepository: AuthenticationRepository,
) : RegisterUserUseCase {
    override suspend operator fun invoke(
        username: String, password: String, confirmPassword: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            authenticationRepository.register(username, password, confirmPassword)
        }
    }
}