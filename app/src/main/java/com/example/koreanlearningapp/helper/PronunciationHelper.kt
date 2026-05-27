package com.example.koreanlearningapp.helper

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class PronunciationHelper(private val apiKey: String) {

    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = apiKey
    )

    suspend fun assessPronunciation(
        audioFile: File,
        referenceText: String
    ): PronunciationResult {
        return withContext(Dispatchers.IO) {
            val audioBytes = audioFile.readBytes()
            val prompt = """
                    Phân tích phát âm câu mẫu: "$referenceText"
                    Yêu cầu:
                    1. Chỉ trả về đúng 3 dòng theo định dạng sau:
                    DIEM: [số từ 0-100]
                    NHANXET: [nội dung nhận xét]
                    GOIY: [nội dung gợi ý]
                    2. Không viết thêm bất kỳ lời dẫn nào khác.
                """.trimIndent()

            val response = model.generateContent(
                content {
                    text(prompt)
                    blob("audio/wav", audioBytes)
                }
            )

            val jsonText = response.text ?: throw Exception("Không nhận được phản hồi từ Gemini")

            PronunciationResult(
                score = extractScore(jsonText),
                feedback = extractValue(jsonText, "feedback"),
                suggestions = extractValue(jsonText, "suggestions")
            )
        }
    }

    private fun extractScore(json: String): Int {
        return json.substringAfter("\"score\":").substringBefore(",").trim().toIntOrNull() ?: 70
    }

    private fun extractValue(json: String, key: String): String {
        return json.substringAfter("\"$key\":\"").substringBeforeLast("\"").trim()
    }
}

data class PronunciationResult(
    val score: Int,
    val feedback: String,
    val suggestions: String
)