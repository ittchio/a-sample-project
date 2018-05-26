package com.marvel.marvel.main.model

import android.arch.lifecycle.ViewModel
import com.marvel.marvel.main.model.pojos.Comics
import com.marvel.marvel.main.model.pojos.Result
import rx.Observer
import rx.Scheduler
import rx.Subscription
import rx.subjects.ReplaySubject

class MainModelRetrofit(
    api: Api,
    newThreadScheduler: Scheduler,
    mainThreadScheduler: Scheduler
) : ViewModel(), MainModel {
    private var subscription: Subscription? = null
    private val subject: ReplaySubject<Comics> = ReplaySubject.create()

    init {
        api.comics
            .subscribeOn(newThreadScheduler)
            .observeOn(mainThreadScheduler)
            .subscribe(subject)
    }

    override fun subscribe(observer: Observer<List<Result>>) {
        subscription =
                subject
                    .map { it?.data?.results ?: emptyList() }
                    .flatMapIterable { it }
                    .toList()
                    .subscribe(observer)
    }

    override fun unsubscribe() {
        subscription?.unsubscribe()
        subscription = null

    }
}
