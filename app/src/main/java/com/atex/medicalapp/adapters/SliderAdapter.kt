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
        R.drawable.eat,
        R.drawable.sleep
    )

    val slide_headings = arrayOf(
        "EAT", "SLEEP"
    )

    val slide_descs = arrayOf(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
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
        val slideHeading = view.findViewById<TextView>(R.id.slide_heading)
        val slideDescription = view.findViewById<TextView>(R.id.slide_desc)

        if (position==count-1){
            slideImageView.visibility = View.INVISIBLE
            slideHeading.text = ""
            slideDescription.text = ""
        }else{
            slideImageView.setImageResource(slides_images[position])
            slideHeading.text = slide_headings[position]
            slideDescription.setText(slide_descs.get(position))
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

}