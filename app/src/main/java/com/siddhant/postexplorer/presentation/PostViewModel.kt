package com.siddhant.postexplorer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddhant.postexplorer.data.PostRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<PostUiState>(PostUiState.Loading)

    val uiState: StateFlow<PostUiState> =
        _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        loadPosts()
    }

    fun loadPosts() {

        if (loadJob?.isActive == true) {
            return
        }

        loadJob = viewModelScope.launch {

            _uiState.value = PostUiState.Loading

            try {

                val posts = repository.getPosts()

                _uiState.value =
                    PostUiState.Success(posts)

            } catch (exception: CancellationException) {

                throw exception

            } catch (exception: IOException) {

                _uiState.value = PostUiState.Error(
                    "Connection failed. Check your internet and retry."
                )

            } catch (exception: Exception) {

                _uiState.value = PostUiState.Error(
                    "Unable to load posts. Please retry."
                )
            }
        }
    }
}