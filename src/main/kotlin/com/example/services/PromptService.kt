package com.example.services

import com.example.dtos.ReviewPostDto
import com.example.utils.DotenvUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.stereotype.Service

@Service
class PromptService(
    private val httpClient: OkHttpClient = OkHttpClient(),
) {
    val openaiApiKey: String
        get() = DotenvUtils(appFileName = "openai.env").readProperty("API_KEY")

    fun getReviewCategory(userReview: ReviewPostDto): String {
        val requestBody = buildReviewCategoryPayload(
            score = userReview.score.toString(),
            review = userReview.review,
        ).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(URL)
            .addHeader("Authorization", "Bearer $openaiApiKey")
            .post(requestBody)
            .build()

        return httpClient.newCall(request).execute().use { response ->
            val responseBody = response.body?.string() ?: "Failed to get response from ChatGPT"
            responseBody
        }
    }

    private fun buildReviewCategoryPayload(
        score: String,
        review: String,
    ) = """
            {
              "model": "$MODEL",
              "messages": [
                {
                  "role": "system",
                  "content": "Você receberá avaliações de usuários de um aplicativo móvel em produção, no formato [ <pontuacao> ] <avaliacao>.\n\nO seu trabalho será classificar cada uma das avaliações com base na pontuacao e o texto da avaliação fornecido, e retornar como saída apenas uma das seguintes categorias abaixo, no formato <categoria>.\n    \n    - Promotor\n    - Detrator\n    - Neutro\n    - Melhoria\n    - Atenção\n    - Bug\n    - Sumiço de dados\n    - Outro\n\nConsidere que <pontuacao> é um valor inteiro de 1 a 5, em que 1 representa o grau de satisfação 'Muito insatisfeito' e que 5 representa o grau de satisfação 'Muito satisfeito'. O conteúdo <avaliacao> é apresentado na forma de texto não estruturado"
                },
                {
                  "role": "user",
                  "content": "[ $score ] $review"
                }
              ],
              "max_tokens": 24
            }
        """.trimIndent()

    companion object {
        private const val URL = "https://api.openai.com/v1/chat/completions"
        private const val MODEL = "gpt-3.5-turbo"
    }
}
