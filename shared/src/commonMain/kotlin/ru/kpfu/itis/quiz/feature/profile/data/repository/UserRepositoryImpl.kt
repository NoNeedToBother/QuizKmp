package ru.kpfu.itis.quiz.feature.profile.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.database.AppDatabase
import ru.kpfu.itis.quiz.core.util.encrypt
import ru.kpfu.itis.quiz.feature.profile.data.mapper.mapUserEntityWithResults
import ru.kpfu.itis.quiz.feature.profile.domain.model.User
import ru.kpfu.itis.quiz.feature.profile.domain.model.UserWithResults
import ru.kpfu.itis.quiz.feature.profile.domain.repository.UserRepository

class UserRepositoryImpl(
    private val database: AppDatabase,
) : UserRepository {
    override suspend fun getSignedInUser(): UserWithResults? {
        return withContext(Dispatchers.IO) {
            database.userDao().getSignedInUserWithResults()?.let {
                mapUserEntityWithResults(it)
            }
        }
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            database.userDao().logout()
        }
    }

    override suspend fun getUser(id: Long): UserWithResults? {
        return withContext(Dispatchers.IO) {
            database.userDao().getByIdWithResults(id)?.let {
                mapUserEntityWithResults(it)
            }
        }
    }

    override suspend fun updateUser(updated: User) {
        withContext(Dispatchers.IO) {
            val dao = database.userDao()
            val entity = dao.getById(updated.id)
            entity?.apply {
                dao.update(
                    entity.copy(
                        username = updated.username,
                        info = updated.info,
                        profilePictureUri = updated.profilePictureUri
                    )
                )
            }
        }
    }

    override suspend fun updatePassword(userId: Long, password: String) {
        withContext(Dispatchers.IO) {
            database.userDao().updatePassword(
                id = userId, password = password.encrypt()
            )
        }
    }
}
