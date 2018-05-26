package com.marvel.marvel.application

import dagger.Module
import dagger.Provides


@Module
class AppModule {

    @Provides
    fun provideImageLoader(): ImageLoader = PicassoImageLoader
}
