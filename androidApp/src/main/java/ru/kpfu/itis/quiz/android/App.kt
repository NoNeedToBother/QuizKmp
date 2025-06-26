package ru.kpfu.itis.quiz.android

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import ru.kpfu.itis.quiz.android.config.initCommon

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.initialize(applicationContext)
        initCommon()
    }
}