package com.samsung.android.blog

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.samsung.android.blog.data.Post

@BindingAdapter("app:items")
fun setList(recyclerView: RecyclerView, items: List<Post>?) {
    items?.let {
        (recyclerView.adapter as PostListAdapter).setData(it)
    }
}

@BindingAdapter("app:img_url")
fun setImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions().override(200, 200))
            .into(imgView)
    }
}