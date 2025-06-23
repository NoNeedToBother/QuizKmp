package ru.kpfu.itis.quiz.feature.questions.domain.usecase

interface GetMaxScoreUseCase {
    suspend operator fun invoke(): Double
}
