package com.marvel.marvel.budget.model.resolutionstrategy

import com.marvel.marvel.viewmodel.ComicsViewModel

interface Strategy {
    fun sortComicsByPricePerPage(
        list: List<ComicsViewModel>,
        budget: String?
    ): List<ComicsViewModel>
}
