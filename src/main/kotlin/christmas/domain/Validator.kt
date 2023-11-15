package christmas.domain

import christmas.data.Menu
import christmas.data.MenuType
import christmas.data.OrderTicket

class Validator {

    private val validBookingDay = 1..31

    fun changeInputToDateNum(input: String): Int {
        if (isNum(input).not()) {
            throw NumberFormatException(VISIT_DAY_IS_NOT_VALID)
        }
        val date = input.toInt()
        require(checkDateInValidBookingDay(date)) {
            VISIT_DAY_IS_NOT_VALID
        }
        return date
    }

    fun changeInputToOrderTicket(input: String): OrderTicket {
        val orderedMenu = splitMenuFromInput(input)

        require(checkOrderNotOnlyContainDrink(orderedMenu)) {
            DRINK_ONLY_ORDER_IS_NOT_ALLOWED
        }
        return OrderTicket(orderedMenu)
    }

    private fun splitMenuFromInput(input: String): LinkedHashMap<Menu, Int> {
        val orderedMenu = linkedMapOf<Menu, Int>()

        for (phrase in input.split(ORDER_INPUT_SPLITTER)) {
            val (name, size) = extractMenuInfoFrom(phrase)
            checkTotalOrderSize(size + orderedMenu.values.sum())

            val menu = createMenuWithValidChecking(name, orderedMenu)
            orderedMenu[menu] = size
        }
        return orderedMenu
    }

    private fun checkOrderNotOnlyContainDrink(orderedMenu: LinkedHashMap<Menu, Int>): Boolean =
        orderedMenu.keys.firstOrNull {
            it.type != MenuType.DRINK
        } != null


    private fun extractMenuInfoFrom(phrase: String): Pair<String, Int> {
        val words = phrase.split(ORDER_MENU_FORMAT_SPLITTER)
        require(words.size == SPLIT_PARTS_COUNT) {
            ORDER_IS_NOT_VALID
        }
        val (name, sizePhrase) = words
        val size = changeSizePhraseToInt(sizePhrase)

        return name to size
    }

    private fun checkTotalOrderSize(size: Int) {
        require(size <= MAX_TOTAL_ORDER_SIZE) {
            ORDER_SIZE_IS_OVER
        }
    }

    private fun createMenuWithValidChecking(name: String, orderedMenu: LinkedHashMap<Menu, Int>): Menu {
        val menu = Menu.from(name)
        if (menu == null || menu in orderedMenu) {
            throw IllegalArgumentException(ORDER_IS_NOT_VALID)
        }
        return menu
    }

    private fun checkDateInValidBookingDay(day: Int) = day in validBookingDay

    private fun isNum(input: String) = input.toIntOrNull() != null

    private fun changeSizePhraseToInt(phrase: String) = phrase.toIntOrNull()?.takeIf {
        it >= MIN_SINGLE_MENU_ORDER_SIZE
    } ?: throw IllegalArgumentException(ORDER_IS_NOT_VALID)


    companion object {
        private const val ORDER_INPUT_SPLITTER = ","
        private const val MAX_TOTAL_ORDER_SIZE = 20
        private const val MIN_SINGLE_MENU_ORDER_SIZE = 1
        private const val SPLIT_PARTS_COUNT = 2
        private const val ORDER_MENU_FORMAT_SPLITTER = "-"
        const val ORDER_SIZE_IS_OVER = "[ERROR] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다."
        const val VISIT_DAY_IS_NOT_VALID = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요."
        const val ORDER_IS_NOT_VALID = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."
        const val DRINK_ONLY_ORDER_IS_NOT_ALLOWED = "[ERROR] 음료만 주문할 수 없습니다. 다시 입력해 주세요."
    }
}
