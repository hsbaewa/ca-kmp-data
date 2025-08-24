package kr.co.hs.kmp.data.extension

import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.timeZoneForSecondsFromGMT
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(markerClass = [ExperimentalTime::class])
actual fun String.toHttpDateOrNull(): Instant? {
    if (isEmpty()) return null

    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = "EEE, dd MMM yyyy HH:mm:ss 'GMT'"
    dateFormatter.locale = NSLocale("en_US_POSIX")
    dateFormatter.timeZone = NSTimeZone.timeZoneForSecondsFromGMT(0)
    return dateFormatter
        .dateFromString(this)
        ?.timeIntervalSince1970?.toLong()
        ?.let { Instant.fromEpochMilliseconds(it) }
}