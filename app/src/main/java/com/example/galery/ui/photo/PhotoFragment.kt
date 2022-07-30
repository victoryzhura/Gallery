package com.example.galery.ui.photo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galery.R
import com.example.galery.databinding.FragmentMainBinding
import com.example.galery.ui.PhotoAdapter
import com.example.galery.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PhotoFragment :
    BaseFragment<FragmentMainBinding, PhotoViewModel>(FragmentMainBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoAdapter(callback = {
            findNavController().navigate(
                PhotoFragmentDirections.actionPhotoFragmentToDetailFragment(
                    it
                )
            )
        },
            clickLike = {
                viewModel.insert(it)
            })
        binding.photoRecycler.setHasFixedSize(true)
        binding.photoRecycler.adapter = adapter
        binding.lifecycleOwner = this
        binding.photoRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.listOfLiked.observe(viewLifecycleOwner) {
            adapter.setListOfLiked(it)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.photoListFlow.collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener {
            when (val currentState = it.refresh) {
                is LoadState.Loading -> {
                }
                is LoadState.Error -> {
                    val extractedException = currentState.error
                    binding.statusImage.setImageResource(R.drawable.connection_error)
                }
            }
        }
    }
}
