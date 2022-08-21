package uz.kozimjon.covid19app.utils

import uz.kozimjon.covid19app.model.Coronavirus

sealed class CoronavirusResource {
    object Loading : CoronavirusResource()
    data class Success(val coronavirus: Coronavirus) : CoronavirusResource()
    data class Error(val message: String) : CoronavirusResource()
}