package uz.kozimjon.covid19app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.kozimjon.covid19app.R
import uz.kozimjon.covid19app.databinding.AdapterArchivedBinding
import uz.kozimjon.covid19app.model.SavedArticle

class ArchivedAdapter: RecyclerView.Adapter<ArchivedAdapter.ArchivedViewHolder>() {
    private var list = ArrayList<SavedArticle>()
    private lateinit var listener: OnArchivedClickListener

    fun setData(list: List<SavedArticle>, listener: OnArchivedClickListener) {
        this.list = list as ArrayList<SavedArticle>
        this.listener = listener
        notifyDataSetChanged()
    }

    interface OnArchivedClickListener {
        fun onAClick(article: SavedArticle)
        fun onADeleteClick(article: SavedArticle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivedViewHolder {
        val view = AdapterArchivedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArchivedViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArchivedViewHolder, position: Int) {
        val article = list[position]
        holder.onBind(article)
    }

    override fun getItemCount(): Int = list.size

    // ViewHolder
    inner class ArchivedViewHolder(val binding: AdapterArchivedBinding): RecyclerView.ViewHolder(binding.root)  {
        fun onBind(article: SavedArticle) {
            binding.tvName.text = article.name ?: "No name"
            binding.tvAuthor.text = article.author ?: "No author"
            binding.tvDesc.text = article.author ?: "No description"
            Glide.with(itemView.context).load(article.urlToImage).centerCrop().placeholder(R.drawable.ic_launcher_background).into(binding.ivImage);

            itemView.setOnClickListener {
                listener.onAClick(article)
            }

            binding.ivDelete.setOnClickListener {
                listener.onADeleteClick(article)
                val newList = ArrayList<SavedArticle>()
                list.forEach {
                    if (it != article) {
                        newList.add(it)
                    }
                }

                list = newList
                notifyDataSetChanged()
            }
        }
    }
}