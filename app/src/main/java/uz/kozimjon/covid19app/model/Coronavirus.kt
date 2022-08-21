package uz.kozimjon.covid19app.model


data class Coronavirus(
    val summaryStats: SummaryStats?,
    val cache: Cache?,
    val rawData: List<CountryData>?
) {
    data class SummaryStats(val global: Global?) {
        data class Global(val confirmed: Int?, val recovered: Int?, val deaths: Int?)
    }

    data class Cache(
        val lastUpdated: String?,
        val expires: String?
    )

    data class CountryData(
        val Country_Region: String?,
        val Last_Update: String?,
        val Confirmed: String?,
        val Deaths: String?,
        val Recovered: String?,
        val Active: String?,
        val Incident_Rate: String?,
        val Case_Fatality_Ratio: String?
    )
}