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
    }

    private fun askVisitDay() = doUntilSuccess { inputView.readDate() }

    private fun registerMenuToPos() {
        for (menu in chef.getAllAvailableMenu()) {
            pos.register(menu)
        }
    }
}