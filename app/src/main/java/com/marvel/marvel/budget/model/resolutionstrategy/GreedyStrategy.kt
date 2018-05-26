package com.marvel.marvel.budget.model.resolutionstrategy

import com.marvel.marvel.viewmodel.ComicsViewModel
import java.math.BigDecimal
import java.util.*

class GreedyStrategy : Strategy {

    override fun sortComicsByPricePerPage(
        comicsList: List<ComicsViewModel>,
        budgetAsString: String?
    ): List<ComicsViewModel> {
        val budget = parseBudget(budgetAsString)
        val pricesPerPageUnsorted = calculatePricePerPage(comicsList)
        val pricesPerPageSorted = sortBasedOnPricePerPage(pricesPerPageUnsorted)
        return addComicsToListIfThereIsEnoughBudget(pricesPerPageSorted, budget)
    }

    private fun addComicsToListIfThereIsEnoughBudget(
        pricePerPages: List<PricePerPage>,
        budget: BigDecimal
    ): List<ComicsViewModel> {
        val comics = mutableListOf<ComicsViewModel>()
        var tempBudget = budget
        for (price in pricePerPages) {
            if (budgetIsEmpty(tempBudget)) {
                break
            }
            if (budgetIsSufficient(tempBudget, price.price)) {
                comics.add(price.comics)
                tempBudget = tempBudget.subtract(price.price)
            }
        }
        return comics.toList()
    }

    private fun sortBasedOnPricePerPage(pricePerPages: List<PricePerPage>): MutableList<PricePerPage> {
        val mutableList = pricePerPages.toMutableList()
        mutableList.sortWith(Comparator { first, second ->
            if (first.pricePerPage != null && second.pricePerPage != null) {
                first.pricePerPage.compareTo(second.pricePerPage)
            } else 0
        })
        return mutableList
    }

    private fun calculatePricePerPage(comicsList: List<ComicsViewModel>): List<PricePerPage> =
        comicsList.filter { it.pageCount != 0 }
            .map {
                PricePerPage(it, it.price, it.pageCount)
            }.toList()

    private fun budgetIsSufficient(budget: BigDecimal, currentPrice: BigDecimal?) =
        budget.subtract(currentPrice).compareTo(BigDecimal.ZERO) >= 0

    private fun budgetIsEmpty(budget: BigDecimal) = budget.compareTo(BigDecimal.ZERO) < 0

    private fun parseBudget(budget: String?): BigDecimal =
        budget?.let {
            try {
                BigDecimal.valueOf(it.toDouble())
            } catch (exception: NumberFormatException) {
                BigDecimal.ZERO
            }
        } ?: BigDecimal.ZERO

    internal class PricePerPage(val comics: ComicsViewModel, price: String?, pages: Int?) {
        val pricePerPage: Double? = calcualtePricePerPage(price, pages)
        val price: BigDecimal? = calculatePrice(price)

        private fun calcualtePricePerPage(priceString: String?, pages: Int?): Double? {
            priceString ?: return null
            if (pages == null || pages == 0) {
                return null
            }
            return try {
                val price = priceString.toDouble()
                (price / (pages))
            } catch (Exception: NumberFormatException) {
                null
            }
        }

        private fun calculatePrice(price: String?): BigDecimal? {
            price ?: return null
            return try {
                BigDecimal(price)
            } catch (exception: NumberFormatException) {
                null
            }
        }
    }
}
