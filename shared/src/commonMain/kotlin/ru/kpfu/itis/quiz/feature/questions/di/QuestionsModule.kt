package ru.kpfu.itis.quiz.feature.questions.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.kpfu.itis.quiz.feature.questions.data.repository.QuestionRepositoryImpl
import ru.kpfu.itis.quiz.feature.questions.data.repository.QuestionSettingsRepositoryImpl
import ru.kpfu.itis.quiz.feature.questions.data.repository.ResultRepositoryImpl
import ru.kpfu.itis.quiz.feature.questions.data.repository.UserRepositoryImpl
import ru.kpfu.itis.quiz.feature.questions.data.usecase.GetMaxScoreUseCaseImpl
import ru.kpfu.itis.quiz.feature.questions.data.usecase.GetQuestionSettingsUseCaseImpl
import ru.kpfu.itis.quiz.feature.questions.data.usecase.GetQuestionsUseCaseImpl
import ru.kpfu.itis.quiz.feature.questions.data.usecase.SaveQuestionSettingsUseCaseImpl
import ru.kpfu.itis.quiz.feature.questions.data.usecase.SaveResultsUseCaseImpl
import ru.kpfu.itis.quiz.feature.questions.domain.repository.QuestionRepository
import ru.kpfu.itis.quiz.feature.questions.domain.repository.QuestionSettingsRepository
import ru.kpfu.itis.quiz.feature.questions.domain.repository.ResultRepository
import ru.kpfu.itis.quiz.feature.questions.domain.repository.UserRepository
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.GetMaxScoreUseCase
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.GetQuestionSettingsUseCase
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.GetQuestionsUseCase
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.SaveQuestionSettingsUseCase
import ru.kpfu.itis.quiz.feature.questions.domain.usecase.SaveResultsUseCase
import ru.kpfu.itis.quiz.feature.questions.presentation.questions.viewmodel.QuestionsViewModel
import ru.kpfu.itis.quiz.feature.questions.presentation.settings.viewmodel.QuestionSettingsViewModel

val questionsModule = module {
    single<QuestionSettingsRepository> { QuestionSettingsRepositoryImpl(get()) }
    single<QuestionRepository> { QuestionRepositoryImpl(get()) }
    single<ResultRepository> { ResultRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }

    single<GetMaxScoreUseCase> { GetMaxScoreUseCaseImpl(get()) }
    single<GetQuestionSettingsUseCase> { GetQuestionSettingsUseCaseImpl(get()) }
    single<GetQuestionsUseCase> { GetQuestionsUseCaseImpl(get(), get()) }
    single<SaveQuestionSettingsUseCase> { SaveQuestionSettingsUseCaseImpl(get()) }
    single<SaveResultsUseCase> { SaveResultsUseCaseImpl(get(), get()) }

    viewModel { QuestionsViewModel(get(), get(), get(), get(), get()) }
    viewModel { QuestionSettingsViewModel(get(), get(), get()) }
}
