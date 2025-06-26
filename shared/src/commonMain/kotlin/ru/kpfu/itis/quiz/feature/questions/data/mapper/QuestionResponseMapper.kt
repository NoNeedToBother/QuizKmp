package ru.kpfu.itis.quiz.feature.questions.data.mapper

import ru.kpfu.itis.quiz.core.util.decodeHtml
import ru.kpfu.itis.quiz.feature.questions.data.model.QuestionDataResponse
import ru.kpfu.itis.quiz.feature.questions.data.model.QuestionResponse
import ru.kpfu.itis.quiz.feature.questions.domain.model.Questions
import ru.kpfu.itis.quiz.feature.questions.domain.model.QuestionData

internal fun mapResponse(model: QuestionResponse): Questions {
    model.code?.let {
        return Questions(mapQuestionData(model.questions))
    } ?: throw RuntimeException()
}

internal fun mapQuestionData(questionsData: List<QuestionDataResponse>?): List<QuestionData> {
    val result = ArrayList<QuestionData>()
    questionsData?.run {
        for (questionData in questionsData) {
            with(questionData) {
                if (text != null && answer != null && incorrectAnswers != null) {
                    result.add(
                        QuestionData(
                            decodeHtml(text),
                            decodeHtml(answer),
                            incorrectAnswers.map {
                                decodeHtml(it)
                            })
                    )
                } else throw RuntimeException()
            }
        }
    } ?: throw RuntimeException()
    return result
}
