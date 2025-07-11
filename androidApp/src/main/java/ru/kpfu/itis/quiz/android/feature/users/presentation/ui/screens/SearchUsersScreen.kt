package ru.kpfu.itis.quiz.android.feature.users.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.feature.users.presentation.mvi.SearchUsersScreenSideEffect
import ru.kpfu.itis.quiz.feature.users.presentation.mvi.SearchUsersScreenState
import ru.kpfu.itis.quiz.android.feature.users.presentation.ui.components.SearchBar
import ru.kpfu.itis.quiz.android.feature.users.presentation.ui.components.UserList
import ru.kpfu.itis.quiz.feature.users.presentation.mvi.SearchUsersScreenIntent
import ru.kpfu.itis.quiz.feature.users.presentation.viewmodel.SearchUsersViewModel
import java.lang.IllegalStateException
import java.util.Timer
import kotlin.concurrent.timerTask

private const val MAX_USER_AMOUNT = 15

private const val MIN_TIME_BETWEEN_REGISTERING = 400L

private const val DEFAULT_LAST_TIME_VALUE = -1L

@Composable
fun SearchUsersScreen(
    goToUserScreen: (Long) -> Unit,
    viewModel: SearchUsersViewModel = koinViewModel()
) {
    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var lastTime by remember { mutableLongStateOf(DEFAULT_LAST_TIME_VALUE) }
    var timer by remember { mutableStateOf(Timer()) }
    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(null) {
        effect.collect {
            when (it) {
                is SearchUsersScreenSideEffect.ShowError -> {
                    val errorMessage = it.message
                    val errorTitle = it.title

                    error = errorTitle to errorMessage
                }
            }
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    Screen(
        state = state.value,
        onUserClick = { id -> goToUserScreen(id) },
        onSearch = {
            searchQuery = it
            val currentTime = System.currentTimeMillis()
            if (lastTime != DEFAULT_LAST_TIME_VALUE &&
                currentTime - lastTime < MIN_TIME_BETWEEN_REGISTERING
            ) {
                try {
                    timer.cancel()
                    timer = Timer()
                } catch (_: IllegalStateException) {}
            }
            lastTime = currentTime
            timer.schedule(timerTask {
                viewModel.onIntent(SearchUsersScreenIntent.SearchUsers(searchQuery, MAX_USER_AMOUNT))
            }, MIN_TIME_BETWEEN_REGISTERING)
        },
    )

    Box {
        error?.let {
            ErrorDialog(
                onDismiss = { error = null },
                title = it.first,
                text = it.second
            )
        }
    }
}

@Composable
fun Screen(
    state: SearchUsersScreenState,
    onUserClick: (Long) -> Unit,
    onSearch: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        var searchQuery by remember { mutableStateOf("") }

        SearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                onSearch(searchQuery)
            },
        )

        UserList(
            users = state.users,
            onUserClick = onUserClick,
        )
    }
}
