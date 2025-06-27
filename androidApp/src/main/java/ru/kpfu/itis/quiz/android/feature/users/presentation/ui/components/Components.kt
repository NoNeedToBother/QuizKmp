package ru.kpfu.itis.quiz.android.feature.users.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import ru.kpfu.itis.quiz.android.R
import ru.kpfu.itis.quiz.android.core.designsystem.components.EmptyResults
import ru.kpfu.itis.quiz.core.model.User
import ru.kpfu.itis.quiz.android.core.designsystem.components.ProfilePicture

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
    onClick: (Long) -> Unit)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(user.id) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfilePicture(
            uri = user.profilePictureUri,
            modifier = Modifier.size(48.dp),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${user.username}#${user.id}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    users: Flow<PagingData<User>>,
    onUserClick: (Long) -> Unit,
) {
    val listState = rememberLazyListState()

    val userPagingItems = users.collectAsLazyPagingItems()
    if (userPagingItems.itemCount == 0 &&
        userPagingItems.loadState.refresh != LoadState.Loading) EmptyResults()
    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        items(count = userPagingItems.itemCount) { index ->
            val user = userPagingItems[index]
            user?.let {
                UserItem(user = user, onClick = onUserClick)
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.search_users)) },
        trailingIcon = {
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_users))
            }
        }
    )
}
