package uz.kozimjon.covid19app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.AdapterArticleBinding
import uz.kozimjon.covid19app.model.ArticleResponse
import uz.kozimjon.covid19app.model.Example
import uz.kozimjon.covid19app.model.SavedArticle

class ArticleAdapter: RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private var list = ArrayList<ArticleResponse.Article>()
    private lateinit var listener: OnArticleClickListener

    fun setData(listener: OnArticleClickListener, list: List<ArticleResponse.Article>) {
        this.list = list as ArrayList<ArticleResponse.Article>
        this.listener = listener
        notifyDataSetChanged()
    }

    interface OnArticleClickListener {
        fun onArticleClick(article: ArticleResponse.Article)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = AdapterArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    // ViewHolder
    inner class ArticleViewHolder(val binding: AdapterArticleBinding): RecyclerView.ViewHolder(binding.root)  {
        fun onBind(article: ArticleResponse.Article) {
            binding.tvName.text = article.source?.name ?: "No name"
            binding.tvDesc.text = article.author ?: "No description"
            Glide.with(itemView.context).load(article.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)

            itemView.setOnClickListener {
                listener.onArticleClick(article)
            }
        }
    }
}