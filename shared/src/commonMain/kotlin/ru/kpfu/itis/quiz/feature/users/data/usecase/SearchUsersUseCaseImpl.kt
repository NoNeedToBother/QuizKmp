package ru.kpfu.itis.quiz.feature.users.data.usecase

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import ru.kpfu.itis.quiz.core.model.User
import ru.kpfu.itis.quiz.feature.users.domain.paging.UserPagingSource
import ru.kpfu.itis.quiz.feature.users.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.users.domain.usecase.SearchUsersUseCase

class SearchUsersUseCaseImpl(
    private val userRepository: UserRepository,
) : SearchUsersUseCase {
    override suspend fun invoke(username: String, limit: Int): Pager<Int, User> {
        return Pager(
            PagingConfig(
                pageSize = limit,
            )
        ) { UserPagingSource(userRepository).apply {
            query = username
        } }
    }
}
