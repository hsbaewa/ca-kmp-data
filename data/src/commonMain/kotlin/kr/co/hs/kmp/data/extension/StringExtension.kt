package kr.co.hs.kmp.data.extension

import io.ktor.utils.io.core.toByteArray
import org.kotlincrypto.hash.md.MD5

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
}