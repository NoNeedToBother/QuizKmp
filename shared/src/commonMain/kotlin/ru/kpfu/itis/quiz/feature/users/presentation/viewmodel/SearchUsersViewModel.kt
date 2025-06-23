package ru.kpfu.itis.quiz.feature.users.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.feature.users.domain.usecase.SearchUsersUseCase
import ru.kpfu.itis.quiz.feature.users.presentation.mvi.SearchUsersScreenIntent
import ru.kpfu.itis.quiz.feature.users.presentation.mvi.SearchUsersScreenSideEffect
import ru.kpfu.itis.quiz.feature.users.presentation.mvi.SearchUsersScreenState

class SearchUsersViewModel(
    savedStateHandle: SavedStateHandle,
    private val searchUsersUseCase: SearchUsersUseCase,
): ViewModel(), ContainerHost<SearchUsersScreenState, SearchUsersScreenSideEffect> {

    override val container = container<SearchUsersScreenState, SearchUsersScreenSideEffect>(
        initialState = SearchUsersScreenState(),
        savedStateHandle = savedStateHandle,
        serializer = SearchUsersScreenState.serializer()
    )

    fun onIntent(intent: SearchUsersScreenIntent) {
        when(intent) {
            is SearchUsersScreenIntent.SearchUsers -> searchUsers(intent)
        }
    }

    private fun searchUsers(intent: SearchUsersScreenIntent.SearchUsers) = intent {
        try {
            val flow = searchUsersUseCase.invoke(intent.query, intent.limit).flow.cachedIn(viewModelScope)
            //val users = searchUsersUseCase.invoke(username, max, lastId).map { user -> userUiModelMapper.map(user) }
            reduce { state.copy(users = flow) }
        } catch (ex: Throwable) {
            postSideEffect(SearchUsersScreenSideEffect.ShowError(
                title = Res.string.search_users_fail,
                message = ex.message ?: Res.string.default_error_msg
            ))
        }
    }
}
