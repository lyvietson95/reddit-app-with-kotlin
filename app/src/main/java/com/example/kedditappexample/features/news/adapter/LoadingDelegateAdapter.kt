package com.example.kedditappexample.features.news.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.kedditappexample.R
import com.example.kedditappexample.commons.adapter.ViewType
import com.example.kedditappexample.commons.adapter.ViewTypeDelegateAdapter
import com.example.kedditappexample.commons.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = TurnViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class TurnViewHolder(parent: ViewGroup): RecyclerView.ViewHolder (
        parent.inflate(R.layout.new_item_loading))

}