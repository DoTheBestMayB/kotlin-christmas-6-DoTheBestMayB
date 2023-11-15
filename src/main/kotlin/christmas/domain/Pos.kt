package christmas.domain

import christmas.data.*

class Pos {

    fun createReceipt(
        orderTicket: OrderTicket,
        priceDiscounts: List<Benefit>,
        gift: Gift?,
    ): Receipt {
        val benefits = calculateBenefits(priceDiscounts, gift)
        val totalBenefitAmount = calculateTotalBenefitAmount(benefits)
        return Receipt(
            orderMenu = orderTicket.orderedMenu,
            originalTotalPrice = orderTicket.totalOrderPrice(),
            gift = gift,
            benefits = benefits,
            totalBenefitAmount = totalBenefitAmount,
            expectedPayPrice = calculateExpectedPayPrice(orderTicket, priceDiscounts),
            badge = Badge.from(totalBenefitAmount)
        )
    }

    private fun calculateBenefits(priceDiscounts: List<Benefit>, gift: Gift?) = gift?.let {
        priceDiscounts + Benefit(gift.name, gift.getDiscountAmount())
    } ?: priceDiscounts

    private fun calculateTotalBenefitAmount(benefits: List<Benefit>) = benefits.sumOf { it.discountAmount }

    private fun calculateExpectedPayPrice(orderTicket: OrderTicket, priceDiscounts: List<Benefit>) =
        orderTicket.totalOrderPrice() - priceDiscounts.sumOf { it.discountAmount }
}
