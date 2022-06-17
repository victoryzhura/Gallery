package com.example.galery.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.galery.data.entity.OnePhotoItem
import com.example.galery.ui.PhotoAdapter
import retrofit2.http.GET

@Dao
interface OnePhotoDatabaseDao {


    @Insert
    suspend fun insert(photo: OnePhotoItem)

    @Query("DELETE from photo_table WHERE id = :key")
    suspend fun delete(key: String)

    @Query("SELECT * FROM photo_table")
    fun getAllPhoto(): LiveData<List<OnePhotoItem>>
}


