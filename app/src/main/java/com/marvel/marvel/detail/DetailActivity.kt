package com.marvel.marvel.detail


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.transition.Transition
import com.marvel.marvel.R
import com.marvel.marvel.application.ImageLoader
import com.marvel.marvel.application.MarvelApplication
import com.marvel.marvel.main.view.addListener
import com.marvel.marvel.main.view.isLollipopOrAbove
import com.marvel.marvel.main.view.removeListener
import com.marvel.marvel.viewmodel.ComicsViewModel
import kotlinx.android.synthetic.main.detail_activity.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {
    companion object {
        const val COMICS_EXTRA = "comix_extra"
    }

    @Inject
    lateinit var loader: ImageLoader
    private var transitionListener: SimpleTransitionListener? = null
    private var isConfigChange: Boolean = false
    private var callback: ImageLoader.Callback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        supportPostponeEnterTransition()
        (applicationContext as? MarvelApplication)?.appComponent?.inject(this)
        isConfigChange = (savedInstanceState != null)
    }

    override fun onResume() {
        super.onResume()
        loadImageAndColors()
    }

    override fun onPause() {
        super.onPause()
        loader.cancelTask(image)
        callback = null
        if (isLollipopOrAbove)
            @SuppressLint("NewApi") {
                window.removeListener(transitionListener)
            }
    }

    private fun loadImageAndColors() {
        intent?.getParcelableExtra<ComicsViewModel>(COMICS_EXTRA)?.let { model ->
            setImageAndColors(model)
            setTexts(model)
        }
    }

    private fun setTexts(model: ComicsViewModel) {
        number_of_pages.text = model.numberOfPagesAsString
        price.text = model.price
        title_tv.text = model.title
        description.text = model.description
        authors.text = model.authors
    }

    private fun setImageAndColors(model: ComicsViewModel) {
        callback = object : ImageLoader.SimpleCallback() {
            override fun onSuccess() {
                supportStartPostponedEnterTransition()
                val loadedImage = (image.drawable as? BitmapDrawable)?.bitmap
                waitForAnimationAndSetColors(loadedImage)
            }
        }
        loader.loadImage(image, model.imageUrl, callback)
    }


    private fun waitForAnimationAndSetColors(loadedImage: Bitmap?) {
        if (isLollipopOrAbove && !isConfigChange)
            @SuppressLint("NewApi") {
                transitionListener = object : SimpleTransitionListener() {
                    override fun onTransitionEnd(transition: Transition?) {
                        setColors(loadedImage)
                    }
                }
                window.addListener(transitionListener)
            } else {
            setColors(loadedImage)
        }
    }

    private fun setColors(loadedImage: Bitmap?) {
        Palette.from(loadedImage)
            .generate { palette ->
                val swatch = palette?.dominantSwatch ?: return@generate
                swatch.rgb.let {
                    background.setBackgroundColor(it)
                }
                swatch.bodyTextColor.let {
                    number_of_pages.setTextColor(it)
                    price.setTextColor(it)
                    description.setTextColor(it)
                    authors.setTextColor(it)
                }
                swatch.titleTextColor.let {
                    title_tv.setTextColor(it)
                }
            }
    }
}
