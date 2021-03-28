package com.atex.medicalapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atex.medicalapp.R
import com.atex.medicalapp.TopicsFragment
import com.atex.medicalapp.data.TopicItem
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.topic_item_layout.view.*

class TopicsAdapter(
    private var topicList: List<TopicItem>,
    private val listener: OnItemClickListener,
    private val context: TopicsFragment
) :
    RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.topic_item_layout,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = topicList[position]
        holder.topicName.text = currentItem.topicName
        holder.topicAuthor.text = currentItem.author
        Glide
            .with(context)
            .load(currentItem.imgUrl)
            .into(holder.topicImg);
    }


    override fun getItemCount(): Int {
        return topicList.size
    }

    fun setTopicList(list: List<TopicItem>) {
        topicList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val topicImg: ImageView = itemView.topic_img
        val topicName: TextView = itemView.topic_name
        val topicAuthor: TextView = itemView.topic_author

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(
                    topicList[position].data,
                    topicList[position].author,
                    topicList[position].topicTitle
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(data: List<String>, author: String, title: String)
    }


}