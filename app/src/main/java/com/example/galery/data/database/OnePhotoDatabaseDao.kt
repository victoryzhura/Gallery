package com.example.galery.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.galery.data.entity.OnePhotoItem

@Dao
interface OnePhotoDatabaseDao {

    @Insert
    suspend fun insert(photo: OnePhotoItem)

    @Query("DELETE from photo_table WHERE id = :key")
    suspend fun clear(key: String)

    @Update
    suspend fun update(photo: OnePhotoItem)
}


