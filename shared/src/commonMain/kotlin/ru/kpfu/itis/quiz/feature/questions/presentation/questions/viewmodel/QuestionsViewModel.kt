package ru.kpfu.itis.quiz.feature.questions.presentation.questions.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.core.util.KMMTimer
import ru.kpfu.itis.quiz.core.util.normalizeEnumName
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.GetMaxScoreUseCase
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.GetQuestionSettingsUseCase
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.GetQuestionsUseCase
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.SaveResultsUseCase
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.mapper.mapQuestionsToUiModel
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.model.AnswerDataUi
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.model.QuestionDataUi
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.model.ResultData
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.mvi.QuestionsScreenIntent
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.mvi.QuestionsScreenSideEffect
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.mvi.QuestionsScreenState

class QuestionsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val saveResultsUseCase: SaveResultsUseCase,
    private val getQuestionSettingsUseCase: GetQuestionSettingsUseCase,
    private val getMaxScoreUseCase: GetMaxScoreUseCase,
): ViewModel(), ContainerHost<QuestionsScreenState, QuestionsScreenSideEffect> {

    override val container = container<QuestionsScreenState, QuestionsScreenSideEffect>(
        initialState = QuestionsScreenState(),
        savedStateHandle = savedStateHandle,
        serializer = QuestionsScreenState.serializer()
    )

    private var timer: KMMTimer? = null

    fun onIntent(intent: QuestionsScreenIntent) = intent {
        when(intent) {
            is QuestionsScreenIntent.GetMaxScore -> getMaxScore()
            is QuestionsScreenIntent.GetQuestions -> getQuestions()
            is QuestionsScreenIntent.UpdateAnswers -> updateChosenAnswers(intent)
            is QuestionsScreenIntent.EndQuestions -> onQuestionsEnd()
        }
    }

    private fun getMaxScore() = intent {
        val maxScore = getMaxScoreUseCase.invoke()
        reduce { state.copy(maxScore = maxScore) }
    }

    private fun updateChosenAnswers(intent: QuestionsScreenIntent.UpdateAnswers) = intent {
        val value = state.questions[intent.pos]
        val answerListCopy = ArrayList<AnswerDataUi>()
        for (answerData in value.answers) {
            answerListCopy.add(answerData.copy())
        }
        val question = QuestionDataUi(value.text, answerListCopy).apply {
            difficulty = value.difficulty
            category = value.category
            gameMode = value.gameMode
        }
        for (answerPos in question.answers.indices) {
            val answer = question.answers[answerPos]
            if (answerPos == intent.chosenPos) answer.chosen = answer.chosen.not()
            else answer.chosen = false
        }
        val newQuestions = ArrayList(state.questions)
        newQuestions[intent.pos] = question

        reduce { state.copy(questions = newQuestions) }
    }

    private fun getQuestions() = intent {
        try {
            val questions = mapQuestionsToUiModel(getQuestionsUseCase.invoke())

            reduce { state.copy(questions = questions) }
            startClockTicking()
        } catch (ex: Throwable) {
            postSideEffect(QuestionsScreenSideEffect.ShowError(
                title = Res.string.get_questions_fail,
                message = ex.message ?: Res.string.default_error_msg
            ))
        }
    }

    private fun onQuestionsEnd() = intent {
        try {
            val time = state.time
            var correct = 0
            var total = 0
            val settings = getQuestionSettingsUseCase.invoke()
            for (question in state.questions) {
                total++
                for (answer in question.answers) {
                    if (answer.correct && answer.chosen) correct++
                }
            }
            val score = saveResultsUseCase.invoke(
                difficulty = settings.difficulty,
                category = settings.category,
                gameMode = settings.gameMode,
                time = time, correct = correct, total = total
            )
            val resultData = ResultData(
                difficulty = settings.difficulty.name.normalizeEnumName(),
                category = settings.category.name.normalizeEnumName(),
                gameMode = settings.gameMode.name.normalizeEnumName(),
                time = time,
                correct = correct,
                total = total,
                score = score
            )
            reduce { state.copy(result = resultData) }
            stopTimer()
        } catch (ex: Throwable) {
            postSideEffect(QuestionsScreenSideEffect.ShowError(
                title = Res.string.save_results_fail,
                message = ex.message ?: Res.string.default_error_msg
            ))
        }
    }

    private fun startClockTicking() = intent {
        if (timer == null) {
            timer = KMMTimer(
                name = "clock_timer",
                interval = ONE_SECOND_MILLIS,
                delay = 0L,
                action = {
                    viewModelScope.launch {
                        reduce { state.copy(time = state.time + 1) }
                    }
                }
            )
            timer?.start()
        }
    }

    private fun stopTimer() {
        if (timer?.isRunning() == true) {
            timer?.cancel()
        }
        timer = null
    }

    override fun onCleared() {
        stopTimer()
        super.onCleared()
    }

    companion object {
        private const val ONE_SECOND_MILLIS = 1000L
    }
}
