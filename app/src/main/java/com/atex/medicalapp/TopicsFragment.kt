package com.atex.medicalapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.atex.medicalapp.adapters.TopicsAdapter
import com.atex.medicalapp.data.TopicItem
import com.atex.medicalapp.interfaces.NavigationInterface
import com.atex.medicalapp.viewmodel.MyViewModel

class TopicsFragment : Fragment(R.layout.topics_fragment), TopicsAdapter.OnItemClickListener {

    private val topicsList = ArrayList<TopicItem>()
    private lateinit var adapter: TopicsAdapter
    private lateinit var viewModel: MyViewModel
    private val args: TopicsFragmentArgs by navArgs()
    private lateinit var navigationInterface: NavigationInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            instantiateNavigationInterface(it)
        }

        adapter = TopicsAdapter(topicsList, this, this)
        val recyclerView: RecyclerView = view.findViewById(R.id.topics_recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setHasFixedSize(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getTopics(args.chapterId).observe(viewLifecycleOwner,
            androidx.lifecycle.Observer<List<TopicItem>> { topics ->
                adapter.setTopicList(topics)
            })
    }

    private fun instantiateNavigationInterface(context: FragmentActivity) {
        navigationInterface = context as NavigationInterface
    }

    override fun onItemClick(data: List<String>, author: String, title: String) {
        val action = TopicsFragmentDirections.actionTopicsFragmentToArticleFragment(
            data.toTypedArray(),
            author,
            title
        )
        findNavController().navigate(action)
    }
}