package ru.kpfu.itis.quiz.core.util

import android.text.Html

actual fun decodeHtml(html: String): String {
    return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT).toString()
}
