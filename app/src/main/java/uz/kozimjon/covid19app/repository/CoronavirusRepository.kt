package uz.kozimjon.covid19app.repository

import kotlinx.coroutines.flow.flow
import uz.kozimjon.covid19app.api.ApiService
import javax.inject.Inject
import javax.inject.Named

class CoronavirusRepository @Inject constructor(@Named("coronavirus") private val apiService: ApiService) {
    suspend fun getData() = flow { emit(apiService.getData()) }
}