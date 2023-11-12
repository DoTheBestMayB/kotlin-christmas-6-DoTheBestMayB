package christmas.util

open class RetryStrategy {

    protected fun <T> doUntilSuccess(operation: () -> T): T {
        while (true) {
            try {
                return operation()
            } catch (e: Exception) {
                val errorMessage = e.message ?: "[ERROR] ${e.stackTraceToString()}"
                println(errorMessage)
            }
        }
    }

    protected fun <T> executeWithFallback(primary: () -> T, fallback: () -> T): T {
        try {
            return primary()
        } catch (e: Exception) {
            val errorMessage = e.message ?: "[ERROR] ${e.stackTraceToString()}"
            println(errorMessage)
        }
        return fallback()
    }
}