package kr.co.hs.kmp.data.extension

import io.ktor.utils.io.core.toByteArray
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.parse
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
    fun String.toISO8601Instant(): Instant = toISO8601DateTimeComponents()
        .run { toLocalDateTime().toInstant(toUtcOffset()) }

    fun String.toISO8601DateTimeComponents(): DateTimeComponents = DateTimeComponents.parse(
        this,
        DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET
    )

    fun String.toISO8601LocalDateTime(): LocalDateTime =
        toISO8601DateTimeComponents().toLocalDateTime()
}

@OptIn(ExperimentalTime::class)
expect fun String.toHttpDateOrNull(): Instant?