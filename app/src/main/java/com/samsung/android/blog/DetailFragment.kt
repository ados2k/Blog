package com.samsung.android.blog

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.samsung.android.blog.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_edit -> editPost()
                    R.id.action_delete -> deletePost()
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        viewModel.setPostId(args.postId)
        viewModel.result.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                if (it == "Success") {
                    findNavController().navigate(
                        DetailFragmentDirections.actionDetailFragmentToMainFragment()
                    )
                }
            }
        }

        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    private fun deletePost(): Boolean {
        viewModel.deletePost()
        return true
    }

    private fun editPost(): Boolean {
        findNavController().navigate(
            DetailFragmentDirections.actionDetailFragmentToEditorFragment(viewModel.post.value!!)
        )
        return true
    }
}