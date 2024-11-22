package com.example.news_retrofit

import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET("everything?q=bitcoin&apiKey=ac4a364362af4061b056efb58c3de37d")
    fun getData(): Call<ApiResponse>
}
