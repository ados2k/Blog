package com.samsung.android.blog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samsung.android.blog.data.Post
import com.samsung.android.blog.data.User
import com.samsung.android.blog.rest.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun setPostId(postId: Int) {
        if (_loading.value!!) return
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _loading.postValue(true)
                    val retPost = RetrofitInstance.api.getPost(postId)
                    val retUser = RetrofitInstance.api.getUser(retPost.userId)
                    _post.postValue(retPost)
                    _user.postValue(retUser)
                } catch (e: Exception) {
                    Log.e(TAG, "Exception $e")
                } finally {
                    _loading.postValue(false)
                }
            }
        }
    }

    private val _result = MutableLiveData("")
    val result: LiveData<String>
        get() = _result
    fun deletePost() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    RetrofitInstance.api.deletePost(_post.value!!.id)
                    _result.postValue("Success")
                } catch (e: Exception) {
                    _result.postValue("Error $e")
                }
            }
        }
    }
}