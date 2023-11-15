package christmas.domain

import camp.nextstep.edu.missionutils.Console
import christmas.data.OrderTicket

class InputView(
    private val outputView: OutputView,
    private val validator: Validator,
) {

    fun readDate(): Int {
        outputView.show(REQUEST_INPUT_VISIT_DAY, true)

        val input = read()
        return validator.changeInputToDateNum(input)
    }

    fun readMenu(): OrderTicket {
        outputView.show(REQUEST_INPUT_MENU, true)

        val input = read()
        return validator.changeInputToOrderTicket(input)
    }

    private fun read() = Console.readLine()

    companion object {
        private const val REQUEST_INPUT_VISIT_DAY = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)"
        private const val REQUEST_INPUT_MENU = "주문하실 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)"
    }
}