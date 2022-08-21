package uz.kozimjon.covid19app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.AdapterHelpBinding
import uz.kozimjon.covid19app.databinding.AdapterPreventionBinding
import uz.kozimjon.covid19app.model.ArticleResponse

class PreventionAdapter: RecyclerView.Adapter<PreventionAdapter.PreventionViewHolder>() {
    private var list = ArrayList<ArticleResponse.Article>()
    private lateinit var listener: OnPreventionClickListener

    fun setData(listener: OnPreventionClickListener, list: List<ArticleResponse.Article>) {
        this.list = list as ArrayList<ArticleResponse.Article>
        this.listener = listener
        notifyDataSetChanged()
    }

    interface OnPreventionClickListener {
        fun onPreventionClick(article: ArticleResponse.Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreventionViewHolder {
        val view = AdapterPreventionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreventionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreventionViewHolder, position: Int) {
        holder.onBind(list[position])

        holder.itemView.setOnClickListener {
            listener.onPreventionClick(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    // ViewHolder
    inner class PreventionViewHolder(val binding: AdapterPreventionBinding): RecyclerView.ViewHolder(binding.root)  {
        fun onBind(article: ArticleResponse.Article) {
            binding.tvName.text = article.source?.name ?: "No name"
            binding.tvDesc.text = article.description ?: "No description"
            Glide.with(itemView.context).load(article.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)
        }
    }
}