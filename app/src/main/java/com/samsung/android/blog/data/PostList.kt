package com.samsung.android.blog.data

data class PostList(
    val posts: List<Post>,
    val total: Int,
    val skip: Int,
    val limit: Int
)