package christmas.domain

import christmas.data.*

class Pos {

    private val receipts = mutableListOf<Receipt>()

    fun createReceipt(
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
            badge = Badge.from(totalBenefitPrice)
        ).also {
            receipts.add(it)
        }
    }
}