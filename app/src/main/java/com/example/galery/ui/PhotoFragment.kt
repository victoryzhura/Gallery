package com.example.galery.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galery.data.database.OnePhotoDatabase
import com.example.galery.data.entity.OnePhotoItem
import com.example.galery.data.entity.Urls
import com.example.galery.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch


class PhotoFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        val application = requireActivity().application
        val dataSource = OnePhotoDatabase.getInstance(application).onePhotoDatabase
        val viewModelFactory = PhotoViewModelFactory(dataSource)
        photoViewModel =
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
                photoViewModel.insert(it)
            })
        binding.photoRecycler.setHasFixedSize(true)
        binding.photoRecycler.adapter = adapter
        binding.lifecycleOwner = this
        binding.photoRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        lifecycleScope.launch(Dispatchers.IO) {
            photoViewModel.photoListFlow.collectLatest {
                adapter.submitData(it)
                
                Log.d("test1", adapter.snapshot().items.size.toString() + "asfaf")

            }
            while (true) {
                adapter.loadStateFlow.distinctUntilChangedBy {
                    it.refresh
                }.collectLatest {
                    Log.d("test1", adapter.snapshot().items.size.toString() + "asfaf")
                }
                delay(100)
            }
        }


//        photoViewModel.listOfPhotos.observe(viewLifecycleOwner) {
//            adapter.submitList(it)
//        }

    }
}