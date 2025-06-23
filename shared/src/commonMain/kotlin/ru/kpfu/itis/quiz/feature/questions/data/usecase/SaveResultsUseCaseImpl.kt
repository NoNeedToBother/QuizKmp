package ru.kpfu.itis.quiz.feature.questions.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.feature.questions.domain.model.Result
import ru.kpfu.itis.quiz.feature.questions.domain.repository.ResultRepository
import ru.kpfu.itis.quiz.feature.questions.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.SaveResultsUseCase

class SaveResultsUseCaseImpl(
    private val userRepository: UserRepository,
    private val resultRepository: ResultRepository,
) : SaveResultsUseCase {
    override suspend fun invoke(
        difficulty: Difficulty,
        category: Category,
        gameMode: GameMode,
        time: Int,
        correct: Int,
        total: Int
    ): Double {
        return withContext(Dispatchers.IO) {
            val currentUser = userRepository.getCurrentUser()
                ?: throw RuntimeException(Res.string.user_not_present)

            val result = Result(
                user = currentUser,
                time = time,
                correct = correct,
                total = total,
                difficulty = difficulty,
                category = category,
                gameMode = gameMode
            )
            resultRepository.save(result, currentUser.id)
        }
    }
}