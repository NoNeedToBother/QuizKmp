package ru.kpfu.itis.quiz.feature.authentication.domain.repository

import ru.kpfu.itis.quiz.core.model.User

interface AuthenticationRepository {

    suspend fun register(username: String, password: String, confirmPassword: String): Boolean

    suspend fun signIn(username: String, password: String): Boolean

    suspend fun getSignedInUser(): User?

}