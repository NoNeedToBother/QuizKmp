package ru.kpfu.itis.quiz.feature.profile.domain.repository

import ru.kpfu.itis.quiz.feature.profile.domain.model.User
import ru.kpfu.itis.quiz.feature.profile.domain.model.UserWithResults

interface UserRepository {
    suspend fun getSignedInUser(): UserWithResults?

    suspend fun logout()

    suspend fun getUser(id: Long): UserWithResults?

    suspend fun updateUser(updated: User)

    suspend fun updatePassword(userId: Long, password: String)
}
