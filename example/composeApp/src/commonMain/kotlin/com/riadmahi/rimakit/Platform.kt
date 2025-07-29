package com.riadmahi.rimakit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform