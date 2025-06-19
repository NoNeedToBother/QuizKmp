package ru.kpfu.itis.quiz.questions.presentation.model

import ru.kpfu.itis.quiz.core.core.model.Category
import ru.kpfu.itis.quiz.core.core.model.Difficulty
import ru.kpfu.itis.quiz.core.core.model.GameMode

class QuestionData(
    val text: String,
    val answers: List<AnswerData>,
) {

    var difficulty: Difficulty? = null

    var category: Category? = null

    var gameMode: GameMode? = null

}
