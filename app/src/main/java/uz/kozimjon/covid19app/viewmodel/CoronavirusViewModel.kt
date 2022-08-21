package uz.kozimjon.covid19app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.kozimjon.covid19app.repository.CoronavirusRepository
import uz.kozimjon.covid19app.utils.CoronavirusResource
import javax.inject.Inject

@HiltViewModel
class CoronavirusViewModel @Inject constructor(private val coronavirusRepository: CoronavirusRepository) : ViewModel() {
    private val stateFlow = MutableStateFlow<CoronavirusResource>(CoronavirusResource.Loading)

    init {
        fetchCoronavirus()
    }

    private fun fetchCoronavirus() {
        viewModelScope.launch {
            // Loading
            stateFlow.value = CoronavirusResource.Loading

            val flow = coronavirusRepository.getData()
            flow.catch {
                // Error
                stateFlow.value = CoronavirusResource.Error(it.message ?: "")
            }.collect {
                if (it.isSuccessful) {
                    // Data
                    stateFlow.value = CoronavirusResource.Success(it.body()!!)
                } else {
                    // Error
                    stateFlow.value = CoronavirusResource.Error("Error")
                }
            }
        }
    }

    fun getData(): StateFlow<CoronavirusResource> {
        return stateFlow
    }
}