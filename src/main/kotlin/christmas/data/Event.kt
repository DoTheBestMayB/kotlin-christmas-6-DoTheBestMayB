package christmas.data

data class Event(
    val name: String,
    private val period: IntRange,
    private val isItPossibleToApply: (visitDate: Int) -> Boolean,
    private val rule: (parameter: Any) -> Any,
) {
    fun apply(visitDate: Int, parameter: Any): Any? {
        if (isItPossibleToApply(visitDate).not()) {
            return null
        }
        return rule(parameter)
    }
}
