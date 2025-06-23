package ru.kpfu.itis.quiz.feature.questions.domain.usecase

import ru.kpfu.itis.quiz.feature.questions.domain.model.QuestionData

interface GetQuestionsUseCase {
    suspend operator fun invoke(): List<QuestionData>
}
