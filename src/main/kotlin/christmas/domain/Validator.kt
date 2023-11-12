package christmas.domain

class Validator {

    private val validBookingDay = 1..31

    fun checkDate(day: Int) = day in validBookingDay

    fun isNum(input: String) = input.toIntOrNull() != null

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