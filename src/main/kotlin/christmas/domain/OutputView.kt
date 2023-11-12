package christmas.domain

class OutputView {

    fun show(msg: String, lineBreak: Boolean) {
        print(msg)

        if (lineBreak) {
            println()
        }
    }
}