package christmas.data

data class ReceiptTestData(
    val orderTicket: OrderTicket,
    val priceDiscounts: List<Benefit>,
    val gift: Gift?,
    val receipt: Receipt,
)