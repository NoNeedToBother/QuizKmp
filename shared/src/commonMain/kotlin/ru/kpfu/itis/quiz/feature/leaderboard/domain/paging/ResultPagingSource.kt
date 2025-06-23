package ru.kpfu.itis.quiz.feature.leaderboard.domain.paging

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.model.GameMode
import ru.kpfu.itis.quiz.core.model.Result
import ru.kpfu.itis.quiz.feature.leaderboard.domain.repository.ResultRepository

class ResultPagingSource(
    private val repository: ResultRepository,
) : PagingSource<Int, Result>() {

    var gameMode: GameMode = GameMode.BLITZ
    var difficulty: Difficulty? = null
    var category: Category? = null

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return withContext(Dispatchers.IO) {
            val page = params.key ?: 0

            try {
                val entities = repository.getResults(
                    gameMode = gameMode, category = category, difficulty = difficulty,
                    limit = params.loadSize, offset = page * params.loadSize
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