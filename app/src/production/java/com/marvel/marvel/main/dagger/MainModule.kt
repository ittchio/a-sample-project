package com.marvel.marvel.main.dagger


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.marvel.marvel.application.PicassoImageLoader
import com.marvel.marvel.main.model.Api
import com.marvel.marvel.main.model.ApiRetrofit
import com.marvel.marvel.main.model.MainModel
import com.marvel.marvel.main.model.MainModelRetrofit
import com.marvel.marvel.main.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


@Module
class MainModule(private val activity: AppCompatActivity) {

    @SuppressWarnings("unused")
    @Provides
    internal fun provideMainPresenter(model: MainModel) =
        MainPresenter(model, activity.applicationContext)

    @SuppressWarnings("unused")
    @Provides
    internal fun provideApi(): Api = ApiRetrofit()

    @SuppressWarnings("unused")
    @Provides
    internal fun provideMainModel(api: Api, factory: ViewModelProvider.Factory): MainModel =
        getModel(factory)

    @SuppressWarnings("unused")
    @Provides
    internal fun provideLoader() = PicassoImageLoader

    @SuppressWarnings("unused")
    @Provides
    internal fun provideFactory(api: Api): ViewModelProvider.Factory = RetrofitViewModelFactory(api)

    private fun getModel(factory: ViewModelProvider.Factory): MainModel =
        ViewModelProviders.of(activity, factory).get(MainModelRetrofit::class.java)

    class RetrofitViewModelFactory(val api: Api) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(p0: Class<T>): T =
            MainModelRetrofit(api, Schedulers.newThread(), AndroidSchedulers.mainThread()) as T
    }


}
