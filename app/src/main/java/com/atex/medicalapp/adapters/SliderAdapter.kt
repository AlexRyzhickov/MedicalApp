package com.atex.medicalapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.atex.medicalapp.R

class SliderAdapter(
    var context: Context?
) : PagerAdapter() {

    val slides_images = intArrayOf(
        R.drawable.img_mome,
        R.drawable.img_mum2
    )

    val slide_descs = arrayOf(
        context?.getString(R.string.welcome_string_1),
        context?.getString(R.string.welcome_string_2)
    )

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val layoutInflater =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slide_layout, container, false)

        val slideImageView = view.findViewById<ImageView>(R.id.slideImage)
        val slideDescription = view.findViewById<TextView>(R.id.slide_desc)

        if (position==count-1){
            slideImageView.visibility = View.INVISIBLE
            slideDescription.text = ""
        }else{
            slideImageView.setImageResource(slides_images[position])
            slideDescription.setText(slide_descs.get(position))
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}