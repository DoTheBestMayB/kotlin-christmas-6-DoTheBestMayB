package christmas.util

open class RetryStrategy {

    protected fun <T> doUntilSuccess(operation: () -> T): T {
        while (true) {
            try {
                return operation()
            } catch (e: NoSuchElementException) {
                if (e.message?.contains(NO_INPUT) == true) {
                    throw e
                }
                val errorMessage = e.message ?: "[ERROR] ${e.stackTraceToString()}"
                println(errorMessage)
            } catch (e: Exception) {
                val errorMessage = e.message ?: "[ERROR] ${e.stackTraceToString()}"
                println(errorMessage)
            }
        }
    }

    protected fun <T> executeWithFallback(primary: () -> T, fallback: () -> T): T {
        try {
            return primary()
        } catch (e: NoSuchElementException) {
            throw e
        } catch (e: Exception) {
            val errorMessage = e.message ?: "[ERROR] ${e.stackTraceToString()}"
            println(errorMessage)
        }
        return fallback()
    }

    companion object {
        private const val NO_INPUT = "No line found"
    }
}