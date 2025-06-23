package ru.kpfu.itis.quiz.feature.questions.presentation.questions.mapper

import ru.kpfu.itis.quiz.feature.questions.domain.model.AnswerData
import ru.kpfu.itis.quiz.feature.questions.domain.model.QuestionData
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.model.QuestionDataUi

fun mapToUiModel(models: List<QuestionData>): List<QuestionDataUi> {
    return models.map { question ->
        val answers = mutableListOf<AnswerData>()
        answers.add(AnswerData(answer = question.answer, chosen = false, correct = true))
        question.incorrectAnswers.forEach { incorrect ->
            answers.add(AnswerData(answer = incorrect, chosen = false, correct = false))
        }
        QuestionDataUi(
            text = question.text,
            answers = answers.shuffled()
        )
    }
}
