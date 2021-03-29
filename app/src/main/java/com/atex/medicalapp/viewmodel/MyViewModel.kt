package com.atex.medicalapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.atex.medicalapp.data.ChapterItem
import com.atex.medicalapp.data.TopicItem
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private var chapters: MutableLiveData<List<ChapterItem>> = MutableLiveData<List<ChapterItem>>()
    private var topics: MutableLiveData<List<TopicItem>> = MutableLiveData<List<TopicItem>>()

    fun getChapters(): MutableLiveData<List<ChapterItem>> {
        db.collection("Chapters")
            .addSnapshotListener { value, error ->
                val list: MutableList<ChapterItem> = ArrayList<ChapterItem>()
                for (userDoc in value!!.documentChanges) {
                    val chapter: ChapterItem = userDoc.document.toObject(ChapterItem::class.java)
                    chapter.chapterId = userDoc.document.id
                    list.add(chapter)
                }
                chapters.value = list
            }
        return chapters
    }

    fun getTopics(chapterId: String): MutableLiveData<List<TopicItem>> {
        db.collection("Chapters").document(chapterId).collection("Topics")
            .addSnapshotListener { value, error ->
                val list: MutableList<TopicItem> = ArrayList<TopicItem>()
                for (topic in value!!.documentChanges) {
                    val user: TopicItem = topic.document.toObject(TopicItem::class.java)
                    list.add(user)
                }
                topics.value = list
            }
        return topics
    }

}