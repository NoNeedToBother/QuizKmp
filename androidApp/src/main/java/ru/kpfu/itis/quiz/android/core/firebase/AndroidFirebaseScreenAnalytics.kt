package ru.kpfu.itis.quiz.android.core.firebase

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import ru.kpfu.itis.quiz.core.firebase.FirebaseScreenAnalytics
import ru.kpfu.itis.quiz.core.firebase.ScreenEvent

class AndroidFirebaseScreenAnalytics(
    private val analytics: FirebaseAnalytics
) : FirebaseScreenAnalytics {
    override fun log(event: ScreenEvent) {
        analytics.logEvent(event.toString(), bundleOf())
    }
}
