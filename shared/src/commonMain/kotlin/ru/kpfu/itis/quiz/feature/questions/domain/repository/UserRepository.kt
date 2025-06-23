package ru.kpfu.itis.quiz.feature.questions.domain.repository

import ru.kpfu.itis.quiz.core.model.User

interface UserRepository {

    fun getCurrentUser(): User?
}