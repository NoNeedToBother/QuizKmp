package ru.kpfu.itis.quiz.core.util

import platform.Foundation.NSAttributedString
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding

actual fun decodeHtml(html: String): String {
    val nsString = html as NSString
    val data = nsString.dataUsingEncoding(NSUTF8StringEncoding) ?: return html
    val attributedString = NSAttributedString(data = data, options = emptyMap(), documentAttributes = null)
    return attributedString.string()
}