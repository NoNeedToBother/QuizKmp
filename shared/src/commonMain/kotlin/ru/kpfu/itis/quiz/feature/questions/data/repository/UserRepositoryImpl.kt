package ru.kpfu.itis.quiz.feature.questions.data.repository

import ru.kpfu.itis.quiz.core.database.AppDatabase
import ru.kpfu.itis.quiz.core.model.User
import ru.kpfu.itis.quiz.feature.questions.data.mapper.entityToUser
import ru.kpfu.itis.quiz.feature.questions.domain.repository.UserRepository

class UserRepositoryImpl(
    private val appDatabase: AppDatabase
) : UserRepository {
    override fun getCurrentUser(): User? {
        val dao = appDatabase.userDao()

        return dao.getSignedInUser()?.let {
            entityToUser(it)
        }
    }
}
