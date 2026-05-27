package com.example.koreanlearningapp.helper


import android.content.Context
import android.media.MediaRecorder
import java.io.File
import java.io.IOException

class AudioRecorder(private val context: Context) {

    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    var isRecording = false

    fun startRecording(): File? {
        val fileName = "pronunciation_${System.currentTimeMillis()}.wav"
        audioFile = File(context.cacheDir, fileName)

        mediaRecorder = MediaRecorder().apply {
            try {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioSamplingRate(16000)
                setOutputFile(audioFile?.absolutePath)

                prepare()
                start()
                isRecording = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return audioFile
    }

    fun stopRecording(): File? {
        mediaRecorder?.apply {
            try {
                stop()
                release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        mediaRecorder = null
        isRecording = false
        return audioFile
    }

    fun getCurrentFile(): File? = audioFile
}