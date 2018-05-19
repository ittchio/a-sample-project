package com.marvel.marvel.application

import com.marvel.marvel.budget.dagger.BudgetComponent
import com.marvel.marvel.budget.dagger.BudgetModule
import com.marvel.marvel.customview.MarvelRecyclerView
import com.marvel.marvel.detail.DetailActivity
import com.marvel.marvel.main.dagger.MainComponent
import com.marvel.marvel.main.dagger.MainModule
import dagger.Component


@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun with(module: BudgetModule): BudgetComponent

    fun with(module: MainModule): MainComponent

    fun inject(marvelRecyclerView: MarvelRecyclerView)

    fun inject(detailActivity: DetailActivity)
}
