package ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import org.koin.compose.viewmodel.koinViewModel
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog
import ru.kpfu.itis.quiz.android.core.designsystem.components.InfoTextField
import ru.kpfu.itis.quiz.android.core.designsystem.components.ProfilePicture
import ru.kpfu.itis.quiz.android.core.designsystem.components.TextButton
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile.ProfileScreenState
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.CredentialsDialog
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.ProfilePictureDialog
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.ProfileSettingsDialog
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.StatsDialog
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile.ProfileScreenIntent
import ru.kpfu.itis.quiz.feature.profile.presentation.mvi.profile.ProfileScreenSideEffect
import ru.kpfu.itis.quiz.feature.profile.presentation.viewmodel.ProfileViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun ProfileScreen(
    goToSignInScreen: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var profilePictureUri by remember { mutableStateOf<String?>(null) }
    var showProfileSettingsDialog by remember { mutableStateOf(false) }
    var showCredentialsDialog by remember { mutableStateOf(false) }
    var showStatsDialog by remember { mutableStateOf(false) }

    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(null) {
        viewModel.onIntent(ProfileScreenIntent.GetCurrent)

        effect.collect {
            when(it) {
                ProfileScreenSideEffect.GoToSignIn -> { goToSignInScreen() }
                is ProfileScreenSideEffect.ShowError -> {
                    val errorMessage = it.message
                    val errorTitle = it.title

                    error = errorTitle to errorMessage
                }
                is ProfileScreenSideEffect.ProfilePictureConfirmed -> { profilePictureUri = it.uri }
            }
        }
    }

    val pickProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let { viewModel.onIntent(ProfileScreenIntent.ConfirmProfilePicture(it.toString())) } }

    val context = LocalContext.current

    ScreenContent(
        modifier = Modifier.fillMaxSize(),
        state = state.value,
        onSubmitPhotoClick = {
            pickProfilePictureLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onUserSettingsClicked = { showProfileSettingsDialog = true },
        onChangeCredentialsClicked = { showCredentialsDialog = true },
        onLogoutClicked = { viewModel.onIntent(ProfileScreenIntent.Logout) },
        onCheckResultsClick = { showStatsDialog = true }
    ) {
        if (showStatsDialog)
            StatsDialog(
                results = state.value.results,
                onDismiss = { showStatsDialog = false }
            )
        profilePictureUri?.let {
            ProfilePictureDialog(
                uri = it.toUri(),
                onPositivePressed = {
                    val savedUri = saveLocalPicture(
                        context = context,
                        name = "img_${state.value.user?.username}_${state.value.user?.id}",
                        uri = it.toUri()
                    ).buildUpon()
                        .appendQueryParameter("timestamp", System.currentTimeMillis().toString())
                        .build()
                    viewModel.onIntent(ProfileScreenIntent.UpdateProfilePicture(savedUri.toString()))
                    profilePictureUri = null
                },
                onDismiss = { profilePictureUri = null }
            )
        }
        if (showProfileSettingsDialog)
            ProfileSettingsDialog(
                onDismiss = {
                    showProfileSettingsDialog = false
                    viewModel.onIntent(ProfileScreenIntent.ClearErrors)
                },
                onSave = { username, info ->
                    viewModel.onIntent(ProfileScreenIntent.UpdateUserInfo(
                        username = username, info = info
                    ))
                    showProfileSettingsDialog = false
                },
                checkUsername = { viewModel.onIntent(ProfileScreenIntent.ValidateUsername(it)) },
                usernameError = state.value.usernameError
            )
        if (showCredentialsDialog) {
            val dialogIncorrectCredentialsMsg = stringResource(R.string.dialog_incorrect_credentials)
            val confirmPasswordNotMatchingMsg = stringResource(R.string.password_confirm_password_not_match)
            val credentialsChangeFailMsg = stringResource(R.string.credentials_change_failed)
            CredentialsDialog(
                onDismiss = {
                    showCredentialsDialog = false
                    viewModel.onIntent(ProfileScreenIntent.ClearErrors)
                },
                onSave = { password, confirmPassword ->
                    if (password != null && confirmPassword != null) {
                        if (password == confirmPassword) viewModel.onIntent(
                            ProfileScreenIntent.UpdatePassword(
                                password
                            )
                        )
                        else {
                            error = dialogIncorrectCredentialsMsg to confirmPasswordNotMatchingMsg
                        }
                    } else {
                        error = credentialsChangeFailMsg to dialogIncorrectCredentialsMsg
                    }
                    showCredentialsDialog = false
                },
                checkPassword = { viewModel.onIntent(ProfileScreenIntent.ValidatePassword(it)) },
                checkConfirmPassword = {
                    viewModel.onIntent(
                        ProfileScreenIntent.ValidateConfirmPassword(
                            it
                        )
                    )
                },
                passwordError = state.value.passwordError,
                confirmPasswordError = state.value.confirmPasswordError
            )
        }
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
fun ScreenContent(
    modifier: Modifier = Modifier,
    state: ProfileScreenState,
    onSubmitPhotoClick: () -> Unit,
    onCheckResultsClick: () -> Unit,
    onUserSettingsClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    onChangeCredentialsClicked: () -> Unit,
    dialogs: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        SettingsMenu(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            processingCredentials = state.processingCredentials,
            onUserSettingsClicked = onUserSettingsClicked,
            onLogoutClicked = onLogoutClicked,
            onChangeCredentialsClicked = onChangeCredentialsClicked
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(top = 24.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImage(
                uri = state.user?.profilePictureUri,
                onSubmitPhotoClick = onSubmitPhotoClick
            )
            InfoTextField(
                modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
                label = stringResource(R.string.username),
                value = state.user?.username ?: ""
            )
            InfoTextField(
                modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
                label = stringResource(R.string.info),
                value = state.user?.info ?: ""
            )
            InfoTextField(
                modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
                label = stringResource(R.string.registration_date, state.user?.dateRegistered ?: ""),
                value = ""
            )

            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 80.dp)
                        .padding(bottom = 12.dp),
                    onClick = onCheckResultsClick,
                    text = stringResource(R.string.check_stats)
                )
            }
        }
    }
    dialogs()
}

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    uri: String?,
    onSubmitPhotoClick: () -> Unit
) {
    Box(modifier = modifier.size(220.dp)) {
        ProfilePicture(
            uri = uri ?: "",
            modifier = Modifier.size(200.dp).align(Alignment.Center)
        )
        FloatingActionButton(
            onClick = onSubmitPhotoClick,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
        ) {
            Icon(Icons.Default.Photo, contentDescription = stringResource(R.string.submit_photo))
        }
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun SettingsMenu(
    modifier: Modifier = Modifier,
    processingCredentials: Boolean,
    onUserSettingsClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    onChangeCredentialsClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val image = AnimatedImageVector.animatedVectorResource(R.drawable.settings_anim)
    var atEnd by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Image(
            painter = rememberAnimatedVectorPainter(image, atEnd),
            contentDescription = stringResource(R.string.settings),
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    expanded = true
                    atEnd = !atEnd
                },
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                atEnd = false
            },
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.settings)) },
                onClick = {
                    expanded = false
                    atEnd = false
                    onUserSettingsClicked()
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.change_credentials)) },
                onClick = {
                    expanded = false
                    atEnd = false
                    onChangeCredentialsClicked()
                },
                enabled = !processingCredentials
            )
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.logout), color = Color.Red) },
                onClick = {
                    expanded = false
                    atEnd = false
                    onLogoutClicked()
                }
            )
        }
    }
}

private fun saveLocalPicture(context: Context, name: String, uri: Uri): Uri {
    val contentResolver = context.contentResolver
    val fileName = "${name}.jpg"
    val outputFile = File(context.filesDir, fileName)

    contentResolver.openInputStream(uri)?.use { input ->
        FileOutputStream(outputFile).use { output ->
            input.copyTo(output)
        }
    }

    return Uri.fromFile(outputFile)
}
