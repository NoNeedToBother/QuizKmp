package ru.kpfu.itis.quiz.feature.leaderboard.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.kpfu.itis.quiz.feature.leaderboard.data.repository.QuestionSettingsRepositoryImpl
import ru.kpfu.itis.quiz.feature.leaderboard.data.repository.ResultRepositoryImpl
import ru.kpfu.itis.quiz.feature.leaderboard.data.usecase.GetDifficultyUseCaseImpl
import ru.kpfu.itis.quiz.feature.leaderboard.data.usecase.GetGameModeUseCaseImpl
import ru.kpfu.itis.quiz.feature.leaderboard.data.usecase.GetLeaderboardUseCaseImpl
import ru.kpfu.itis.quiz.feature.leaderboard.domain.repository.QuestionSettingsRepository
import ru.kpfu.itis.quiz.feature.leaderboard.domain.repository.ResultRepository
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetDifficultyUseCase
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetGameModeUseCase
import ru.kpfu.itis.quiz.feature.leaderboard.domain.usecase.GetLeaderboardUseCase
import ru.kpfu.itis.quiz.feature.leaderboard.presentation.viewmodel.LeaderboardViewModel

val leaderboardModule = module {
    single<ResultRepository> { ResultRepositoryImpl(get()) }
    single<QuestionSettingsRepository> { QuestionSettingsRepositoryImpl(get()) }

    single<GetDifficultyUseCase> { GetDifficultyUseCaseImpl(get()) }
    single<GetGameModeUseCase> { GetGameModeUseCaseImpl(get()) }
    single<GetLeaderboardUseCase> { GetLeaderboardUseCaseImpl(get()) }

    viewModel { LeaderboardViewModel(get(), get(), get(), get()) }
}
