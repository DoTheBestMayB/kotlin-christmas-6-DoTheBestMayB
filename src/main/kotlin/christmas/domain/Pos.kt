package christmas.domain

import christmas.data.*
import java.time.DayOfWeek
import java.time.LocalDate

class Pos(private val eventManager: EventManager) {

    private val receipts = mutableListOf<Receipt>()

    fun register(orderTicket: OrderTicket, date: Int): Receipt {
        val priceDiscounts = checkPriceDiscount(orderTicket, date)
        val gift = eventManager.checkAvailableGifts(orderTicket)

        return createReceipt(orderTicket, priceDiscounts, gift).also {
            receipts.add(it)
        }
    }

    private fun checkPriceDiscount(orderTicket: OrderTicket, date: Int): List<Benefit> {
        if (eventManager.canApplyEvent(orderTicket.totalOrderPrice()).not()) {
            return emptyList()
        }
        val christmasDiscount = eventManager.checkChristmasDiscount(date)
        val dayDiscount = when (LocalDate.of(2023, 12, date).dayOfWeek) {
            DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY -> eventManager.checkWeekDiscount(
                orderTicket
            )

            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY -> eventManager.checkWeekendDiscount(orderTicket)
        }
        val specialDiscount = eventManager.checkSpecialDiscount(date)
        return listOfNotNull(christmasDiscount, dayDiscount, specialDiscount)
    }

    private fun createReceipt(
        orderTicket: OrderTicket,
        priceDiscounts: List<Benefit>,
        gift: Gift?,
    ): Receipt {
        val benefits = gift?.let {
            priceDiscounts + Benefit(gift.name, gift.getDiscountAmount())
        } ?: priceDiscounts
        val totalBenefitPrice = benefits.sumOf { it.discountAmount }
        return Receipt(
            orderMenu = orderTicket.orderedMenu,
            originalTotalPrice = orderTicket.totalOrderPrice(),
            gift = gift,
            benefits = benefits,
            totalBenefitPrice = totalBenefitPrice,
            grandTotal = orderTicket.totalOrderPrice() - priceDiscounts.sumOf { it.discountAmount },
            badge = BADGE.from(totalBenefitPrice)
        )
    }
}