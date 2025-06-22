package ru.kpfu.itis.quiz.android.feature.questions.presentation.model

import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode

class QuestionData(
    val text: String,
    val answers: List<AnswerData>,
) {

    var difficulty: Difficulty? = null

    var category: Category? = null

    var gameMode: GameMode? = null

}
