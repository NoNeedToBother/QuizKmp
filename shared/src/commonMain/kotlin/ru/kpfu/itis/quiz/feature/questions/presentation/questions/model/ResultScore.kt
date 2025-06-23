package ru.kpfu.itis.quiz.feature.questions.presentation.questions.model

enum class ResultScore(
    var gradation: Gradation
) {

    A(Gradation.DEFAULT),
    B(Gradation.DEFAULT),
    C(Gradation.DEFAULT),
    D(Gradation.DEFAULT),
    E(Gradation.DEFAULT),
    F(Gradation.DEFAULT);



    enum class Gradation {
        PLUS, MINUS, DEFAULT
    }
}
