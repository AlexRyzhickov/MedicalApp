package com.atex.medicalapp.layoutmanager

import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MyStaggeredGridLayoutManager(var spanCount2 : Int, var orientation2: Int) : StaggeredGridLayoutManager(spanCount2,orientation2) {
    private var scrollable = true

    fun enableScrolling() {
        scrollable = true
    }

    fun disableScrolling() {
        scrollable = false
    }

    override fun canScrollVertically() =
        super.canScrollVertically() && scrollable


    override fun canScrollHorizontally() =
        super.canScrollVertically()
                && scrollable
}