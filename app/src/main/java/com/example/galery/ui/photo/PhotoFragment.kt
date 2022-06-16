package com.example.galery.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galery.R
import com.example.galery.data.database.OnePhotoDatabase
import com.example.galery.databinding.FragmentMainBinding
import com.example.galery.ui.PhotoAdapter
import com.example.galery.ui.base.RoomViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PhotoFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModelPhoto: PhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        val application = requireActivity().application
        val dataSource = OnePhotoDatabase.getInstance(application).onePhotoDatabase
        val viewModelFactory = RoomViewModelFactory(dataSource)
        viewModelPhoto =
            ViewModelProvider(
                this, viewModelFactory
            )[PhotoViewModel::class.java]

        return binding.root
    }

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
                viewModelPhoto.insert(it)
            })
        binding.photoRecycler.setHasFixedSize(true)
        binding.photoRecycler.adapter = adapter
        binding.lifecycleOwner = this
        binding.photoRecycler.layoutManager = GridLayoutManager(requireContext(), 2)


        viewModelPhoto.listOfLiked.observe(viewLifecycleOwner) {
            adapter.setListOfLiked(it)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModelPhoto.photoListFlow.collectLatest {
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
//        photoViewModel.listOfPhotos.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }

    }
}