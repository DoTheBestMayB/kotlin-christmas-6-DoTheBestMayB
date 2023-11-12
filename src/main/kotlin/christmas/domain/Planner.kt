package christmas.domain

import christmas.util.RetryStrategy

class Planner(
    private val inputView: InputView,
): RetryStrategy() {

    fun startPlan() {
        val day = askVisitDay()
    }

    private fun askVisitDay() = doUntilSuccess { inputView.readDate() }
}