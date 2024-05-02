package com.samsung.android.blog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samsung.android.blog.data.Post
import com.samsung.android.blog.rest.RetrofitInstance
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {
    private val _postList = MutableLiveData<List<Post>>(emptyList())
    val postList: LiveData<List<Post>>
        get() = _postList

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    init {
        getPosts()
    }

    fun getPosts() {
        if (_loading.value!!) return
        viewModelScope.launch {
            try {
                _loading.value = true
                val retPosts = RetrofitInstance.api.getPosts(_postList.value!!.size)
                _postList.value = _postList.value!! + retPosts.posts
            } catch (e: Exception) {
                Log.e(TAG, "Exception $e")
            } finally {
                _loading.value = false
            }
        }
    }
}