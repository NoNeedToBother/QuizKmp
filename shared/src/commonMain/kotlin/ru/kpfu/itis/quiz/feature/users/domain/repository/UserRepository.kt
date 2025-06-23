package ru.kpfu.itis.quiz.feature.users.domain.repository

import ru.kpfu.itis.quiz.core.model.User

interface UserRepository {

    suspend fun findByUsernameQueryWithPaging(username: String, limit: Int, offset: Int): List<User>

}