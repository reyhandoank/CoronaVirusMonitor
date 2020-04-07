package com.example.coronavirus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.common.Priority
import com.example.coronavirus.BuildConfig.API_KEY
import com.example.coronavirus.BuildConfig.BASE_URL
import com.example.coronavirus.model.WorldCase
import com.example.coronavirus.view.WorldView
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WorldCaseViewModel : ViewModel(), WorldView.ViewModel {

    private val worldCaseData = MutableLiveData<WorldCase>()
    private val compositeDisposable = CompositeDisposable()

    fun getData() : MutableLiveData<WorldCase> {
        return worldCaseData
    }

    override fun setData(api: String, view: WorldView.View) {
        view.showLoading()
        Rx2AndroidNetworking.get(BASE_URL + "coronavirus/worldstat.php")
            .setPriority(Priority.LOW)
            .addHeaders("x-rapidapi-key", API_KEY)
            .build()
            .getObjectObservable(WorldCase::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<WorldCase> {
                override fun onComplete() {
                    view.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: WorldCase) {
                    view.showData(t)
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