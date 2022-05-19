package com.example.galery.ui.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.galery.data.database.OnePhotoDatabaseDao
import com.example.galery.data.entity.OnePhotoItem
import com.example.galery.data.retrofit.OnePhotoApi
import com.example.galery.ui.base.RoomViewModel
import com.example.galery.ui.pagination.PhotoPagingSource
import kotlinx.coroutines.flow.Flow


class PhotoViewModel(database: OnePhotoDatabaseDao) : RoomViewModel(database) {

    val photoListFlow: Flow<PagingData<OnePhotoItem>> = Pager(PagingConfig(pageSize = 20)) {
        PhotoPagingSource(OnePhotoApi.retrofitService)
    }.flow.cachedIn(viewModelScope)


//    private val viewModelJob = Job()
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    val listOfPhotos = MutableLiveData<List<OnePhotoItem>>()

//    fun getListOfPhotos() {
//        listOfPhotos.value = null
//        val getPhotoList = OnePhotoApi.retrofitService.getOnePhotoAsync(1)
//        coroutineScope.launch {
//            try {
//                val listResult = getPhotoList.await()
//                listOfPhotos.value = listResult
//                Log.d("test1", "${listResult.size}")
//            } catch (t: Throwable) {
////                listOfPhotos.value = listOf("No Internet")
//                t.printStackTrace()
//            }
//        }
//    }

//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }
}