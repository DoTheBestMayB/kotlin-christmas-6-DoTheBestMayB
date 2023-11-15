package christmas.domain

import christmas.data.Benefit
import christmas.data.OrderTicket
import christmas.util.RetryStrategy
import java.time.DayOfWeek
import java.time.LocalDate

class Planner(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val pos: Pos,
    private val eventManager: EventManager,
) : RetryStrategy() {

    fun startPlan() {
        val date = askVisitDay()
        val orderTicket = askMenu()
        val priceDiscounts = checkPriceDiscount(orderTicket, date)
        val gift = eventManager.checkAvailableGifts(orderTicket)
        val receipt = pos.createReceipt(orderTicket, priceDiscounts, gift)

        outputView.show(date, receipt)
    }

    private fun askVisitDay() = doUntilSuccess { inputView.readDate() }

    private fun askMenu() = doUntilSuccess { inputView.readMenu() }

    private fun checkPriceDiscount(orderTicket: OrderTicket, date: Int): List<Benefit> {
        if (eventManager.canApplyEvent(orderTicket).not()) {
            return emptyList()
        }
        val christmasDiscount = eventManager.checkChristmasDiscount(date)
        val dayDiscount = checkDayDiscount(orderTicket, date)
        val specialDiscount = eventManager.checkSpecialDiscount(date)
        return listOfNotNull(christmasDiscount, dayDiscount, specialDiscount)
    }

    private fun checkDayDiscount(orderTicket: OrderTicket, date: Int) = when (LocalDate.of(2023, 12, date).dayOfWeek) {
        DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY -> eventManager.checkWeekDiscount(
            orderTicket
        )

        DayOfWeek.FRIDAY, DayOfWeek.SATURDAY -> eventManager.checkWeekendDiscount(orderTicket)
    }
}