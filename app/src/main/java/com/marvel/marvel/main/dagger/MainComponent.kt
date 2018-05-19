package com.marvel.marvel.main.dagger

import com.marvel.marvel.customview.MarvelRecyclerView
import com.marvel.marvel.main.view.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [(MainModule::class)])
interface MainComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(marvelRecyclerView: MarvelRecyclerView)
}
