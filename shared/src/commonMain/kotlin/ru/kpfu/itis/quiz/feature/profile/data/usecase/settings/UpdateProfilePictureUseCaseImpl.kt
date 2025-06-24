package ru.kpfu.itis.quiz.feature.profile.data.usecase.settings

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.feature.profile.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateProfilePictureUseCase

class UpdateProfilePictureUseCaseImpl(
    private val userRepository: UserRepository
) : UpdateProfilePictureUseCase {
    override suspend fun invoke(uri: String) {
        withContext(Dispatchers.IO) {
            userRepository.getSignedInUser()?.user?.let { current ->
                userRepository.updateUser(
                    current.copy(profilePictureUri = uri)
                )
            } ?: throw RuntimeException(Res.string.user_not_present)
        }
    }
}
