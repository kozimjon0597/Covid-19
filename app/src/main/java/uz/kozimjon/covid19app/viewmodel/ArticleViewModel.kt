package uz.kozimjon.covid19app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import uz.kozimjon.covid19app.model.ArticleResponse
import uz.kozimjon.covid19app.repository.ArticleRepository
import uz.kozimjon.covid19app.utils.ArticleResource
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleRepository: ArticleRepository) : ViewModel() {
    private val stateFlow = MutableStateFlow<ArticleResource>(ArticleResource.Loading)

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            // Loading
            stateFlow.value = ArticleResource.Loading

            val flow = articleRepository.getArticles()
            flow.catch {
                // Error
                stateFlow.value = ArticleResource.Error(it.message ?: "")
            }.collect {
                if (it.isSuccessful) {
                    // Data
                    stateFlow.value = ArticleResource.Success(it.body()!!)
                } else {
                    // Error
                    stateFlow.value = ArticleResource.Error("Error")
                }
            }
        }
    }

    fun getArticles(): StateFlow<ArticleResource> {
        return stateFlow
    }

}