package christmas.domain

import camp.nextstep.edu.missionutils.Console

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

    private fun read() = Console.readLine()

    companion object {
        private const val REQUEST_INPUT_VISIT_DAY = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"
        private const val VISIT_DAY_IS_NOT_VALID = "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요."
    }
}