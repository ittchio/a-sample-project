package com.marvel.marvel.budget.view


import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.marvel.marvel.R
import com.marvel.marvel.application.MarvelApplication
import com.marvel.marvel.budget.dagger.BudgetModule
import com.marvel.marvel.budget.presenter.BudgetPresenter
import kotlinx.android.synthetic.main.activity_budget.*
import java.util.*
import javax.inject.Inject

class BudgetActivity : AppCompatActivity(), BudgetView {
    companion object {
        const val COMICS_KEY = "comics_key"
        private const val NUMBER_OF_COMICS_KEY = "number_of_comics_key"
        private const val TOTAL_PRICE_KEY = "total_price_key"
        private const val NUMBER_OF_PAGES_KEY = "number_of_pages_key"
    }

    override val budget: String
        get() = edit.text.toString()

    @Inject
    lateinit var presenter: BudgetPresenter
    private var numberOfComics: Int = 0
    private var totalprice: String = "0"
    private var numberOfpages: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget)
        go.setOnClickListener {
            presenter.onGoClicked()
        }
        (applicationContext as? MarvelApplication)?.appComponent?.with(
            BudgetModule(
                this@BudgetActivity,
                savedInstanceState
            )
        )?.inject(this)
        restoreViewState(savedInstanceState)
        presenter.bind(this@BudgetActivity)
    }

    private fun restoreViewState(savedInstanceState: Bundle?) {
        setNumberOfpages(savedInstanceState?.getInt(NUMBER_OF_PAGES_KEY))
        setNumberOfComics(savedInstanceState?.getInt(NUMBER_OF_COMICS_KEY))
        setTotalPrice(savedInstanceState?.getString(TOTAL_PRICE_KEY))
    }

    override fun onError(message: String?) {
        Snackbar.make(root, message ?: getString(R.string.error), Snackbar.LENGTH_SHORT).show()
    }

    override fun setTotals(numberOfComics: Int, totalPrice: String, numberOfpages: Int) {
        setNumberOfComics(numberOfComics)
        setNumberOfpages(numberOfpages)
        setTotalPrice(totalPrice)
    }

    private fun setTotalPrice(totalPrice: String?) {
        totalPrice ?: return
        this.totalprice = totalPrice
        this.total_price.text = "" + totalPrice + getString(R.string.total_price)
    }

    private fun setNumberOfpages(numberOfpages: Int?) {
        numberOfpages ?: return
        this.numberOfpages = numberOfpages
        this.pages_number.text = numberOfpages.toString() + getString(R.string.pages)
    }

    private fun setNumberOfComics(numberOfComics: Int?) {
        numberOfComics ?: return
        this.numberOfComics = numberOfComics
        this.comics_number.text = numberOfComics.toString() + getString(R.string.comics)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(COMICS_KEY, ArrayList<Parcelable>(presenter.comics))
        outState.putString(TOTAL_PRICE_KEY, totalprice)
        outState.putInt(NUMBER_OF_PAGES_KEY, numberOfpages)
        outState.putInt(NUMBER_OF_COMICS_KEY, numberOfComics)
        super.onSaveInstanceState(outState)
    }
}
