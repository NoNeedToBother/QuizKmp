package ru.kpfu.itis.quiz.android.feature.auth.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.core.designsystem.theme.StardosFont

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.app_name),
        textAlign = TextAlign.Center,
        modifier = modifier,
        fontSize = 92.sp,
        style = TextStyle(fontFamily = StardosFont)
    )
}
