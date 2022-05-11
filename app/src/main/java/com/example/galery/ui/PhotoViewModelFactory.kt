package com.example.galery.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.galery.data.database.OnePhotoDatabaseDao

class PhotoViewModelFactory (
        private val dataSource: OnePhotoDatabaseDao
    ) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
                return PhotoViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
