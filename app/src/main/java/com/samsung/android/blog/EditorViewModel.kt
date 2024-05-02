package com.samsung.android.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samsung.android.blog.data.Post
import com.samsung.android.blog.rest.RetrofitInstance
import kotlinx.coroutines.launch

class EditorViewModel : ViewModel() {
    private lateinit var _post: Post

    private val _postId = MutableLiveData("")
    val postId: LiveData<String>
        get() = _postId

    val title = MutableLiveData("")
    val content = MutableLiveData("")

    private val _result = MutableLiveData("")
    val result: LiveData<String>
        get() = _result

    fun setPost(post: Post) {
        _post = post
        _postId.value = "Post #${post.id}"
        title.value = post.title
        content.value = post.content
    }

    fun updatePost() {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.updatePost(
                    _post.id,
                    Post(_post.id, title.value!!, content.value!!, _post.userId)
                )
                _result.value = "Success"
            } catch (e: Exception) {
                _result.value = "Error $e"
            }
        }
    }
}