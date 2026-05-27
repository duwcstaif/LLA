package com.example.koreanlearningapp.network // Sửa lại package nếu cần

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Cấu trúc của một từ đã học
data class LearnedWord(
    val word: String,
    val contextSentence: String // Lưu luôn cái câu chứa từ đó để nhớ ngữ cảnh
)

object HistoryManager {
    private const val PREFS_NAME = "korean_app_history"
    private const val KEY_WORDS = "learned_words"

    // Hàm lưu từ mới vào kho
    fun saveWord(context: Context, word: String, sentence: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        // Lấy danh sách cũ ra
        val json = prefs.getString(KEY_WORDS, "[]")
        val type = object : com.google.gson.reflect.TypeToken<MutableList<LearnedWord>>() {}.type
        val words: MutableList<LearnedWord> = gson.fromJson(json, type)

        // Nếu từ này chưa học (chưa có trong list) thì mới thêm vào đầu danh sách
        if (words.none { it.word == word }) {
            words.add(0, LearnedWord(word, sentence))
            prefs.edit().putString(KEY_WORDS, gson.toJson(words)).apply()
        }
    }

    // Hàm lấy toàn bộ danh sách ra để hiển thị
    fun getLearnedWords(context: Context): List<LearnedWord> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(KEY_WORDS, "[]")
        val type = object : com.google.gson.reflect.TypeToken<List<LearnedWord>>() {}.type
        return gson.fromJson(json, type)
    }

    // Hàm xóa lịch sử (Nếu muốn làm thêm nút Reset)
    fun clearHistory(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
    }
}