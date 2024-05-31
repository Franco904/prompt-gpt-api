package com.example.controllers

import com.example.dtos.ReviewPostDto
import com.example.services.PromptService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reviews")
class PromptController(
    private val promptService: PromptService,
) {
    @PostMapping
    fun postReviewPrompt(
        @RequestBody reviewPostDto: ReviewPostDto,
    ): ResponseEntity<String> {
        val reviewCategory = promptService.getReviewCategory(reviewPostDto)

        return ResponseEntity.ok(reviewCategory)
    }
}
