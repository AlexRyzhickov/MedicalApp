package com.atex.medicalapp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.atex.medicalapp.adapters.ChapterAdapter
import com.atex.medicalapp.data.ChapterItem
import com.atex.medicalapp.interfaces.NavigationInterface
import com.atex.medicalapp.viewmodel.MyViewModel

class HomeFragment : Fragment(R.layout.fragment_home), ChapterAdapter.OnItemClickListener{

    private val chapterList = ArrayList<ChapterItem>()
    private lateinit var adapter: ChapterAdapter
    private lateinit var viewModel: MyViewModel
    private lateinit var navigationInterface: NavigationInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            instantiateNavigationInterface(it)
        }

        adapter = ChapterAdapter(chapterList, this, this)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        recyclerView.setHasFixedSize(true)

        navigationInterface.showBanner()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getChapters().observe(viewLifecycleOwner,
            androidx.lifecycle.Observer<List<ChapterItem>> { chapters ->
                adapter.setChapterList(chapters)
            })
    }

    override fun onItemClick(chapterId: String) {
        navigationInterface.showInterstitialAds()
        val action = HomeFragmentDirections.actionHomeFragmentToTopicsFragment(chapterId)
        findNavController().navigate(action)
    }

    private fun instantiateNavigationInterface(context: FragmentActivity) {
        navigationInterface = context as NavigationInterface
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigationInterface.closeActivity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}