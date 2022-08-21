package uz.kozimjon.covid19app.api

import retrofit2.Response
import retrofit2.http.GET
import uz.kozimjon.covid19app.model.ArticleResponse
import uz.kozimjon.covid19app.model.Coronavirus

interface ApiService {
    @GET("/v2/top-headlines?sources=techcrunch&apiKey=5c68ef6f850c43cfa459f09eb4700285")
    suspend fun getArticles(): Response<ArticleResponse>

    @GET("url")
    suspend fun getData(): Response<Coronavirus>
}