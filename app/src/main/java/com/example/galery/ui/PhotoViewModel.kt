package com.example.galery.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.galery.data.database.OnePhotoDatabaseDao
import com.example.galery.data.entity.OnePhotoItem
import com.example.galery.data.retrofit.OnePhotoApi
import com.example.galery.ui.pagination.PhotoPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PhotoViewModel(private val database: OnePhotoDatabaseDao) : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val photoListFlow: Flow<PagingData<OnePhotoItem>> = Pager(PagingConfig(pageSize = 20)) {
        PhotoPagingSource(OnePhotoApi.retrofitService)
    }.flow.cachedIn(viewModelScope)

    fun insert(onePhoto: OnePhotoItem) {

        val list = photoListFlow.map {
            Log.d("test1", "${it} aksfkaf")
            it.filter { it.isLiked }
        }
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("test1", "${list.count()} aksfkaf")

            if (list.count() == 0) {
                database.insert(onePhoto)
            }

            list.collect { it ->
                Log.d("test1", "$it")
                it.map {
                    Log.d("test1.2", "$it")
                    if (onePhoto.id == it.id) {
                        if (onePhoto.isLiked) {
                            database.update(onePhoto)
                        } else database.clear(onePhoto.id)
                    } else {
                        database.insert(onePhoto)
                        Log.d("test1.3", "'[[[")
                    }
                }
            }
        }
    }

    val listOfPhotos = MutableLiveData<List<OnePhotoItem>>()


    fun getListOfPhotos() {
        listOfPhotos.value = null
        val getPhotoList = OnePhotoApi.retrofitService.getOnePhotoAsync(1)
        coroutineScope.launch {
            try {
                val listResult = getPhotoList.await()
                listOfPhotos.value = listResult
                Log.d("test1", "${listResult.size}")
            } catch (t: Throwable) {
//                listOfPhotos.value = listOf("No Internet")
                t.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}