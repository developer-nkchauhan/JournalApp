package com.develop.journalapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.develop.journalapp.databinding.JournalRowBinding
import com.develop.journalapp.model.Journal

class JournalAdapter(private val context : Context,private val jList : List<Journal>) : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {

    inner class ViewHolder(private val bind : JournalRowBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bindData(journal : Journal){
            Glide.with(context).load(journal.imageUrl.toUri()).into(bind.journalImageList)
            bind.journalTitleList.text = journal.title
            bind.journalThoughtList.text = journal.thoughts
            bind.journalRowUsername.text = journal.userName
            bind.journalTimestampList.text = journal.timeAdded.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val jrBinding = JournalRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(jrBinding)
    }

    override fun getItemCount(): Int {
        return jList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = jList.get(position)
        holder.bindData(data)
    }
}