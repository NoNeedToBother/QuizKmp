package ru.kpfu.itis.quiz.android.feature.leaderboard.presentation.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.placeholder
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.quiz.core.util.normalizeEnumName
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.core.model.Result

@Composable
fun ResultItem(
    result: Result,
    onProfileClick: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = result.user.profilePictureUri.let {
                    if (it.isEmpty()) painterResource(R.drawable.default_pfp)
                    else rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(result.user.profilePictureUri.toUri())
                            .placeholder(R.drawable.default_pfp)
                            .build(),
                        placeholder = painterResource(R.drawable.default_pfp),
                    )
                },
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable { onProfileClick(result.user.id) },
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = result.user.username, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = result.score.toString(), fontSize = 14.sp, color = Color.Gray)
            }
        }

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly)
                {
                    Text(stringResource(R.string.res_game_mode,
                        result.gameMode.name.normalizeEnumName()), fontSize = 14.sp)
                    Text(stringResource(R.string.res_difficulty,
                        result.difficulty.name.normalizeEnumName()), fontSize = 14.sp)
                    Text(stringResource(R.string.res_category,
                        result.category.name.normalizeEnumName()), fontSize = 14.sp)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    val min = result.time / 60
                    val sec = result.time % 60

                    Text(
                        if (min != 0) stringResource(
                            R.string.time_with_min, min, sec
                        ) else stringResource(R.string.time, sec),
                        fontSize = 14.sp)
                    Text(stringResource(R.string.res_ratio, result.correct, result.total), fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun ResultList(
    modifier: Modifier = Modifier,
    results: Flow<PagingData<Result>>,
    onProfileClick: (Long) -> Unit,
) {
    val listState = rememberLazyListState()

    val resultPagingItems = results.collectAsLazyPagingItems()
    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(count = resultPagingItems.itemCount) { index ->
            val result = resultPagingItems[index]
            result?.let {
                ResultItem(it, onProfileClick)
            }
        }
    }
}
