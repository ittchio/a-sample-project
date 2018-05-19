package com.marvel.marvel.budget.model

import android.os.Parcelable

import com.marvel.marvel.viewmodel.ComicsViewModel

import rx.Observer

interface BudgetModel {

    val comics: List<Parcelable>

    fun sortComicsListByPrice(budget: String?)

    fun subscribe(observer: Observer<List<ComicsViewModel>>)

    fun unsubscribe()

}
