package uz.kozimjon.covid19app.repository

import kotlinx.coroutines.flow.flow
import uz.kozimjon.covid19app.api.ApiService
import javax.inject.Inject
import javax.inject.Named

class ArticleRepository @Inject constructor(@Named("news") private val apiService: ApiService) {
    suspend fun getArticles() = flow { emit(apiService.getArticles()) }
}