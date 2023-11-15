package christmas.domain

import christmas.data.*
import java.time.DayOfWeek
import java.time.LocalDate

class EventManager {

    private val specialDiscountDay = listOf(3, 10, 17, 24, 25, 31)

    fun checkPriceDiscount(orderTicket: OrderTicket, date: Int): List<Benefit> {
        if (canApplyEvent(orderTicket.totalOrderPrice()).not()) {
            return emptyList()
        }
        val christmasDiscount = checkChristmasDiscount(date)
        val dayDiscount = when (LocalDate.of(2023, 12, date).dayOfWeek) {
            DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY -> checkWeekDiscount(
                orderTicket
            )
            DayOfWeek.FRIDAY, DayOfWeek.SATURDAY -> checkWeekendDiscount(orderTicket)
        }
        val specialDiscount = checkSpecialDiscount(date)
        return listOfNotNull(christmasDiscount, dayDiscount, specialDiscount)
    }

    fun checkChristmasDiscount(date: Int): Benefit? {
        if (date > LAST_DAY_OF_CHRISTMAS_D_DAY_DISCOUNT) {
            return null
        }
        val discountAmount =
            CHRISTMAS_D_DAY_DEFAULT_DISCOUNT + (date - CHRISTMAS_D_DAY_DISCOUNT_DAY_CORRECTION_VALUE) * CHRISTMAS_D_DAY_DISCOUNT_UNIT
        return Benefit(CHRISTMAS_D_DAY_DISCOUNT, discountAmount)
    }

    fun checkWeekDiscount(orderTicket: OrderTicket): Benefit {
        var totalCount = 0
        for ((menu, size) in orderTicket.orderedMenu) {
            if (menu.type == MenuType.DESSERT) {
                totalCount += WEEK_DISCOUNT_UNIT * size
            }
        }
        return Benefit(WEEK_DISCOUNT, totalCount)
    }

    fun checkWeekendDiscount(orderTicket: OrderTicket): Benefit {
        var totalCount = 0
        for ((menu, size) in orderTicket.orderedMenu) {
            if (menu.type == MenuType.MAIN) {
                totalCount += WEEKEND_DISCOUNT_UNIT * size
            }
        }
        return Benefit(WEEKEND_DISCOUNT, totalCount)
    }

    fun checkSpecialDiscount(date: Int): Benefit? {
        if (date !in specialDiscountDay) {
            return null
        }
        return Benefit(SPECIAL_DISCOUNT, 1000)
    }

    fun checkAvailableGifts(orderTicket: OrderTicket): Gift? {
        if (orderTicket.totalOrderPrice() < MIN_TOTAL_ORDER_PRICE_FOR_GIFT) {
            return null
        }
        val gift = Menu.from(GIFT_MENU_NAME) ?: throw NoSuchElementException(GIFT_MENU_NO_EXIST)
        return Gift(GIFT_EVENT, gift, GIFT_AMOUNT)
    }

    fun canApplyEvent(totalOrderPrice: Int) = totalOrderPrice >= MIN_ORDER_PRICE_TO_APPLY_EVENT

    companion object {
        private const val WEEKEND_DISCOUNT_UNIT = 2023
        private const val WEEK_DISCOUNT_UNIT = 2023
        private const val MIN_TOTAL_ORDER_PRICE_FOR_GIFT = 120_000
        private const val MIN_ORDER_PRICE_TO_APPLY_EVENT = 10_000
        private const val CHRISTMAS_D_DAY_DEFAULT_DISCOUNT = 1_000
        private const val CHRISTMAS_D_DAY_DISCOUNT_UNIT = 100
        private const val CHRISTMAS_D_DAY_DISCOUNT_DAY_CORRECTION_VALUE = 1
        private const val LAST_DAY_OF_CHRISTMAS_D_DAY_DISCOUNT = 25
        private const val GIFT_MENU_NAME = "샴페인"
        private const val GIFT_AMOUNT = 1
        private const val GIFT_MENU_NO_EXIST = "[ERROR] 기프트 메뉴가 현재 판매 중이지 않습니다."
        private const val CHRISTMAS_D_DAY_DISCOUNT = "크리스마스 디데이 할인"
        private const val WEEK_DISCOUNT = "평일 할인"
        private const val WEEKEND_DISCOUNT = "주말 할인"
        private const val SPECIAL_DISCOUNT = "특별 할인"
        private const val GIFT_EVENT = "증정 이벤트"
    }
}