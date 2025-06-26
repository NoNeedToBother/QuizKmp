package ru.kpfu.itis.quiz.feature.profile.presentation.mapper

import ru.kpfu.itis.quiz.feature.profile.domain.model.Result as DomainResult
import ru.kpfu.itis.quiz.feature.profile.presentation.model.Result

fun mapResult(result: DomainResult): Result {
    return with(result) {
        Result(
            id = id,
            score = score
        )
    }
}
