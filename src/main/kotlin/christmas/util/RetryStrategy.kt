package christmas.util

open class RetryStrategy {

    protected fun <T> doUntilSuccess(operation: () -> T): T {
        while (true) {
            try {
                return operation()
            } catch (e: NoSuchElementException) {
                throwExceptionIfCausedByNoInput(e)
                val errorMessage = e.message ?: "[ERROR] ${e.stackTraceToString()}"
                println(errorMessage)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "[ERROR] ${e.stackTraceToString()}"
                println(errorMessage)
            }
        }
    }

    private fun throwExceptionIfCausedByNoInput(e: NoSuchElementException) {
        if (e.message?.contains(NO_INPUT) == true) {
            throw e
        }
    }

    companion object {
        private const val NO_INPUT = "No line found"
    }
}