package com.example.utils

import io.github.cdimascio.dotenv.dotenv

class DotenvUtils(private val appFileName: String) {
    fun readProperty(property: String): String {
        val envFile = dotenv {
            directory = "src/main/resources"
            filename = appFileName
        }

        return envFile["API_KEY"] ?: throw Exception("Property $property not found")
    }
}
