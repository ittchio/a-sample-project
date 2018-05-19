package com.marvel.marvel.budget.view

interface BudgetView {

    val budget: String

    fun onError(message: String?)

    fun setTotals(size: Int, total: String, numberOfpages: Int)
}
