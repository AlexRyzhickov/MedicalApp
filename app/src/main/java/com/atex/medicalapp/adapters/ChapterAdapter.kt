package com.atex.medicalapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atex.medicalapp.HomeFragment
import com.atex.medicalapp.R
import com.atex.medicalapp.data.ChapterItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.chapter_item_layout.view.*

class ChapterAdapter(
    private var chapterList: List<ChapterItem>,
    private val listener: OnItemClickListener,
    private val context: HomeFragment
) :
    RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.chapter_item_layout,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = chapterList[position]
        holder.chapterName.text = currentItem.chapterName
        Glide
            .with(context)
            .load(currentItem.imgUrl)
            .into(holder.img);

    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    fun setChapterList(list: List<ChapterItem>) {
        chapterList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val img: ImageView = itemView.chapter_img
        val chapterName: TextView = itemView.chapter_name

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(chapterList[position].chapterId)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(chapterId: String)
    }
}