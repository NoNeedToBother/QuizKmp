package ru.kpfu.itis.quiz.feature.profile.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.feature.profile.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.LogoutUseCase

class LogoutUseCaseImpl(
    private val userRepository: UserRepository
) : LogoutUseCase {

    override suspend fun invoke() {
        withContext(Dispatchers.IO) {
            userRepository.logout()
        }
    }
}
