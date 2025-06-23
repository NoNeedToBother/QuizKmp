package ru.kpfu.itis.quiz.feature.questions.domain.usecase

import ru.kpfu.itis.quiz.core.model.QuestionSettings

interface GetQuestionSettingsUseCase {
    suspend operator fun invoke(): QuestionSettings
}
