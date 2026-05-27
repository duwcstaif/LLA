package com.example.koreanlearningapp.ui

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.koreanlearningapp.helper.AudioRecorder
import com.example.koreanlearningapp.helper.PronunciationHelper
import com.example.koreanlearningapp.helper.PronunciationResult
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PronunciationScreen() {
    val apiKey = "AIzaSyDHB96Zr9RbO0Y8_5mnD_stijvF93rblGI"

    val context = LocalContext.current
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )
    val pronunciationHelper = remember { PronunciationHelper(apiKey) }
    val audioRecorder = remember { AudioRecorder(context) }

    var isRecording by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<PronunciationResult?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var referenceText by remember { mutableStateOf("Hello, how are you today?") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text("Chấm Điểm Phát Âm", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = referenceText,
            onValueChange = { referenceText = it },
            label = { Text("Câu cần đọc") },
            modifier = Modifier.fillMaxWidth()
        )

        // Nút Ghi âm
        Button(
            onClick = {
                if (!isRecording) {
                    audioRecorder.startRecording()
                    isRecording = true
                } else {
                    val audioFile = audioRecorder.stopRecording()
                    isRecording = false

                    audioFile?.let { file ->
                        isLoading = true
                        scope.launch {
                            try {
                                result = pronunciationHelper.assessPronunciation(file, referenceText)
                                Log.d("KIEU_DU_LIEU", "Giá trị thực tế in ra: $result")
                                Log.d("KIEU_DU_LIEU", "========================================")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            isLoading = false
                        }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                Text("Đang phân tích...")
            } else {
                Text(if (isRecording) "Dừng Ghi Âm" else "Bắt Đầu Ghi Âm")
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        }

        // Hiển thị kết quả
        result?.let { res ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
//                    Text("Điểm: ${res.score}/100", style = MaterialTheme.typography.headlineMedium)
//                    Text("Gợi ý: ${res.suggestions}")
                    Text("Nhận xét: ${res.feedback}")
                }
                Log.d("json",res.feedback)
                Log.d("json",res.suggestions)
            }
        }
    }
}


