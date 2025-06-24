package ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.components.DialogWithTitle
import ru.kpfu.itis.quiz.android.core.designsystem.components.PasswordInputSection

@Composable
fun CredentialsDialog(
    onDismiss: () -> Unit,
    onSave: (password: String?, confirmPassword: String?) -> Unit,
    checkPassword: (password: String?) -> Unit,
    checkConfirmPassword: (password: String?) -> Unit,
    passwordError: String? = null,
    confirmPasswordError: String? = null,
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    DialogWithTitle(
        title = stringResource(R.string.change_credentials),
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            PasswordInputSection(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                onInput = {
                    password = it
                    checkPassword(it)
                },
                label = stringResource(R.string.password),
                value = password,
                error = passwordError
            )
            PasswordInputSection(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                onInput = {
                    confirmPassword = it
                    checkConfirmPassword(it)
                },
                label = stringResource(R.string.confirm_password),
                value = confirmPassword,
                error = confirmPasswordError
            )

            Row(
                modifier = Modifier.align(Alignment.End)
            ) {
                Button(
                    onClick = {
                        val resPassword = password.ifEmpty { null }
                        val resConfirmPassword = confirmPassword.ifEmpty { null }
                        onSave(resPassword, resConfirmPassword)
                    },
                    enabled = passwordError == null && password.isNotEmpty() &&
                            confirmPasswordError == null && confirmPassword.isNotEmpty()
                ) {
                    Text(text = stringResource(R.string.dialog_pos))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onDismiss
                ) {
                    Text(text = stringResource(R.string.dialog_neg))
                }
            }
        }
    }
}
