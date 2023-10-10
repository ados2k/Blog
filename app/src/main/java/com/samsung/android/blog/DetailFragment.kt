package com.samsung.android.blog

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import com.samsung.android.blog.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
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