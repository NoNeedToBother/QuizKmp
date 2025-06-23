package ru.kpfu.itis.quiz.feature.users.domain.usecase

import app.cash.paging.Pager
import ru.kpfu.itis.quiz.core.model.User

interface SearchUsersUseCase {

    suspend operator fun invoke(username: String, limit: Int): Pager<Int, User>

}