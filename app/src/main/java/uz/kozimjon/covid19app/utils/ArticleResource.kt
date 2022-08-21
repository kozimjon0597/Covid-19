package uz.kozimjon.covid19app.utils

import uz.kozimjon.covid19app.model.ArticleResponse

sealed class ArticleResource {
    object Loading : ArticleResource()
    data class Success(val articleResponse: ArticleResponse) : ArticleResource()
    data class Error(val message: String) : ArticleResource()
}
