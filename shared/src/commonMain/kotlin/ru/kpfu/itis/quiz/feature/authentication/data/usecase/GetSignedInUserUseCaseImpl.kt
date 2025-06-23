package ru.kpfu.itis.quiz.feature.authentication.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.model.User
import ru.kpfu.itis.quiz.feature.authentication.domain.repository.AuthenticationRepository
import ru.kpfu.itis.quiz.feature.authentication.domain.usecase.GetSignedInUserUseCase

class GetSignedInUserUseCaseImpl(
    private val authenticationRepository: AuthenticationRepository,
) : GetSignedInUserUseCase {

    override suspend fun invoke(): User? {
        return withContext(Dispatchers.IO) {
            authenticationRepository.getSignedInUser()
        }
    }
}
