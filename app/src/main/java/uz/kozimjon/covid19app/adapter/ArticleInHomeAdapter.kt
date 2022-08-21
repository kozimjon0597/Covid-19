package uz.kozimjon.covid19app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.AdapterArticleInHomeBinding
import uz.kozimjon.covid19app.model.ArticleResponse

class ArticleInHomeAdapter : RecyclerView.Adapter<ArticleInHomeAdapter.ArticleInHomeViewHolder>() {
    private var list = ArrayList<ArticleResponse.Article>()
    private lateinit var listener: OnArticleInHomeClickListener

    fun setData(listener: OnArticleInHomeClickListener, list: List<ArticleResponse.Article>) {
        this.list = list as ArrayList<ArticleResponse.Article>
        this.listener = listener
        notifyDataSetChanged()
    }

    interface OnArticleInHomeClickListener {
        fun onArticleClick(article: ArticleResponse.Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleInHomeViewHolder {
        val view = AdapterArticleInHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleInHomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleInHomeViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    // ViewHolder
    inner class ArticleInHomeViewHolder(val binding: AdapterArticleInHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(article: ArticleResponse.Article) {
            binding.tvName.text = article.source?.name ?: "No name"
            binding.tvDesc.text = article.description ?: "No description"
            Glide.with(itemView.context).load(article.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)

            itemView.setOnClickListener {
                listener.onArticleClick(article)
            }
        }
    }
}