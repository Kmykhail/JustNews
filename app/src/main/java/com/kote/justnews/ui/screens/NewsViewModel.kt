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

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            apiRepository.getTopHeadlines().collect{ result ->
                when (result) {
                    is Result.Failure -> {
                        _state.value = NewsState.Error(result.exception.message ?: "")
                    }
                    is Result.Success<List<News>> -> {
                        _state.value = NewsState.Success(currentNews = result.data)
                    }
                }
            }
        }
    }
}

sealed class NewsState {
    object Initial: NewsState()
    object Loading: NewsState()
    data class Error(val message: String) : NewsState()

    data class Success(
        val currentNews: List<News>,
    ) : NewsState()
}