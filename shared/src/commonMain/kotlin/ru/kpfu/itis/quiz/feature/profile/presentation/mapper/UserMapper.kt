package ru.kpfu.itis.quiz.feature.profile.presentation.mapper

import ru.kpfu.itis.quiz.feature.profile.domain.model.User as DomainUser
import ru.kpfu.itis.quiz.feature.profile.presentation.model.User

fun mapUser(user: DomainUser): User {
    return with(user) {
        User(
            id = id,
            username = username,
            profilePictureUri = profilePictureUri,
            info = info,
            dateRegistered = dateRegistered,
        )
    }
}
