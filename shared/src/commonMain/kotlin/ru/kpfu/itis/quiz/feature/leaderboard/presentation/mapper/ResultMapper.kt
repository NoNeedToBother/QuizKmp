package ru.kpfu.itis.quiz.feature.leaderboard.presentation.mapper

import ru.kpfu.itis.quiz.core.model.Result as CommonResult
import ru.kpfu.itis.quiz.core.model.User as CommonUser
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.model.Result
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.model.User

fun mapResult(result: CommonResult): Result {
    return with(result) {
        Result(
            id = id,
            user = mapUser(user),
            time = time,
            correct = correct,
            total = total,
            score = score,
            difficulty = difficulty,
            category = category,
            gameMode = gameMode,
        )
    }
}

fun mapUser(user: CommonUser): User {
    return with(user) {
        User(
            id = id,
            username = username,
            profilePictureUri = profilePictureUri,
        )
    }
}
