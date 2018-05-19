package com.marvel.marvel.main.presenter

import com.marvel.marvel.main.model.MainModel
import com.marvel.marvel.main.model.pojos.Result
import com.marvel.marvel.main.view.MainView
import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.isA
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import rx.Observer

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {
    private val MESSAGE = "message"
    @Mock
    lateinit var model: MainModel
    @Mock
    lateinit var view: MainView
    @InjectMocks
    lateinit var presenter: MainPresenter
    private val captor: KArgumentCaptor<Observer<List<Result>>> =
        argumentCaptor()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        presenter.bindView(view)
    }

    @Test
    fun when_onPause_then_unsubscribes() {
        // WHEN
        presenter.unsubscribe()
        // THEN
        verify<MainModel>(model).unsubscribe()
    }

    @Test
    fun when_onResume_then_subscribes() {
        // WHEN
        presenter.subscribe()
        // THEN
        verify<MainModel>(model).subscribe(isA())
    }

    @Test
    fun when_onNext_then_viewIsUpdated() {
        // WHEN
        presenter.subscribe()
        verify<MainModel>(model).subscribe(captor.capture())
        captor.firstValue.onNext(emptyList())
        // THEN
        verify<MainView>(view).onComicsAvailable(anyList())
    }

    @Test
    fun when_onError_then_userIsInformed() {
        // WHEN
        presenter.subscribe()
        verify<MainModel>(model).subscribe(captor.capture())
        captor.firstValue.onError(Exception(MESSAGE))
        // THEN
        verify<MainView>(view).onError(MESSAGE)
    }

    @Test
    fun when_onError_then_progressIsStopped() {
        // WHEN
        presenter.subscribe()
        verify<MainModel>(model).subscribe(captor.capture())
        captor.firstValue.onError(Exception(MESSAGE))
        // THEN
        verify<MainView>(view).stopProgress()
    }

    @Test
    fun when_ooCompleted_then_progressIsStopped() {
        // WHEN
        presenter.subscribe()
        verify<MainModel>(model).subscribe(captor.capture())
        captor.firstValue.onCompleted()
        // THEN
        verify<MainView>(view).stopProgress()
    }

}