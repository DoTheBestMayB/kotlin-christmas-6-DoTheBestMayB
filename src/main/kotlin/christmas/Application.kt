package christmas

import christmas.domain.*

fun main() {
    val planner = createPlanner()
    planner.startPlan()
}

private fun createPlanner(): Planner {
    val outputView = OutputView()
    val inputView = InputView(outputView, Validator())
    val eventManager = EventManager()
    val pos = Pos()
    return Planner(outputView, inputView, pos, eventManager)
}
