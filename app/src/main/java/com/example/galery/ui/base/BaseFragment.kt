package com.example.galery.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.galery.data.database.OnePhotoDatabase
import java.lang.reflect.ParameterizedType
import java.util.*

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireActivity().application
        val dataSource = OnePhotoDatabase.getInstance(application).onePhotoDatabase
        val viewModelFactory = RoomViewModelFactory(dataSource)
        _binding = inflate.invoke(inflater, container, false)
        viewModel =
            ViewModelProvider(
                this, viewModelFactory
            )[getVMClass()]
        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun getVMClass(): Class<VM> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
