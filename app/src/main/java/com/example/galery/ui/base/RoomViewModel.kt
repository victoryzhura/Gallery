package com.example.galery.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galery.data.database.OnePhotoDatabaseDao
import com.example.galery.data.entity.OnePhotoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class RoomViewModel(private val database: OnePhotoDatabaseDao) : ViewModel() {

    var listOfLiked = database.getAllPhoto()

    fun insert(onePhoto: OnePhotoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (onePhoto.isLiked)
                database.insert(onePhoto)
            else database.delete(onePhoto.id)
        }
    }
}