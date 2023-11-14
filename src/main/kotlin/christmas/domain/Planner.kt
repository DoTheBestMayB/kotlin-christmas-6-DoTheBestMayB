package christmas.domain

import christmas.util.RetryStrategy

class Planner(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val pos: Pos,
    private val menuManager: MenuManager,
): RetryStrategy() {


    fun startPlan() {
        val day = askVisitDay()
        val orderTicket = askMenu()
        val receipt = pos.register(orderTicket, day)

        outputView.show(day, receipt)
    }

    private fun askVisitDay() = doUntilSuccess { inputView.readDate() }

    private fun askMenu() = doUntilSuccess { inputView.readMenu(menuManager) }

}