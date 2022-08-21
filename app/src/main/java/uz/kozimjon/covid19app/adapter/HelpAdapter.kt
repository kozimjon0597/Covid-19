package uz.kozimjon.covid19app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.AdapterHelpBinding
import uz.kozimjon.covid19app.model.Example
import uz.kozimjon.covid19app.model.Help
import uz.kozimjon.covid19app.model.SavedArticle

class HelpAdapter: RecyclerView.Adapter<HelpAdapter.HelpViewHolder>() {
    private var list = ArrayList<Help>()

    fun setData(list: List<Help>) {
        this.list = list as ArrayList<Help>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
        val view = AdapterHelpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HelpViewHolder(view)
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    // ViewHolder
    class HelpViewHolder(val binding: AdapterHelpBinding): RecyclerView.ViewHolder(binding.root)  {
        fun onBind(help: Help) {
            binding.tvName.text = help.name
            binding.tvDesc.text = help.desc
            binding.ivImage.setImageResource(help.image)
        }
    }
}