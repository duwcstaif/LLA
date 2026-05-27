package com.example.koreanlearningapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.koreanlearningapp.models.LoginRequest
import com.example.koreanlearningapp.models.LoginResponse
import com.example.koreanlearningapp.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    // LiveData: Dùng để phát tín hiệu kết quả đăng nhập ra ngoài Activity
    val loginResult = MutableLiveData<Result<LoginResponse>>()

    fun login(username: String, pass: String) {
        // viewModelScope tự động chạy ngầm và tự hủy khi Activity đóng, không lo rò rỉ bộ nhớ
        viewModelScope.launch {
            try {
                val request = LoginRequest(username, pass)
                val response = repository.login(request)

                if (response.isSuccessful && response.body() != null) {
                    // Thành công -> Phát tín hiệu success
                    loginResult.postValue(Result.success(response.body()!!))
                } else {
                    // Thêm "máy soi" để ép XAMPP khai ra lỗi cụ thể
                    val code = response.code()
                    val errorStr = response.errorBody()?.string() ?: "Không có mô tả"
                    loginResult.postValue(Result.failure(Exception("Mã HTTP $code - $errorStr")))
                    Log.d("error", "Mã HTTP $code - $errorStr")
                }
            } catch (e: Exception) {
                // Lỗi sập mạng, không gọi được API, JSON bị vỡ...
                loginResult.postValue(Result.failure(Exception("Crash App: ${e.message}")))
            }
        }
    }

    val registerResult = MutableLiveData<Result<LoginResponse>>()

    fun register(username: String, pass: String) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(username, pass)
                val response = repository.register(request) // Gọi hàm register vừa thêm ở Repository

                if (response.isSuccessful && response.body() != null) {
                    registerResult.postValue(Result.success(response.body()!!))
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Lỗi Server"
                    registerResult.postValue(Result.failure(Exception(errorMsg)))
                }
            } catch (e: Exception) {
                registerResult.postValue(Result.failure(e))
            }
        }
    }

    fun logout() {
        // Đánh lừa LiveData bằng một cái lỗi ảo để nó xóa mất chữ "Thành công" đi
        loginResult.postValue(Result.failure(Exception("Đã đăng xuất")))
    }
}