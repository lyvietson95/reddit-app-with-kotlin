package com.example.kedditappexample.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.kedditappexample.R
import com.example.kedditappexample.commons.RedditNewsItem
import com.example.kedditappexample.commons.adapter.ViewType
import com.example.kedditappexample.commons.adapter.ViewTypeDelegateAdapter
import com.example.kedditappexample.commons.adapter.getFriendlyTime
import com.example.kedditappexample.commons.inflate
import com.example.kedditappexample.commons.loadImg
import kotlinx.android.synthetic.main.news_item.view.*

class NewDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.news_item)) {

        fun bind(item: RedditNewsItem) = with(itemView) {
            //Picasso.with(itemView.context).load(item.thumbnail).into(img_thumbnail)
            img_thumbnail.loadImg(item.thumbnail)
            description.text = item.title
            author.text = item.author
            comments.text = "${item.numComments} comments"
            time.text = item.created.getFriendlyTime()
        }
    }

}