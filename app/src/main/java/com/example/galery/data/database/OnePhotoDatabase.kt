package com.example.galery.data.database

import android.content.Context
import androidx.room.*
import com.example.galery.data.converter.Converters
import com.example.galery.data.entity.OnePhotoItem

@TypeConverters(Converters::class)
@Database(entities = [OnePhotoItem::class], version = 1, exportSchema = false)
abstract class OnePhotoDatabase : RoomDatabase() {

    abstract val onePhotoDatabase: OnePhotoDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: OnePhotoDatabase? = null
        fun getInstance(context: Context):OnePhotoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OnePhotoDatabase::class.java,
                        "photo_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
