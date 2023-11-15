package christmas.data

data class OrderTicket(
    val orderedMenu: LinkedHashMap<Menu, Int>,
) {

    fun totalOrderPrice(): Int {
        var totalPrice = 0
        for ((menu, size) in orderedMenu) {
            totalPrice += menu.price * size
        }
        return totalPrice
    }
}