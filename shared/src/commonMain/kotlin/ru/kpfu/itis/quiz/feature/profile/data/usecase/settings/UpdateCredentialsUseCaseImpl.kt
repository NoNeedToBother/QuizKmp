package ru.kpfu.itis.quiz.feature.profile.data.usecase.settings

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.feature.profile.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateCredentialsUseCase

class UpdateCredentialsUseCaseImpl(
    private val userRepository: UserRepository
) : UpdateCredentialsUseCase {
    override suspend fun invoke(password: String) {
        withContext(Dispatchers.IO) {
            userRepository.getSignedInUser()?.user?.let { current ->
                userRepository.updatePassword(
                    userId = current.id, password = password
                )
            } ?: throw RuntimeException(Res.string.user_not_present)
        }
    }
}