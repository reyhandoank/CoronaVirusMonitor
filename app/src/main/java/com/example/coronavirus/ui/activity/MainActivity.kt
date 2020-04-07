package com.example.coronavirus.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronavirus.BuildConfig
import com.example.coronavirus.R
import com.example.coronavirus.model.CountryCases
import com.example.coronavirus.model.WorldCase
import com.example.coronavirus.ui.adapter.ListCountryAdapter
import com.example.coronavirus.view.CountryCasesView
import com.example.coronavirus.view.WorldView
import com.example.coronavirus.viewmodel.CountryCasesViewModel
import com.example.coronavirus.viewmodel.WorldCaseViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), WorldView.View, CountryCasesView.View {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = ViewModelProviders.of(this).get(WorldCaseViewModel::class.java)
        data.getData().observe(this, getData)
        data.setData(BuildConfig.API_KEY, view = this)

        val cases = ViewModelProviders.of(this).get(CountryCasesViewModel::class.java)
        cases.getCases().observe(this, getCases)
        cases.setCases(BuildConfig.API_KEY, view = this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = caseAdapter
        }

        //AdMob
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //Test
        /*mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                Toast.makeText(this@MainActivity, "Muncul gan", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(errorCode : Int) {
                Toast.makeText(this@MainActivity, "Gagal gan", Toast.LENGTH_SHORT).show()
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }*/
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        txt_total.visibility = View.VISIBLE
        card_view.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    override fun showData(worldCase: WorldCase) {
        progress_bar.visibility = View.GONE
        tv_total.text = worldCase.totalCases
    }

    override fun errorHandler(e: Throwable) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
    }

    private val getData = Observer<WorldCase> {
        it?.totalCases
    }

    private val getCases = Observer<ArrayList<CountryCases>> {
        if (it != null) {
            caseAdapter.setCases(it)
        }
    }

    private val caseAdapter by lazy {
        ListCountryAdapter(this)
    }
}
