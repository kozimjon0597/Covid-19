package uz.kozimjon.covid19app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.AdapterSavedArticleBinding
import uz.kozimjon.covid19app.model.SavedArticle
import uz.kozimjon.covid19app.paper_db.Archived

class SavedArticleAdapter: RecyclerView.Adapter<SavedArticleAdapter.SavedArticleViewHolder>() {
    private var list = ArrayList<SavedArticle>()
    private lateinit var listener: OnSavedArticleClickListener

    fun setData(list: List<SavedArticle>, listener: OnSavedArticleClickListener) {
        this.list = list as ArrayList<SavedArticle>
        this.listener = listener
        notifyDataSetChanged()
    }

    interface OnSavedArticleClickListener {
        fun onSAClick(article: SavedArticle)
        fun onSADeleteClick(article: SavedArticle)
        fun onSAAClick(article: SavedArticle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedArticleViewHolder {
        val view = AdapterSavedArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedArticleViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    // ViewHolder
    inner class SavedArticleViewHolder(val binding: AdapterSavedArticleBinding): RecyclerView.ViewHolder(binding.root)  {
        fun onBind(article: SavedArticle) {
            binding.tvName.text = article.name ?: "No name"
            binding.tvAuthor.text = article.author ?: "No author"
            binding.tvDesc.text = article.author ?: "No description"
            Glide.with(itemView.context).load(article.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage)

            if (Archived.existArchived(article)) {
                binding.ivAddArchive.setImageResource(R.drawable.ic_baseline_library_add_check_24)
            } else {
                binding.ivAddArchive.setImageResource(R.drawable.ic_outline_library_add_check_24)
            }

            itemView.setOnClickListener {
                listener.onSAClick(article)
            }

            binding.ivDelete.setOnClickListener {
                listener.onSADeleteClick(article)
                val newList = ArrayList<SavedArticle>()
                list.forEach {
                    if (it != article) {
                        newList.add(it)
                    }
                }

                list = newList
                notifyDataSetChanged()
            }

            binding.ivAddArchive.setOnClickListener {
                if (Archived.existArchived(article)) {
                    binding.ivAddArchive.setImageResource(R.drawable.ic_outline_library_add_check_24)
                    Archived.removeArchived(article)
                } else {
                    binding.ivAddArchive.setImageResource(R.drawable.ic_baseline_library_add_check_24)
                    Archived.addArchived(article)
                }

                listener.onSAAClick(article)
                notifyDataSetChanged()
            }
        }
    }
}