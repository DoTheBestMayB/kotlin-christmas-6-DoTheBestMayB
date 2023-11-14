package christmas.domain

class Validator private constructor() {

    private val validBookingDay = 1..31

    fun checkDate(day: Int) = day in validBookingDay

    fun isNum(input: String) = input.toIntOrNull() != null
    fun checkOrderFormat(phrase: String): Boolean {
        val words = phrase.split(InputView.ORDER_MENU_FORMAT_SPLITTER)

        return words.size == 2
    }

    fun checkSingleMenuSize(phrase: String): Boolean {
        val size = phrase.toIntOrNull() ?: return false
        return size >= InputView.MIN_SINGLE_MENU_SIZE
    }

    fun checkTotalOrderSize(size: Int) = size <= InputView.MAX_TOTAL_ORDER_SIZE

    companion object {
        private var instance: Validator? = null

        fun getInstance(): Validator {
            val currentInstance = instance
            if (currentInstance != null) {
                return currentInstance
            }
            return synchronized(this) {
                val synchronizedInstance = instance
                if (synchronizedInstance != null) {
                    return@synchronized synchronizedInstance
                }
                val createdValidator = Validator()
                instance = createdValidator
                createdValidator
            }
        }

        fun releaseInstance() {
            instance = null
        }
    }
}