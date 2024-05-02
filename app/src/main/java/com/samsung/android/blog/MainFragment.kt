package com.samsung.android.blog

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samsung.android.blog.databinding.FragmentMainBinding

class MainFragment : Fragment(), PostListAdapter.ListItemListener {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: PostListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        with(binding.recyclerView) {
            setHasFixedSize(true)
            val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
            addItemDecoration(divider)
        }

        adapter = PostListAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && !recyclerView.canScrollVertically(1)
                ) {
                    viewModel.getPosts()
                }
            }
        })

        return binding.root
    }

    override fun onItemClicked(postId: Int) {
        //Toast.makeText(context, "$postId clicked", Toast.LENGTH_SHORT).show()
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToDetailFragment(postId)
        )
    }
}