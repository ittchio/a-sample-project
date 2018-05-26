package com.marvel.marvel.main.view

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.marvel.marvel.R
import com.marvel.marvel.application.MarvelApplication
import com.marvel.marvel.budget.view.BudgetActivity
import com.marvel.marvel.customview.MarvelRecyclerView
import com.marvel.marvel.detail.DetailActivity
import com.marvel.marvel.main.dagger.MainModule
import com.marvel.marvel.main.presenter.MainPresenter
import com.marvel.marvel.viewmodel.ComicsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {
    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as? MarvelApplication)?.appComponent?.with(MainModule(this))
            ?.inject(this)
        presenter.bindView(this@MainActivity)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        recycler.adapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.budget -> {
                presenter.onBudgetSelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onComicsAvailable(results: List<ComicsViewModel>) {
        recycler.setCallback(object : MarvelRecyclerView.Callback {
            override fun onItemClicked(comicsViewModel: ComicsViewModel?, imageView: ImageView) {
                presenter.onItemClicked(comicsViewModel, imageView)
            }
        })
        recycler.onComicsAvailable(results)
    }

    @SuppressLint("NewApi")
    override fun startDetailActivity(comicsViewModel: ComicsViewModel, imageView: ImageView) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.COMICS_EXTRA, comicsViewModel)
        if (isLollipopOrAbove) {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                imageView,
                getString(R.string.transition)
            )
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    override fun startBudgetActivity(results: List<ComicsViewModel>) {
        val intent = Intent(this@MainActivity, BudgetActivity::class.java)
        intent.putParcelableArrayListExtra(BudgetActivity.COMICS_KEY, ArrayList(results))
        startActivity(intent)
    }

    override fun onError(error: String?) {
        val message = error ?: getString(R.string.error)
        Snackbar.make(recycler, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun stopProgress() {
        progress.visibility = View.INVISIBLE
    }


}
