package christmas.domain

class Validator {

    private val validBookingDay = 1..31

    fun checkDate(day: Int) = day in validBookingDay

    fun isNum(input: String) = input.toIntOrNull() != null
    fun extractMenuInfoFrom(phrase: String): Pair<String, Int> {
        val words = phrase.split(ORDER_MENU_FORMAT_SPLITTER)
        require(words.size == 2) {
            ORDER_IS_NOT_VALID
        }
        val (name, sizePhrase) = words
        val size = changeSizePhraseToInt(sizePhrase)

        return name to size
    }

    fun checkTotalOrderSize(size: Int) {
        require(size <= MAX_TOTAL_ORDER_SIZE) {
            ORDER_SIZE_IS_OVER
        }
    }

    private fun changeSizePhraseToInt(phrase: String) = phrase.toIntOrNull()?.takeIf {
        it >= MIN_SINGLE_MENU_ORDER_SIZE
    } ?: throw IllegalArgumentException(ORDER_IS_NOT_VALID)


    companion object {
        private const val ORDER_IS_NOT_VALID = "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."
        private const val MAX_TOTAL_ORDER_SIZE = 20
        private const val MIN_SINGLE_MENU_ORDER_SIZE = 1
        private const val ORDER_MENU_FORMAT_SPLITTER = "-"
        const val ORDER_SIZE_IS_OVER = "[ERROR] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다."
    }
}
