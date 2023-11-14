package christmas.domain

import camp.nextstep.edu.missionutils.Console
import christmas.data.MENU_TYPE
import christmas.data.Menu
import christmas.data.OrderTicket

class InputView(
    private val outputView: OutputView,
    private val validator: Validator,
) {

    fun readDate(): Int {
        outputView.show(REQUEST_INPUT_VISIT_DAY, true)
        val input = read()

        if (validator.isNum(input).not()) {
            throw NumberFormatException(VISIT_DAY_IS_NOT_VALID)
        } else if (validator.checkDate(input.toInt()).not()) {
            throw IllegalArgumentException(VISIT_DAY_IS_NOT_VALID)
        }
        return input.toInt()
    }

    fun readMenu(chef: Chef): OrderTicket {
        outputView.show(REQUEST_INPUT_MENU, true)
        val input = read()
        return changeInputToMenu(input, chef)
    }

    private fun changeInputToMenu(input: String, chef: Chef): OrderTicket {
        val orderedMenu = hashMapOf<Menu, Int>()
        var isNotOnlyDrink = false

        for (phrase in input.split(ORDER_INPUT_SPLITTER)) {
            checkValidity(phrase, ORDER_IS_NOT_VALID) { validator.checkOrderFormat(it) }

            val (name, sizePhrase) = phrase.split(ORDER_MENU_FORMAT_SPLITTER)
            val size = changeSizePhraseToInt(sizePhrase)

            checkValidity(size + orderedMenu.values.sum(), ORDER_SIZE_IS_OVER) { validator.checkTotalOrderSize(it) }

            val menu = requireNotNull(chef.getMenu(name)) {
                ORDER_IS_NOT_VALID
            }
            if (menu in orderedMenu) {
                throw IllegalArgumentException(ORDER_IS_NOT_VALID)
            }
            isNotOnlyDrink = isNotOnlyDrink || (menu.type != MENU_TYPE.DRINK)
            orderedMenu[menu] = size
        }
        require(isNotOnlyDrink) {
            DRINK_ONLY_ORDER_IS_NOT_ALLOWED
        }
        return OrderTicket(orderedMenu)
    }

    private fun changeSizePhraseToInt(phrase: String): Int {
        checkValidity(phrase, ORDER_IS_NOT_VALID) { validator.checkSingleMenuSize(it) }
        return phrase.toInt()
    }

    private fun <T> checkValidity(parameter: T, exceptionPhrase: String, operation: (T) -> Boolean) {
        if (operation(parameter).not()) {
            throw IllegalArgumentException(exceptionPhrase)
        }
    }

    private fun read() = Console.readLine()

    companion object {
        private const val ORDER_INPUT_SPLITTER = ","
        private const val REQUEST_INPUT_VISIT_DAY = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"
        private const val REQUEST_INPUT_MENU = "주문하실 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"
        const val VISIT_DAY_IS_NOT_VALID = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요."
        const val ORDER_IS_NOT_VALID = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."
        const val ORDER_SIZE_IS_OVER = "[ERROR] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다."
        const val DRINK_ONLY_ORDER_IS_NOT_ALLOWED = "[ERROR] 음료만 주문 시, 주문할 수 없습니다."
        const val ORDER_MENU_FORMAT_SPLITTER = "-"
        const val MIN_SINGLE_MENU_SIZE = 1
        const val MAX_TOTAL_ORDER_SIZE = 20
    }
}