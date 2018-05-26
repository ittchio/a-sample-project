package com.marvel.marvel.budget.dagger

import com.marvel.marvel.budget.view.BudgetActivity
import dagger.Subcomponent

@Subcomponent(modules = [(BudgetModule::class)])
interface BudgetComponent {

    fun inject(budgetActivity: BudgetActivity)
}
