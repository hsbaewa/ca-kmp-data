package kr.co.hs.kmp.data.sample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform