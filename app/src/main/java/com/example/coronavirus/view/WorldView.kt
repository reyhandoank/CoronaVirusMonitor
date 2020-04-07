package com.example.coronavirus.view

import com.example.coronavirus.model.WorldCase

class WorldView {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showData(worldCase: WorldCase)
        fun errorHandler(e: Throwable)
    }

    interface ViewModel {
        fun setData(api: String, view: View)
        fun onDestroy()
    }
}