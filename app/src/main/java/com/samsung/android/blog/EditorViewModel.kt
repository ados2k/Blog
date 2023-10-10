package com.samsung.android.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samsung.android.blog.data.Post
import com.samsung.android.blog.rest.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditorViewModel : ViewModel() {
    private lateinit var _post: Post
    private val _postId = MutableLiveData("")
    val postId: LiveData<String>
        get() = _postId
    val title = MutableLiveData("")
    val content = MutableLiveData("")

    fun setPost(post: Post) {
        _post = post
        _postId.value = "Post #${post.id}"
        title.value = post.title
        content.value = post.content
    }

    private val _result = MutableLiveData("")
    val result: LiveData<String>
        get() = _result
    fun updatePost() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    RetrofitInstance.api.updatePost(_post.id,
                        Post(_post.id, title.value!!, content.value!!, _post.userId)
                    )
                    _result.postValue("Success")
                } catch (e: Exception) {
                    _result.postValue("Error $e")
                }
            }
        }
    }
}