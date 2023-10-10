package com.samsung.android.blog.rest

import com.samsung.android.blog.data.Post
import com.samsung.android.blog.data.PostList
import com.samsung.android.blog.data.User
import retrofit2.http.*

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(@Query("skip") skip: Int = 0, @Query("limit") limit: Int = 10): PostList

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") postId: Int, @Body post: Post)

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") postId: Int)
}