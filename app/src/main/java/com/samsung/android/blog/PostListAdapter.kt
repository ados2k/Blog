package com.samsung.android.blog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.samsung.android.blog.data.Post
import com.samsung.android.blog.databinding.ListItemBinding

class PostListAdapter(private val listener: ListItemListener) :
    RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private var postList = emptyList<Post>()

    fun setData(items: List<Post>) {
        postList = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post) {
            binding.post = item
            binding.itemListener = listener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = postList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    interface ListItemListener {
        fun onItemClicked(postId: Int)
    }
}