package com.marvel.marvel.main.view

import android.os.Build
import android.support.annotation.RequiresApi
import android.transition.Transition
import android.view.Window


val isLollipopOrAbove: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Window.addListener(transitionListener: Transition.TransitionListener?) {
    if (transitionListener != null) {
        this.sharedElementEnterTransition.addListener(transitionListener)
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun Window.removeListener(transitionListener: Transition.TransitionListener?) {
    if (transitionListener != null) {
        this.sharedElementEnterTransition.removeListener(transitionListener)
    }
}