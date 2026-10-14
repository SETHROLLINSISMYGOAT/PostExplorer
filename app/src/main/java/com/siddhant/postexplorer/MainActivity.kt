package com.siddhant.postexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.siddhant.postexplorer.data.PostRepository
import com.siddhant.postexplorer.data.RetrofitClient
import com.siddhant.postexplorer.presentation.PostScreen
import com.siddhant.postexplorer.presentation.PostViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val factory = viewModelFactory {

            initializer {

                PostViewModel(
                    repository = PostRepository(
                        api = RetrofitClient.api
                    )
                )
            }
        }

        val postViewModel =
            ViewModelProvider(this, factory)[PostViewModel::class.java]

        setContent {

            MaterialTheme {

                PostScreen(
                    viewModel = postViewModel
                )
            }
        }
    }
}