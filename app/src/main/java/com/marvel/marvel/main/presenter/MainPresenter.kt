package com.marvel.marvel.main.presenter


import android.content.Context
import android.widget.ImageView
import com.marvel.marvel.customview.MarvelRecyclerView
import com.marvel.marvel.main.model.MainModel
import com.marvel.marvel.main.model.pojos.Result
import com.marvel.marvel.main.view.MainView
import com.marvel.marvel.viewmodel.ComicsViewModel
import com.marvel.marvel.viewmodel.ComicsViewModelRetrofitBuilder
import rx.Observer
import java.util.*

class MainPresenter(
    val model: MainModel,
    private val context: Context?
) : MarvelRecyclerView.Callback {

    private var view: MainView? = null
    private var results: List<ComicsViewModel>? = null

    fun bindView(mainView: MainView) {
        this.view = mainView
    }

    fun unsubscribe() {
        model.unsubscribe()
    }

    fun subscribe() {
        model.subscribe(object : Observer<List<Result>> {

            override fun onCompleted() {
                this@MainPresenter.stopProgress()
            }

            override fun onError(e: Throwable) {
                this@MainPresenter.stopProgress()
                this@MainPresenter.onError(e)
            }

            override fun onNext(results: List<Result>) {
                this@MainPresenter.onComicsAvailable(results)
            }
        })

    }

    private fun onComicsAvailable(results: List<Result>) {
        this.results = results.map {
            ComicsViewModelRetrofitBuilder(
                it,
                context
            ).build()
        }
        view?.onComicsAvailable(results.map {
            ComicsViewModelRetrofitBuilder(
                it,
                context
            ).build()
        })
    }

    private fun onError(e: Throwable) {
        view?.onError(e.message)
    }

    private fun stopProgress() {
        view?.stopProgress()
    }

    override fun onItemClicked(comicsViewModel: ComicsViewModel?, imageView: ImageView) {
        comicsViewModel ?: return
        view?.startDetailActivity(comicsViewModel, imageView)
    }

    fun onBudgetSelected() {
        if (results == null) {
            onError(Exception("Please wait to get the comics"))// TODO the string should be externalized here
            return
        }
        view?.startBudgetActivity(ArrayList(results))
    }
}
