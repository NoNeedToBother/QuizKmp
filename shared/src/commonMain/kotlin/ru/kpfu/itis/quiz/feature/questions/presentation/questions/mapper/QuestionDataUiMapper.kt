package ru.kpfu.itis.quiz.feature.questions.presentation.questions.mapper

import ru.kpfu.itis.quiz.feature.questions.domain.model.QuestionData
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.model.AnswerDataUi
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.model.QuestionDataUi

fun mapQuestionsToUiModel(models: List<QuestionData>): List<QuestionDataUi> {
    return models.map { question ->
        val answers = mutableListOf<AnswerDataUi>()
        answers.add(AnswerDataUi(answer = question.answer, chosen = false, correct = true))
        question.incorrectAnswers.forEach { incorrect ->
            answers.add(AnswerDataUi(answer = incorrect, chosen = false, correct = false))
        }
        QuestionDataUi(
            text = question.text,
            answers = answers.shuffled()
        )
    }
}
