package com.example.galery.data.retrofit

import com.example.galery.data.entity.OnePhotoItem
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.unsplash.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface OnePhotoService {
    @GET("/photos/?client_id=t733oQwqhqagq4H5_aUnY9mSdnZ6tUJRlKVYj-LtaJg&per_page=20")
    fun getOnePhotoAsync(@Query ("page") page: Int): Deferred<List<OnePhotoItem>>
}

object OnePhotoApi {
    val retrofitService: OnePhotoService by lazy {
        retrofit.create(OnePhotoService::class.java)
    }
}