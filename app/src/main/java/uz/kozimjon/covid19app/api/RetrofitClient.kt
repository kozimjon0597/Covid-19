package uz.kozimjon.covid19app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.kozimjon.covid19app.utils.Constants

object RetrofitClient {
    private const val baseUrl = Constants.BASE_URL_CORONAVIRUS
    private val gsonConverterFactory = GsonConverterFactory.create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory).build()

    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}