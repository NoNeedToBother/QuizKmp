package ru.kpfu.itis.quiz.feature.questions.data.mapper

import ru.kpfu.itis.quiz.core.database.entity.UserEntity
import ru.kpfu.itis.quiz.core.model.User

fun entityToUser(entity: UserEntity): User {
    return User(
        id = entity.id,
        username = entity.username,
        profilePictureUri = entity.profilePictureUri,
        info = entity.info,
        dateRegistered = entity.dateRegistered,
    )
}