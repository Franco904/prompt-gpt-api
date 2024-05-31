package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PromptGptApiApplication

fun main(args: Array<String>) {
	runApplication<PromptGptApiApplication>(*args)
}
