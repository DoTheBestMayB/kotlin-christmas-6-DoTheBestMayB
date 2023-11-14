package christmas.domain

import christmas.data.Receipt

class OutputView {

    fun show(msg: String, lineBreak: Boolean) {
        print(msg)

        if (lineBreak) {
            println()
        }
    }

    fun show(day: Int, receipt: Receipt) {
        show("12월 ${day}일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", true)
        show(EMPTY_TEXT_FOR_LINE_BREAK, true)
        show(receipt.toString(), false)
    }

    companion object {
        private const val EMPTY_TEXT_FOR_LINE_BREAK = ""
    }
}