package com.atex.medicalapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atex.medicalapp.data.ChapterItem
import com.atex.medicalapp.data.TopicItem
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MyViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FirebaseFirestore.getInstance()
    private var chapters: MutableLiveData<List<ChapterItem>> = MutableLiveData<List<ChapterItem>>()
    private var topics: MutableLiveData<List<TopicItem>> = MutableLiveData<List<TopicItem>>()
    /*var topicId: String = ""*/ /*= MutableLiveData<String>()*/

//    fun setTopicId(input: String) {
//        topicId.setValue(input)
//    }

//    fun getTopicId(): LiveData<String> {
//        return topicId
//    }

    fun getChapters(): MutableLiveData<List<ChapterItem>> {
        db.collection("Chapters")
            .addSnapshotListener { value, error ->
                val list: MutableList<ChapterItem> = ArrayList<ChapterItem>()
                for (userDoc in value!!.documentChanges) {
                    val user: ChapterItem = userDoc.document.toObject(ChapterItem::class.java)
                    user.chapterId = userDoc.document.id
                    list.add(user)
//                    Log.d("TAG",user.chapterId + "df")
//                    Log.d("TAG",userDoc.document.id)
                }
                chapters.value = list
            }
        return chapters
    }

    fun getTopics(chapterId: String): MutableLiveData<List<TopicItem>> {
        db.collection("Chapters").document(chapterId).collection("Topics")
            .addSnapshotListener { value, error ->
                val list: MutableList<TopicItem> = ArrayList<TopicItem>()
                for (userDoc in value!!.documentChanges) {
                    val user: TopicItem = userDoc.document.toObject(TopicItem::class.java)
//                    if (user.data.size!=0) {
//                        Log.d("TAG", user.data[9])
//                    }
                    list.add(user)
                }
                topics.value = list
            }
        return topics
    }

}