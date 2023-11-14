package christmas.domain

import christmas.util.RetryStrategy

class Planner(
    private val inputView: InputView,
    private val pos: Pos,
    private val chef: Chef,
): RetryStrategy() {

    init {
        registerMenuToPos()
    }

    fun startPlan() {
        val day = askVisitDay()
        val menu = askMenu()
    }

    private fun askVisitDay() = doUntilSuccess { inputView.readDate() }

    private fun askMenu() = doUntilSuccess { inputView.readMenu(chef) }

    private fun registerMenuToPos() {
        for (menu in chef.getAllAvailableMenu()) {
            pos.register(menu)
        }
    }
}