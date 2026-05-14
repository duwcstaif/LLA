package com.example.koreanlearningapp.ui

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.koreanlearningapp.models.Vocabulary
import com.example.koreanlearningapp.network.RetrofitClient
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    lessonId: Int,
    lessonNumber: Int, // Đã sửa: Nhận đúng biến số
    onBackClick: () -> Unit
) {
    var vocabList by remember { mutableStateOf<List<Vocabulary>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(lessonId) {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.instance.getLessonDetail(lessonId)
                if (response.isSuccessful && response.body() != null) {
                    vocabList = response.body()!!
                } else {
                    errorMessage = "Lỗi Server: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = "Lỗi Code: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bài $lessonNumber", fontWeight = FontWeight.Bold) }, // Đã sửa: Hiện chữ Bài 1, Bài 2...
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Chạm vào thẻ để xem nghĩa", color = Color.Gray, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 50.dp))
            } else if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red, modifier = Modifier.padding(top = 50.dp))
            } else if (vocabList.isEmpty()) {
                Text("Chưa có từ vựng nào.", modifier = Modifier.padding(top = 50.dp))
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(vocabList) { vocab ->
                        FlashcardItem(vocab)
                    }
                }
            }
        }
    }
}

@Composable
fun FlashcardItem(vocab: Vocabulary) {
    var isFlipped by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val tts = remember {
        var textToSpeech: TextToSpeech? = null
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.KOREAN
            }
        }
        textToSpeech
    }

    Card(
        modifier = Modifier.fillMaxWidth().height(140.dp).clickable { isFlipped = !isFlipped },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFlipped) MaterialTheme.colorScheme.secondaryContainer else Color.White
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            IconButton(
                onClick = { tts?.speak(vocab.koreanWord, TextToSpeech.QUEUE_FLUSH, null, null) },
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Listen", tint = Color.Blue)
            }

            if (!isFlipped) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = vocab.koreanWord, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "[ ${vocab.pronunciation ?: "..."} ]", fontSize = 16.sp, color = Color.DarkGray)
                }
            } else {
                Text(
                    text = vocab.vietnameseMeaning, fontSize = 26.sp, fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer, textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}