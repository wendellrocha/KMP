package com.wendellrocha.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform