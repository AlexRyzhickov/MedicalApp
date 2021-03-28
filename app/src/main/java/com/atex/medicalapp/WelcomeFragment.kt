package com.atex.medicalapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.atex.medicalapp.adapters.SliderAdapter
import kotlinx.android.synthetic.main.welcome_fragment.*

class WelcomeFragment : Fragment(R.layout.welcome_fragment) {

    private lateinit var mSlideViewPager: ViewPager
    private lateinit var sliderAdapter: SliderAdapter
    private var mDotLayout: LinearLayout? = null

    private var mDots = arrayOfNulls<TextView>(3)

    private lateinit var mNextBtn: Button
    private lateinit var mBackBtn: Button
    private lateinit var mPushBtn: Button
    private var mCurrentPage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSlideViewPager = view.findViewById(R.id.SlideViewPager)
        mDotLayout = view.findViewById(R.id.dotsLayout)

        sliderAdapter = SliderAdapter(context)

        mNextBtn = view.findViewById(R.id.nextBtn)
        mBackBtn = view.findViewById(R.id.prevBtn)
        mPushBtn = view.findViewById(R.id.pushBtn)


        mSlideViewPager.setAdapter(sliderAdapter)
        addDotsIndicator(0)
        mSlideViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                addDotsIndicator(position)

                mCurrentPage = position

                if (position == 0) {
                    mNextBtn.setEnabled(true)
                    mBackBtn.setEnabled(false)
                    mBackBtn.setVisibility(View.INVISIBLE)
                    mNextBtn.setText("Вперёд")
                    mBackBtn.setText("")
                } else if (position == mDots.size - 1) {
                    mNextBtn.setEnabled(true)
                    mBackBtn.setEnabled(true)
                    mBackBtn.setVisibility(View.VISIBLE)
                    mNextBtn.setText("Старт")
                    mBackBtn.setText("Назад")
                    ratingBar.visibility = View.VISIBLE
                    mPushBtn.visibility = View.VISIBLE
//                    mSlideViewPager.visibility = View.INVISIBLE
                } else {
                    mNextBtn.setEnabled(true)
                    mBackBtn.setEnabled(true)
                    mBackBtn.setVisibility(View.VISIBLE)
                    mNextBtn.setText("Вперёд")
                    mBackBtn.setText("Назад")
                    ratingBar.visibility = View.INVISIBLE
                    mPushBtn.visibility = View.INVISIBLE
//                    mSlideViewPager.visibility = View.VISIBLE
                }
            }
        })

        mNextBtn.setOnClickListener {
            if (mNextBtn.text.toString() == "Старт") {
                val action  = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
                findNavController().navigate(action)
            }
            mSlideViewPager.setCurrentItem(mCurrentPage + 1)
        }
        mBackBtn.setOnClickListener { mSlideViewPager.setCurrentItem(mCurrentPage - 1) }
        pushBtn.setOnClickListener {
            if (ratingBar.rating >= 4){
                Toast.makeText(context,"Переходим в Google Play",Toast.LENGTH_SHORT).show()
                val packageName = activity?.packageName
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
                }
            }else{
                Toast.makeText(context,"Спасибо за ваш отзыв",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addDotsIndicator(position: Int) {
        mDots = arrayOfNulls(3)
        mDotLayout!!.removeAllViews()
        for (i in mDots.indices) {
            mDots[i] = TextView(context)
            mDots[i]!!.text = Html.fromHtml("&#8226")
            mDots[i]!!.textSize = 35f
            mDots[i]!!.setTextColor(resources.getColor(R.color.colorTransparentTransition))
            mDotLayout!!.addView(mDots[i])
        }
        if (mDots.size > 0) {
            mDots[position]!!.setTextColor(resources.getColor(R.color.black))
        }
    }
}