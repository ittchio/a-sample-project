package com.marvel.marvel.budget.dagger


import android.content.Intent
import android.os.Bundle
import com.marvel.marvel.budget.model.BudgetModel
import com.marvel.marvel.budget.model.BudgetModelImpl
import com.marvel.marvel.budget.model.resolutionstrategy.GreedyStrategy
import com.marvel.marvel.budget.model.resolutionstrategy.Strategy
import com.marvel.marvel.budget.presenter.BudgetPresenter
import com.marvel.marvel.budget.view.BudgetActivity
import com.marvel.marvel.viewmodel.ComicsViewModel
import dagger.Module
import dagger.Provides
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

@Module
class BudgetModule(budgetActivity: BudgetActivity, savedState: Bundle?) {

    private val comics: List<ComicsViewModel> = getComics(budgetActivity, savedState)

    @SuppressWarnings("unused")
    @Provides
    internal fun provideBudgetPresenter(module: BudgetModel) = BudgetPresenter(module)

    @SuppressWarnings("unused")
    @Provides
    internal fun budgetModuleBudgetModel(strategy: Strategy): BudgetModel =
        BudgetModelImpl(comics, strategy, Schedulers.newThread(), AndroidSchedulers.mainThread())

    @SuppressWarnings("unused")
    @Provides
    internal fun provideStrategy(): Strategy = GreedyStrategy()

    private fun getComics(budgetActivity: BudgetActivity, bundle: Bundle?): List<ComicsViewModel> =
        bundle?.let { getComicsFromBundle(bundle) } ?: getComicsFromIntent(budgetActivity.intent)

    private fun getComicsFromBundle(bundle: Bundle): List<ComicsViewModel> =
        bundle.getParcelableArrayList(BudgetActivity.COMICS_KEY)
                ?: emptyList<ComicsViewModel>()

    private fun getComicsFromIntent(intent: Intent?): List<ComicsViewModel> =
        (intent?.let {
            intent.getParcelableArrayListExtra(BudgetActivity.COMICS_KEY)
                    ?: emptyList<ComicsViewModel>()
        }) ?: emptyList()

}
