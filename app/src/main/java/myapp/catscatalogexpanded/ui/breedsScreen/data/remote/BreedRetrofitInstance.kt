package myapp.catscatalogexpanded.ui.breedsScreen.data.remote

import myapp.catscatalogexpanded.ui.breedsScreen.data.repository.BreedApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BreedRetrofitInstance {
    private const val BASE_URL = "https://api.thecatapi.com/"
    private const val API_KEY = "live_Da7YuJwtX50KM9w0dFY02DpHrumqqu5CZakQcG0KtvzrPvey97R46JjNAV6Dhh5m"

    private val client = OkHttpClient.Builder()
        .addInterceptor ( Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-api-key", API_KEY)
                .build()
            chain.proceed(request)
        })
        .build()

    val retrofitInstance: BreedApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(BreedApi::class.java)
    }
}