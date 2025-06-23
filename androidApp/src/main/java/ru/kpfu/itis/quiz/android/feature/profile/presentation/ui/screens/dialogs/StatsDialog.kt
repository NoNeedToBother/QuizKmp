package ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.kpfu.itis.quiz.core.model.Result
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.components.DialogWithTitle
import ru.kpfu.itis.quiz.android.core.designsystem.components.EmptyResults
import ru.kpfu.itis.quiz.android.core.designsystem.components.Graph

@Composable
fun StatsDialog(
    results: List<Result>,
    onDismiss: () -> Unit
) {
    DialogWithTitle(
        title = stringResource(R.string.stats),
        onDismiss = onDismiss
    ) {
        if (results.isNotEmpty()) {
            val list = mutableListOf<Pair<Double, Double>>()
            for (i in results.indices) {
                list.add((i + 1).toDouble() to results[i].score)
            }

            Graph(
                values = list,
                gradient = true
            )
        } else EmptyResults()
    }
}
