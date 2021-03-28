package com.atex.medicalapp

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.atex.medicalapp.interfaces.NavigationInterface
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationInterface {

    lateinit var mAdView : AdView
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "artex"
    private lateinit var sharedPref: SharedPreferences

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        if (sharedPref.getBoolean(PREF_NAME, false)) {
            graph.startDestination = R.id.homeFragment
        } else {
            graph.startDestination = R.id.welcomeFragment
            val editor = sharedPref.edit()
            editor.putBoolean(PREF_NAME, true)
            editor.apply()
        }

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }

    override fun closeActivity() {
        this.finish()
    }

    override fun showBanner() {
        adView.visibility = View.VISIBLE
    }

    override fun showInterstitialAds() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }
}