package ru.kpfu.itis.quiz.feature.profile.data.usecase.settings

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.feature.profile.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.profile.domain.usecase.settings.UpdateUserInfoUseCase

class UpdateUserInfoUseCaseImpl(
    private val userRepository: UserRepository
) : UpdateUserInfoUseCase {
    override suspend fun invoke(info: String?, username: String?) {
        withContext(Dispatchers.IO) {
            userRepository.getSignedInUser()?.user?.let { current ->
                userRepository.updateUser(
                    current.copy(
                        info = info ?: current.info,
                        username = username ?: current.username
                    )
                )
            } ?: throw RuntimeException(Res.string.user_not_present)
        }
    }
}
