package com.marvel.marvel.budget.model


import com.marvel.marvel.budget.model.resolutionstrategy.Strategy
import com.marvel.marvel.viewmodel.ComicsViewModel
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.Subscription

class BudgetModelImpl(
    override val comics: List<ComicsViewModel>,
    private val strategy: Strategy,
    private val workerThread: Scheduler,
    private val mainThread: Scheduler
) : BudgetModel {

    private var subscription: Subscription? = null
    private var observer: Observer<List<ComicsViewModel>>? = null

    override fun sortComicsListByPrice(budget: String?) {
        val observable = Observable.fromCallable({
            strategy.sortComicsByPricePerPage(comics, budget)
        })
            .subscribeOn(workerThread)
            .observeOn(mainThread)
            .cache()
        observer?.let { subscription = observable.subscribe(observer) }
    }

    override fun subscribe(observer: Observer<List<ComicsViewModel>>) {
        this.observer = observer
    }

    override fun unsubscribe() {
        if (subscription?.isUnsubscribed == false) {
            subscription?.unsubscribe()
        }
    }

}
