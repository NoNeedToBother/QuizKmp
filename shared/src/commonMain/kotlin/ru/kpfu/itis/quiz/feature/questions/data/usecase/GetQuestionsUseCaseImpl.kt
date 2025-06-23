package ru.kpfu.itis.quiz.feature.questions.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.feature.questions.domain.model.QuestionData
import ru.kpfu.itis.quiz.feature.questions.domain.repository.QuestionRepository
import ru.kpfu.itis.quiz.feature.questions.domain.repository.QuestionSettingsRepository
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.GetQuestionsUseCase

class GetQuestionsUseCaseImpl(
    private val questionRepository: QuestionRepository,
    private val questionSettingsRepository: QuestionSettingsRepository,
) : GetQuestionsUseCase {
    override suspend fun invoke(): List<QuestionData> {
        return withContext(Dispatchers.IO) {
            val difficulty = questionSettingsRepository.getDifficulty()
            val category = questionSettingsRepository.getCategory()
            val gameMode = questionSettingsRepository.getGameMode()
            val amount = when (gameMode) {
                GameMode.BLITZ -> BLITZ_QUESTION_AMOUNT
                GameMode.CHALLENGE -> CHALLENGE_QUESTION_AMOUNT
                GameMode.EXPERT -> EXPERT_QUESTION_AMOUNT
            }
            val categoryCode = questionRepository.getCategoryCode(category)
            questionRepository.getQuestions(
                amount = amount, difficulty = difficulty, category = categoryCode).questions
        }
    }

    companion object {
        private const val BLITZ_QUESTION_AMOUNT = 10
        private const val CHALLENGE_QUESTION_AMOUNT = 15
        private const val EXPERT_QUESTION_AMOUNT = 25
    }
}
