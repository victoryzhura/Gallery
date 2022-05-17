package com.example.galery.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galery.data.database.OnePhotoDatabase
import com.example.galery.databinding.FragmentFavoritesBinding
import com.example.galery.ui.PhotoAdapter
import com.example.galery.ui.base.RoomViewModel
import com.example.galery.ui.base.RoomViewModelFactory
import com.example.galery.ui.photo.PhotoViewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModelFavorites: RoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)

        val application = requireActivity().application
        val dataSource = OnePhotoDatabase.getInstance(application).onePhotoDatabase
        val viewModelFactory = RoomViewModelFactory(dataSource)
        viewModelFavorites =
            ViewModelProvider(
                this, viewModelFactory
            )[RoomViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoAdapter(callback = {
            findNavController().navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(
                    it
                )
            )
        },
            clickLike = {
                viewModelFavorites.insert(it)
            })
        binding.favoritesRecycler.setHasFixedSize(true)
        binding.favoritesRecycler.adapter = adapter
        binding.lifecycleOwner = this
        binding.favoritesRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModelFavorites.listOfLiked.observe(viewLifecycleOwner) {
            adapter.setListOfLiked(it)
            adapter.submitData(lifecycle, PagingData.from(it))
        }
    }
}