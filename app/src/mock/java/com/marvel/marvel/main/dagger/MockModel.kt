package com.marvel.marvel.main.dagger


import com.marvel.marvel.main.model.MainModel
import com.marvel.marvel.main.model.pojos.Result
import rx.Observable
import rx.Observer

class MockModel : MainModel {

    override fun subscribe(observer: Observer<List<Result>>) {
        observable?.subscribe(observer)
    }

    override fun unsubscribe() {

    }

    companion object {
        var observable: Observable<List<Result>>? = null
    }
}
