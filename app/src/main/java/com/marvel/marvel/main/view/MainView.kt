package com.marvel.marvel.main.view

import android.widget.ImageView
import com.marvel.marvel.viewmodel.ComicsViewModel

interface MainView {
    fun onComicsAvailable(results: List<ComicsViewModel>)

    fun startDetailActivity(comicsViewModel: ComicsViewModel, imageView: ImageView)

    fun startBudgetActivity(results: List<ComicsViewModel>)

    fun onError(message: String?)

    fun stopProgress()
}
