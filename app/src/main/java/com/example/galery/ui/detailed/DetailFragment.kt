package com.example.galery.ui.detailed

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.galery.databinding.FragmentDetailScreanBinding
import com.example.galery.ui.utility.DownloadsImage
import com.example.galery.ui.utility.showToast


class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailScreanBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailScreanBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailedPhoto = args.onePhoto
        binding.download.setOnClickListener {
            downloadImage(args.onePhoto.urls?.full)
        }
        binding.likeDetail.setOnClickListener {
            args.onePhoto.isLiked = !args.onePhoto.isLiked
            binding.detailedPhoto = args.onePhoto
        }
    }

    private fun downloadImage(ImageUrl: String?) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                123
            )
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                123
            )
            requireContext().showToast("Need Permission to access storage for Downloading Image")
        } else {
            binding.progressBar.visibility = View.VISIBLE
            requireContext().showToast("Downloading Image...")
            DownloadsImage(callback = {
                MediaScannerConnection.scanFile(
                    requireContext(),
                    arrayOf<String>(it.absolutePath),
                    null
                ) { path, uri -> }
            }, textCallback = {
                binding.progressBar.visibility = View.INVISIBLE
                requireContext().showToast(it)
            }).execute(ImageUrl)
        }
    }
}



