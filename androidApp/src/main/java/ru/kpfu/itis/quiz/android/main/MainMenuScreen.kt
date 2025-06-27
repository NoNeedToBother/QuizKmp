package ru.kpfu.itis.quiz.android.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.core.designsystem.components.Logo
import ru.kpfu.itis.quiz.android.core.designsystem.theme.StardosFont

@Composable
fun MainMenuScreen(
    goToQuestionsScreen: () -> Unit,
    goToQuestionSettingsScreen: () -> Unit
) {
    ScreenContent(
        onBeginClick = { goToQuestionsScreen() },
        onQuestionsSettingsClick = { goToQuestionSettingsScreen() }
    )
}

@Composable
fun ScreenContent(
    onBeginClick: () -> Unit,
    onQuestionsSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()

        Spacer(modifier = Modifier.height(32.dp))
        MainMenuTextButton(
            text = stringResource(id = R.string.begin),
            onClick = onBeginClick
        )

        Spacer(modifier = Modifier.height(20.dp))
        MainMenuTextButton(
            text = stringResource(id = R.string.question_settings),
            onClick = onQuestionsSettingsClick
        )
    }
}

@Composable
fun MainMenuTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier.clickable {
            onClick()
        },
        text = text,
        fontFamily = StardosFont,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        style = TextStyle.Default.copy(
            fontSize = 52.sp,
            lineHeight = 60.sp
        )
    )
}
