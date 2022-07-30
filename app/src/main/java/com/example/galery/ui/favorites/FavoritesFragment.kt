package com.example.galery.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.galery.databinding.FragmentFavoritesBinding
import com.example.galery.ui.PhotoAdapter
import com.example.galery.ui.base.BaseFragment
import com.example.galery.ui.base.RoomViewModel

class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding, RoomViewModel>(FragmentFavoritesBinding::inflate) {

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
                viewModel.insert(it)
            })
        binding.favoritesRecycler.setHasFixedSize(true)
        binding.favoritesRecycler.adapter = adapter
        binding.lifecycleOwner = this
        binding.favoritesRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.listOfLiked.observe(viewLifecycleOwner) {
            adapter.setListOfLiked(it)
            adapter.submitData(lifecycle, PagingData.from(it))
        }
    }
}
