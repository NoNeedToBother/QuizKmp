package ru.kpfu.itis.quiz.feature.users.domain.paging

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.model.User
import ru.kpfu.itis.quiz.feature.users.domain.repository.UserRepository

class UserPagingSource(
    private val repository: UserRepository,
) : PagingSource<Int, User>() {

    var query: String = ""

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return withContext(Dispatchers.IO) {
            val page = params.key ?: 0

            try {
                val entities = repository.findByUsernameQueryWithPaging(
                    query, params.loadSize, page * params.loadSize
                )

                LoadResult.Page(
                    data = entities,
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (entities.isEmpty()) null else page + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }
}