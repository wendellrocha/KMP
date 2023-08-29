package com.wendellrocha.kmp

import com.wendellrocha.kmp.data_classes.RocketLaunch
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class Greeting {
    private val platform: Platform = getPlatform()

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun greet(): String {
        return "Hello, ${platform.name}!\n${fetchSpaceXLaunches()}"
    }

    @Throws(Exception::class)
    suspend fun fetchSpaceXLaunches(): String {
        val rockets: List<RocketLaunch> =
            httpClient.get("https://api.spacexdata.com/v4/launches").body()
        val lastSuccessLaunch = rockets.last { it.launchSuccess == true }
        return "The last successful launch was ${lastSuccessLaunch.launchDateUTC} 🚀"
    }
}