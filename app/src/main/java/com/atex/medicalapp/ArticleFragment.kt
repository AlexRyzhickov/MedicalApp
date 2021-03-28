package com.atex.medicalapp

import android.content.Context
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.navArgs
import com.atex.medicalapp.interfaces.NavigationInterface
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.article_fragment.*


class ArticleFragment : Fragment(R.layout.article_fragment) {

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var navigationInterface: NavigationInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            instantiateNavigationInterface(it)
        }

        author.text = args.author
        title.text = args.title

        for (item in args.data){
            if (item.startsWith("https://")){
                val imageView = ImageView(context)
                imageView.setLayoutParams(
                    FrameLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )

                val param = imageView.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(0, 40, 0, 40)
                imageView.layoutParams = param

                Glide
                    .with(this)
                    .load(item)
                    .into(imageView);
                list.addView(imageView)
            }else{
                val text =  TextView(context)

                text.text = item.replace("\\n", "\n")

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    text.justificationMode = JUSTIFICATION_MODE_INTER_WORD
                }
                list.addView(text)
            }
        }

    }

    private fun instantiateNavigationInterface(context: FragmentActivity) {
        navigationInterface = context as NavigationInterface
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigationInterface.showInterstitialAds()
                this.isEnabled = false
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}