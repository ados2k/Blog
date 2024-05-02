package com.samsung.android.blog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samsung.android.blog.data.Post
import com.samsung.android.blog.data.User
import com.samsung.android.blog.rest.RetrofitInstance
import kotlinx.coroutines.launch

private const val TAG = "DetailViewModel"

class DetailViewModel : ViewModel() {
    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post>
        get() = _post

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _result = MutableLiveData("")
    val result: LiveData<String>
        get() = _result

    fun setPostId(postId: Int) {
        if (_loading.value!!) return
        viewModelScope.launch {
            try {
                _loading.value = true
                val retPost = RetrofitInstance.api.getPost(postId)
                val retUser = RetrofitInstance.api.getUser(retPost.userId)
                _post.value = retPost
                _user.value = retUser
            } catch (e: Exception) {
                Log.e(TAG, "Exception $e")
            } finally {
                _loading.value = false
            }
        }
    }

    fun deletePost() {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.deletePost(_post.value!!.id)
                _result.value = "Success"
            } catch (e: Exception) {
                _result.value = "Error $e"
            }
        }
    }
}