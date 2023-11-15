package christmas.domain

import christmas.util.RetryStrategy

class Planner(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val pos: Pos,
    private val menuManager: MenuManager,
    private val eventManager: EventManager,
): RetryStrategy() {

    fun startPlan() {
        val date = askVisitDay()
        val orderTicket = askMenu()
        val priceDiscounts = eventManager.checkPriceDiscount(orderTicket, date)
        val gift = eventManager.checkAvailableGifts(orderTicket)
        val receipt = pos.createReceipt(orderTicket, priceDiscounts, gift)

        outputView.show(date, receipt)
    }

    private fun askVisitDay() = doUntilSuccess { inputView.readDate() }

    private fun askMenu() = doUntilSuccess { inputView.readMenu(menuManager) }
}