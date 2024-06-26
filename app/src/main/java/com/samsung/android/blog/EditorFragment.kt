package com.samsung.android.blog

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.samsung.android.blog.databinding.FragmentEditorBinding

class EditorFragment : Fragment() {
    private val viewModel: EditorViewModel by viewModels()
    private val args: EditorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.setPost(args.post)
        viewModel.result.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                if (it == "Success")
                    findNavController().navigateUp()
            }
        }

        val binding = FragmentEditorBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.fragment = this
        return binding.root
    }

    fun updatePost() {
        viewModel.updatePost()
    }

    fun cancel() {
        findNavController().navigateUp()
    }
}