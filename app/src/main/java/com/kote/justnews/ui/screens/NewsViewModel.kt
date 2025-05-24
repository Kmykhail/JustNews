package com.kote.justnews.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kote.justnews.domain.repository.NewsRepository
import kotlinx.coroutines.launch
import com.kote.justnews.data.model.Result
import com.kote.justnews.domain.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor (
    private val apiRepository: NewsRepository
): ViewModel() {
    private val _state = MutableStateFlow<NewsState>(NewsState.Loading)
    val state = _state.asStateFlow()
    fun loadNews(category: String) {
        viewModelScope.launch {
            when (val result = apiRepository.getNews(category)) {
                is Result.Success -> {
                    _state.value = NewsState.Success(result.data)
                    Log.d("WTF", "category: ${category}, news length: ${result.data.size}")
                }
                is Result.Failure -> {
                    _state.value = NewsState.Error(result.exception.message ?: "exception is comming")
                    Log.e("WTF", "error: ${result.exception.message}")
                }
            }
        }
    }
}

sealed class NewsState {
    object Loading: NewsState()
    data class Success(val news: List<News>) : NewsState()
    data class Error(val message: String) : NewsState()
}