package ru.kpfu.itis.quiz.feature.questions.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.feature.questions.domain.repository.ResultRepository
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.GetMaxScoreUseCase

class GetMaxScoreUseCaseImpl(
    private val resultRepository: ResultRepository
) : GetMaxScoreUseCase {
    override suspend fun invoke(): Double {
        return withContext(Dispatchers.IO) {
            resultRepository.getMaxScore()
        }
    }
}
