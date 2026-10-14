package com.siddhant.postexplorer.data

class PostRepository(
    private val api: PostApi
) {

    suspend fun getPosts(): List<Post> {

        val response = api.getPosts()

        if (!response.isSuccessful) {
            throw IllegalStateException(
                "Server error: HTTP ${response.code()}"
            )
        }

        return response.body()
            ?: throw IllegalStateException(
                "The server returned no response body"
            )
    }
}