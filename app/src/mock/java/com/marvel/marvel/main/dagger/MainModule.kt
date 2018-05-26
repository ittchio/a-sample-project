package com.marvel.marvel.main.dagger

import android.support.v7.app.AppCompatActivity
import com.marvel.marvel.main.model.MainModel
import com.marvel.marvel.main.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val activity: AppCompatActivity) {

    @Provides
    internal fun provideMainPresenter(model: MainModel): MainPresenter {
        return MainPresenter(model, activity.applicationContext)
    }

    @Provides
    internal fun provideMainModel(): MainModel {
        return MockModel()
    }


}
