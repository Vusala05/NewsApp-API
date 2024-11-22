package com.example.news_retrofit

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news_retrofit.databinding.RowBinding

class NewsAdapter(private var articleList: ArrayList<Article>) : RecyclerView.Adapter<NewsAdapter.RowHolder>() {



    class RowHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root)

    fun setFilteredList(articleList: ArrayList<Article>){
        this.articleList = articleList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val article = articleList[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsActivity::class.java)
            intent.putExtra("url", article.url)
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.title.text = article.title
        holder.binding.description.text = article.description
        holder.binding.time.text = article.publishedAt
        holder.binding.author.text = article.author

        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_launcher_background) // Placeholder image while loading
            .error(R.drawable.ic_launcher_background) // Error image if something goes wrong
            .into(holder.binding.image)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newArticles: List<Article>) {
        articleList.clear()
        articleList.addAll(newArticles)
        notifyDataSetChanged()
    }
}
