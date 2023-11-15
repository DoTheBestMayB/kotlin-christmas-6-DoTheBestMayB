package christmas.domain

import christmas.data.*

class EventManager {

    private val specialDiscountDay = listOf(3, 10, 17, 24, 25, 31)
    private val rangeOfChristmasDiscountDay = 1..25

    fun checkChristmasDiscount(date: Int): Benefit? {
        if (isChristmasDiscountAvailable(date)) {
            return null
        }
        val discountAmount = calculateChristmasDiscountAmount(date)
        return Benefit(CHRISTMAS_D_DAY_DISCOUNT, discountAmount)
    }

    fun checkWeekDiscount(orderTicket: OrderTicket): Benefit? {
        val totalDiscount = orderTicket.totalDiscountAs(MenuType.DESSERT, WEEK_DISCOUNT_UNIT)
        if (totalDiscount == 0) {
            return null
        }
        return Benefit(WEEK_DISCOUNT, totalDiscount)
    }

    fun checkWeekendDiscount(orderTicket: OrderTicket): Benefit? {
        val totalDiscount = orderTicket.totalDiscountAs(MenuType.MAIN, WEEKEND_DISCOUNT_UNIT)
        if (totalDiscount == 0) {
            return null
        }
        return Benefit(WEEKEND_DISCOUNT, totalDiscount)
    }

    fun checkSpecialDiscount(date: Int): Benefit? {
        if (isSpecialDiscountAvailable(date)) {
            return null
        }
        return Benefit(SPECIAL_DISCOUNT, AMOUNT_OF_SPECIAL_DISCOUNT)
    }

    fun checkAvailableGifts(orderTicket: OrderTicket): Gift? {
        if (isGiftAvailable(orderTicket.totalOrderPrice()).not()) {
            return null
        }
        val gift = Menu.from(GIFT_MENU_NAME) ?: throw NoSuchElementException(GIFT_MENU_NO_EXIST)
        return Gift(GIFT_EVENT, gift, GIFT_AMOUNT)
    }

    fun canApplyEvent(orderTicket: OrderTicket) = orderTicket.totalOrderPrice() >= MIN_ORDER_PRICE_TO_APPLY_EVENT

    private fun isChristmasDiscountAvailable(date: Int) = date !in rangeOfChristmasDiscountDay

    private fun calculateChristmasDiscountAmount(date: Int) =
        CHRISTMAS_D_DAY_DEFAULT_DISCOUNT + (date - CHRISTMAS_D_DAY_DISCOUNT_DAY_CORRECTION_VALUE) * CHRISTMAS_D_DAY_DISCOUNT_UNIT

    private fun isSpecialDiscountAvailable(date: Int) = date !in specialDiscountDay

    private fun isGiftAvailable(totalPrice: Int) = totalPrice >= MIN_TOTAL_ORDER_PRICE_FOR_GIFT

    companion object {
        private const val MIN_TOTAL_ORDER_PRICE_FOR_GIFT = 120_000
        private const val MIN_ORDER_PRICE_TO_APPLY_EVENT = 10_000
        private const val CHRISTMAS_D_DAY_DEFAULT_DISCOUNT = 1_000
        private const val CHRISTMAS_D_DAY_DISCOUNT_UNIT = 100
        private const val CHRISTMAS_D_DAY_DISCOUNT_DAY_CORRECTION_VALUE = 1
        private const val GIFT_MENU_NO_EXIST = "[ERROR] 기프트 메뉴가 현재 판매 중이지 않습니다."
        const val WEEKEND_DISCOUNT_UNIT = 2023
        const val WEEK_DISCOUNT_UNIT = 2023
        const val AMOUNT_OF_SPECIAL_DISCOUNT = 1_000
        const val WEEKEND_DISCOUNT = "주말 할인"
        const val GIFT_MENU_NAME = "샴페인"
        const val GIFT_AMOUNT = 1
        const val CHRISTMAS_D_DAY_DISCOUNT = "크리스마스 디데이 할인"
        const val WEEK_DISCOUNT = "평일 할인"
        const val SPECIAL_DISCOUNT = "특별 할인"
        const val GIFT_EVENT = "증정 이벤트"
    }
}