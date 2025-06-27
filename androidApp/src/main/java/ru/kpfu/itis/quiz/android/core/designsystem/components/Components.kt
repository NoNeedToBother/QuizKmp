package ru.kpfu.itis.quiz.android.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.placeholder
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.core.designsystem.theme.StardosFont

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
fun InfoTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        enabled = false,
        modifier = modifier
    )
}

@Composable
fun ProfilePicture(
    modifier: Modifier = Modifier,
    uri: String,
    onClick: () -> Unit = {},
) {
    Image(
        painter = uri.let {
            if (it.isEmpty()) painterResource(R.drawable.default_pfp)
            else rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri.toUri())
                    .placeholder(R.drawable.default_pfp)
                    .build(),
                placeholder = painterResource(R.drawable.default_pfp),
            )
        },
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}

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
