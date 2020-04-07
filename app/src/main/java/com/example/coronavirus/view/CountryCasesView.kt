package com.example.coronavirus.view

class CountryCasesView {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun errorHandler(e: Throwable)
    }

    interface ViewModel {
        fun setCases(api: String, view:View)
        fun onDestroy()
    }
}