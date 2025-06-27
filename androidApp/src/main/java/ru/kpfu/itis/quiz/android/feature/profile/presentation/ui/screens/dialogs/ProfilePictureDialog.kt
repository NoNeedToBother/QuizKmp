package ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.core.designsystem.components.ProfilePicture
import ru.kpfu.itis.quiz.android.core.designsystem.components.TextButton
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.components.DialogWithTitle

@Composable
fun ProfilePictureDialog(
    uri: Uri,
    onPositivePressed: () -> Unit,
    onDismiss: () -> Unit
) {
    DialogWithTitle(
        title = stringResource(R.string.preview),
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ProfilePicture(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(200.dp),
                uri = uri.toString(),
            )
            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                TextButton(
                    onClick = onPositivePressed,
                    text = stringResource(R.string.dialog_pos),
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = onDismiss,
                    text = stringResource(R.string.dialog_neg),
                )
            }
        }
    }
}
