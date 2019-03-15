package com.example.kedditappexample.commons.adapter

import android.support.v7.widget.RecyclerView
import android.view.SurfaceHolder
import android.view.ViewGroup

interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}