package ru.kpfu.itis.quiz.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kpfu.itis.quiz.core.config.Configuration

actual class AppDatabaseBuilderFactory {

    actual fun getDatabaseBuilder(configuration: Configuration): RoomDatabase.Builder<AppDatabase> {
        val appContext = configuration.platformConfiguration.androidContext
        val dbFile = appContext.getDatabasePath("quiz.db")
        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}

