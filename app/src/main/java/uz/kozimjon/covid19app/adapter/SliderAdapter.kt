package uz.kozimjon.covid19app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.AdapterSliderBinding
import uz.kozimjon.covid19app.model.ArticleResponse
import uz.kozimjon.covid19app.model.Example
import uz.kozimjon.covid19app.model.SavedArticle
import uz.kozimjon.covid19app.model.Slider

class SliderAdapter: RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    private var list = ArrayList<Slider>()

    fun setData(list: List<Slider>) {
        this.list = list as ArrayList<Slider>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = AdapterSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    // ViewHolder
    class SliderViewHolder(val binding: AdapterSliderBinding): RecyclerView.ViewHolder(binding.root)  {
        fun onBind(slider: Slider) {
            binding.tvName.text = slider.name
            binding.tvDesc.text = slider.desc
            binding.ivImage.setImageResource(slider.image)
        }
    }
}