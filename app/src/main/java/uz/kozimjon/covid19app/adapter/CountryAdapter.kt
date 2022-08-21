package uz.kozimjon.covid19app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.AdapterArticleInHomeBinding
import uz.kozimjon.covid19app.databinding.AdapterCountryBinding
import uz.kozimjon.covid19app.model.ArticleResponse
import uz.kozimjon.covid19app.model.Coronavirus

class CountryAdapter: RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    private var list = ArrayList<Coronavirus.CountryData>()

    fun setData(list: List<Coronavirus.CountryData>) {
        this.list = list as ArrayList<Coronavirus.CountryData>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = AdapterCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    // ViewHolder
    class CountryViewHolder(val binding: AdapterCountryBinding): RecyclerView.ViewHolder(binding.root)  {
        fun onBind(countryData: Coronavirus.CountryData, position: Int) {
            binding.tvNumber.text = "${position + 1}"
            binding.tvName.text = countryData.Country_Region
            binding.tvAffected.text = "Affected: ${countryData.Confirmed}"
            binding.tvRecovered.text = "Recovered: ${(countryData.Confirmed!!.toInt()) - (countryData.Deaths!!.toInt())}"

            binding.tvProtsent.text = ((countryData.Confirmed.toInt() - countryData.Deaths.toInt()) * 100 / countryData.Confirmed.toInt()).toString() + " %"
            binding.progressBar.progress = (countryData.Confirmed.toInt() - countryData.Deaths.toInt()) * 100 / countryData.Confirmed.toInt()
        }
    }
}