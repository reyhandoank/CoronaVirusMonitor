package com.example.coronavirus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.common.Priority
import com.example.coronavirus.BuildConfig.API_KEY
import com.example.coronavirus.BuildConfig.BASE_URL
import com.example.coronavirus.model.CasesResponse
import com.example.coronavirus.model.CountryCases
import com.example.coronavirus.view.CountryCasesView
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CountryCasesViewModel : ViewModel(), CountryCasesView.ViewModel {

    private val countryCaseData = MutableLiveData<ArrayList<CountryCases>>()
    private val compositeDisposable = CompositeDisposable()

    fun getCases() : MutableLiveData<ArrayList<CountryCases>> {
        return countryCaseData
    }

    override fun setCases(api: String, view: CountryCasesView.View) {
        view.showLoading()
        Rx2AndroidNetworking.get(BASE_URL + "coronavirus/cases_by_country.php")
            .setPriority(Priority.LOW)
            .addHeaders("x-rapidapi-key", API_KEY)
            .build()
            .getObjectObservable(CasesResponse::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CasesResponse> {
                override fun onComplete() {
                    view.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: CasesResponse) {
                    countryCaseData.postValue(t.allCountries)
                }

                override fun onError(e: Throwable) {
                    view.errorHandler(e)
                    view.hideLoading()
                }

            })
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }
}