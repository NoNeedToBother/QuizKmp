package ru.kpfu.itis.quiz.feature.questions.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.kpfu.itis.quiz.Res
import ru.kpfu.itis.quiz.core.model.Category
import ru.kpfu.itis.quiz.core.model.Difficulty
import ru.kpfu.itis.quiz.core.util.normalizeEnumName
import ru.kpfu.itis.quiz.feature.questions.data.exception.UnknownParameterException
import ru.kpfu.itis.quiz.feature.questions.data.mapper.mapResponse
import ru.kpfu.itis.quiz.feature.questions.data.model.CategoryResponse
import ru.kpfu.itis.quiz.feature.questions.data.model.QuestionResponse
import ru.kpfu.itis.quiz.feature.questions.domain.model.Questions
import ru.kpfu.itis.quiz.feature.questions.domain.repository.QuestionRepository

class QuestionRepositoryImpl(
    private val client: HttpClient
) : QuestionRepository {
    override suspend fun getCategoryCode(category: Category): Int {
        return withContext(Dispatchers.IO) {
            val response = client.get("/api_category.php").body<CategoryResponse>()
            val categories = response.info
            var res: Int? = null
            for (categoryInfo in categories) {
                val name = categoryInfo.name
                val categoryName = getCategoryByName(name)
                if (category == categoryName) {
                    res = categoryInfo.id
                }
            }
            res ?: throw UnknownParameterException(Res.string.unknown_parameter)
        }
    }

    override suspend fun getQuestions(
        amount: Int,
        difficulty: Difficulty,
        category: Int
    ): Questions {
        return withContext(Dispatchers.IO) {
            val response = client.get("/api.php") {
                parameter("amount", amount)
                parameter("difficulty", difficulty.name.normalizeEnumName().lowercase())
                parameter("category", category)
            }
            val questions = response.body<QuestionResponse>()
            mapResponse(questions)
        }
    }

    private fun getCategoryByName(categoryName: String): Category? {
        return when (categoryName) {
            GENERAL_CATEGORY_NAME -> Category.GENERAL
            BOOKS_CATEGORY_NAME -> Category.BOOK
            FILM_CATEGORY_NAME -> Category.FILM
            MUSIC_CATEGORY_NAME -> Category.MUSIC
            TV_CATEGORY_NAME -> Category.TV
            VIDEO_GAMES_CATEGORY_NAME -> Category.VIDEO_GAMES
            SPORTS_CATEGORY_NAME -> Category.SPORTS
            GEOGRAPHY_CATEGORY_NAME -> Category.GEOGRAPHY
            HISTORY_CATEGORY_NAME -> Category.HISTORY
            ANIMALS_CATEGORY_NAME -> Category.ANIMALS
            else -> null
        }
    }

    companion object {
        const val GENERAL_CATEGORY_NAME = "General Knowledge"
        const val BOOKS_CATEGORY_NAME = "Entertainment: Books"
        const val FILM_CATEGORY_NAME = "Entertainment: Film"
        const val MUSIC_CATEGORY_NAME = "Entertainment: Music"
        const val TV_CATEGORY_NAME = "Entertainment: Television"
        const val VIDEO_GAMES_CATEGORY_NAME = "Entertainment: Video Games"
        const val SPORTS_CATEGORY_NAME = "Sports"
        const val GEOGRAPHY_CATEGORY_NAME = "Geography"
        const val HISTORY_CATEGORY_NAME = "History"
        const val ANIMALS_CATEGORY_NAME = "Animals"
    }
}
