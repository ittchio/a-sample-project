package com.marvel.marvel.budget.presenter


import android.os.Parcelable
import com.marvel.marvel.budget.model.BudgetModel
import com.marvel.marvel.budget.view.BudgetView
import com.marvel.marvel.viewmodel.ComicsViewModel
import java.math.BigDecimal

class BudgetPresenter(private val model: BudgetModel) {
    private var view: BudgetView? = null

    val comics: List<Parcelable>
        get() = model.comics

    fun onGoClicked() {
        model.sortComicsListByPrice(view?.budget)
    }

    fun bind(view: BudgetView) {
        this.view = view
    }

    fun subscribe() {
        model.subscribe(object : SimpleObserver<List<ComicsViewModel>>() {
            override fun onError(e: Throwable?) {
                this@BudgetPresenter.onError(e)
            }

            override fun onNext(comicsListSortedByPrice: List<ComicsViewModel>) {
                this@BudgetPresenter.calculateTotals(comicsListSortedByPrice)
            }
        })
    }

    private fun calculateTotals(comicsList: List<ComicsViewModel>?) {
        val size = comicsList?.size ?: 0
        val total = calculateTotalPrice(comicsList).toString()
        val totalNumberOfPages = calculateTotalNumberOfPages(comicsList)
        view?.setTotals(size, total, totalNumberOfPages)
    }

    private fun calculateTotalNumberOfPages(comics: List<ComicsViewModel>?) = sumPages(comics) ?: 0

    private fun sumPages(comics: List<ComicsViewModel>?) =
        comics?.fold(0,
            { accumulator, model ->
                val count = (model.pageCount ?: 0)
                accumulator + count
            })

    private fun calculateTotalPrice(comics: List<ComicsViewModel>?) =
        sumPrices(comics) ?: BigDecimal.ZERO

    private fun sumPrices(comics: List<ComicsViewModel>?) =
        comics?.fold(BigDecimal.ZERO,
            { accumulator, model ->
                val price = model.price ?: "0.0"
                val augend = BigDecimal(price)
                accumulator.add(augend)
            })

    private fun onError(e: Throwable?) {
        view?.onError(e?.message)
    }

    fun unsubscribe() {
        model.unsubscribe()
    }
}
