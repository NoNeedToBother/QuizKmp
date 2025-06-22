package ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.feature.profile.presentation.mvi.ProfileScreenSideEffect
import ru.kpfu.itis.quiz.android.feature.profile.presentation.mvi.ProfileScreenState
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.components.ProfileInfoField
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.CredentialsDialog
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.INFO_UPDATE_KEY
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.ProfilePictureDialog
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.ProfileSettingsDialog
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.StatsDialog
import ru.kpfu.itis.quiz.android.feature.profile.presentation.ui.screens.dialogs.USERNAME_UPDATE_KEY
import ru.kpfu.itis.quiz.android.core.designsystem.components.ErrorDialog

private const val MAX_RESULTS_AMOUNT = 10

@Composable
fun ProfileScreen(
    goToSignInScreen: () -> Unit
) {
    /*val di = localDI()
    val viewModel: ProfileViewModel by di.instance()
    val resourceManager: ResourceManager by di.instance()

    val state = viewModel.container.stateFlow.collectAsState()
    val effect = viewModel.container.sideEffectFlow

    var results by remember { mutableStateOf<List<ResultUiModel>?>(null) }
    var requests by remember { mutableStateOf<List<UserModel>?>(null) }
    var profilePictureUri by remember { mutableStateOf<Uri?>(null) }
    var showProfileSettingsDialog by remember { mutableStateOf(false) }
    var showConfirmCredentialsDialog by remember { mutableStateOf(false) }
    var showCredentialsDialog by remember { mutableStateOf(false) }

    var error by remember { mutableStateOf<Pair<String, String>?>(null) }

    LaunchedEffect(null) {
        viewModel.getCurrentUser()
        viewModel.subscribeToProfileUpdates()

        effect.collect {
            when(it) {
                ProfileScreenSideEffect.CredentialsConfirmed -> { showCredentialsDialog = true }
                is ProfileScreenSideEffect.FriendRequestsReceived -> { requests = it.requests }
                ProfileScreenSideEffect.GoToSignIn -> { goToSignInScreen() }
                is ProfileScreenSideEffect.ResultsReceived -> { results = it.results }
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
    ) { uri -> uri?.let { viewModel.onProfilePictureChosen(uri) } }

    ScreenContent(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onSubmitPhotoClick = {
            pickProfilePictureLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onCheckResultsClick = { viewModel.getLastResults(MAX_RESULTS_AMOUNT) },
        onCheckRequestsClick = { viewModel.getFriendRequests() },
        onUserSettingsClicked = { showProfileSettingsDialog = true },
        onChangeCredentialsClicked = { showConfirmCredentialsDialog = true },
        onLogoutClicked = { viewModel.logout() },
    ) {
        results?.let {
            StatsDialog(
                results = it,
                onDismiss = {
                    results = null
                }
            )
        }
        requests?.let {
            RequestsDialog(
                initialRequests = it,
                onDismiss = { requests = null },
                onDenyClick = { userId -> viewModel.denyFriendRequest(userId) },
                onAcceptClick = { userId -> viewModel.acceptFriendRequest(userId) },
            )
        }
        profilePictureUri?.let {
            ProfilePictureDialog(
                uri = it,
                onPositivePressed = {
                    viewModel.saveProfilePicture(it)
                    profilePictureUri = null
                },
                onDismiss = { profilePictureUri = null }
            )
        }
        if (showProfileSettingsDialog)
            ProfileSettingsDialog(
                onDismiss = {
                    showProfileSettingsDialog = false
                    viewModel.clearErrors()
                },
                onSave = { username, info ->
                    val params = mutableMapOf<String, String>()
                    username?.let { params.put(USERNAME_UPDATE_KEY, username) }
                    info?.let { params.put(INFO_UPDATE_KEY, info) }
                    viewModel.saveUserSettings(params)
                    showProfileSettingsDialog = false
                },
                checkUsername = { viewModel.validateUsername(it) },
                usernameError = state.value.usernameError
            )
        if (showConfirmCredentialsDialog)
            ConfirmCredentialsDialog(
                onDismiss = {
                    showConfirmCredentialsDialog = false
                    viewModel.clearErrors()
                },
                onSave = { email, password ->
                    if (email != null && password != null)
                        viewModel.confirmCredentials(email, password)
                    showConfirmCredentialsDialog = false
                },
                checkPassword = { viewModel.validatePassword(it) },
                checkEmail = { viewModel.validateUsername(it) },
                passwordError = state.value.passwordError,
                emailError = state.value.emailError
            )
        if (showCredentialsDialog)
            CredentialsDialog(
                onDismiss = {
                    showCredentialsDialog = false
                    viewModel.clearErrors()
                },
                onSave = { email, password, confirmPassword ->
                    if (password != null && confirmPassword != null) {
                        if (password == confirmPassword) viewModel.changeCredentials(email, password)
                        else {
                            error = resourceManager.getString(R.string.dialog_incorrect_credentials) to
                                    resourceManager.getString(R.string.password_confirm_password_not_match)
                        }
                    }
                    else if (password == null && confirmPassword == null) {
                        viewModel.changeCredentials(email, null)
                    } else {
                        error = resourceManager.getString(R.string.credentials_change_failed) to
                                resourceManager.getString(R.string.dialog_incorrect_credentials)
                    }
                    showCredentialsDialog = false
                },
                checkEmail = { viewModel.validateEmail(it) },
                checkPassword = { viewModel.validatePassword(it) },
                checkConfirmPassword = { viewModel.validateConfirmPassword(it) },
                emailError = state.value.emailError,
                passwordError = state.value.passwordError,
                confirmPasswordError = state.value.confirmPasswordError
            )
        error?.let {
            ErrorDialog(
                onDismiss = { error = null },
                title = it.first,
                text = it.second
            )
        }
    }*/
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    state: State<ProfileScreenState>,
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
            processingCredentials = state.value.processingCredentials,
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
                imageUrl = state.value.user?.profilePictureUri,
                onSubmitPhotoClick = onSubmitPhotoClick
            )
            ProfileInfoField(
                modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
                label = stringResource(R.string.username),
                value = state.value.user?.username ?: ""
            )
            ProfileInfoField(
                modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
                label = stringResource(R.string.info),
                value = state.value.user?.info ?: ""
            )
            ProfileInfoField(
                modifier = Modifier.padding(PaddingValues(top = 12.dp, start = 80.dp, end = 80.dp)),
                label = stringResource(R.string.registration_date, state.value.user?.dateRegistered ?: ""),
                value = ""
            )

            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 80.dp),
                    onClick = onCheckResultsClick
                ) {
                    Text(stringResource(R.string.check_stats))
                }
            }
        }
    }
    dialogs()
}

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    onSubmitPhotoClick: () -> Unit
) {
    Box(modifier = modifier.size(220.dp)) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
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
                }
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
