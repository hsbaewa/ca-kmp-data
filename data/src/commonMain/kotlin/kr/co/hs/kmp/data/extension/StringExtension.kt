package kr.co.hs.kmp.data.extension

import io.ktor.utils.io.core.toByteArray
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import org.kotlincrypto.hash.md.MD5
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Suppress("unused")
object StringExtension {

    fun String.toMD5() = this.toByteArray().toMD5()

    fun ByteArray.toMD5() = runCatching {
        val md5 = MD5()
        md5.update(this)
        val hex = md5.digest().toHexString()
        val padding = "00000000000000000000000000000000".substring(0, 32 - hex.length)
        padding + hex
    }.getOrDefault("00000000000000000000000000000000")

    fun String.toJwtFragmentsOrNull() = runCatching {
        this.replace(" ", "+")
            .replace("_", "/")
            .replace("-", "+")
            .split(".")
            .takeIf { it.size > 2 }
    }.getOrNull()

    @OptIn(ExperimentalTime::class, FormatStringsInDatetimeFormats::class)
    fun String.toISO8601Instant(): Instant? {
        val strDate = substring(0, 19)
        val dateTime = LocalDateTime.parse(strDate, LocalDateTime.Formats.ISO)
            .toInstant(TimeZone.UTC)
        val offsetTime = LocalTime.Format { byUnicodePattern("HH:mm") }
            .runCatching { parse(substring(20, length)) }
            .getOrDefault(LocalTime.fromSecondOfDay(0))
        return when (this[19]) {
            '+' -> dateTime
                .minus(offsetTime.hour, DateTimeUnit.HOUR)
                .minus(offsetTime.minute, DateTimeUnit.MINUTE)

            '-' -> dateTime
                .plus(offsetTime.hour, DateTimeUnit.HOUR)
                .plus(offsetTime.minute, DateTimeUnit.MINUTE)

            else -> dateTime
        }
    }
}

@OptIn(ExperimentalTime::class)
expect fun String.toHttpDateOrNull(): Instant?