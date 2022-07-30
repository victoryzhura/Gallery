package com.example.galery.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.galery.data.database.OnePhotoDatabaseDao

class RoomViewModelFactory(
    private val dataSource: OnePhotoDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OnePhotoDatabaseDao::class.java)
            .newInstance(dataSource)
    }
}

