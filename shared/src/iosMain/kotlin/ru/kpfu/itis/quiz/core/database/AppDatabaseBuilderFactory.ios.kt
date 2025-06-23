package ru.kpfu.itis.quiz.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import ru.kpfu.itis.quiz.core.config.Configuration

actual class AppDatabaseBuilderFactory actual constructor(){

    actual fun getDatabaseBuilder(configuration: Configuration): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = documentDirectory() + "/quiz.db"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
        )
    }

    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}
