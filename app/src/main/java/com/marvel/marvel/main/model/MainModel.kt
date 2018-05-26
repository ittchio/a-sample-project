package com.marvel.marvel.main.model


import com.marvel.marvel.main.model.pojos.Result
import rx.Observer

interface MainModel {

    fun subscribe(observer: Observer<List<Result>>)

    fun unsubscribe()
}
