package com.samsung.android.blog.data

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
data class Post(
    val id: Int,
    val title: String,
    @Json(name = "body") val content: String,
    val userId: Int
) : Serializable
