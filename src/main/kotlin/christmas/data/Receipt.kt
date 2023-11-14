package christmas.data

import java.text.DecimalFormat

data class Receipt(
    val orderMenu: HashMap<Menu, Int>,
    val originalTotalPrice: Int,
    val gift: Gift?,
    val benefits: List<Benefit>,
    val totalBenefitPrice: Int,
    val grandTotal: Int,
    val badge: BADGE,
) {
    override fun toString(): String {
        val menuSentence = "<주문 메뉴>${makeOrderMenuSentence()}"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>$LINE_SEPARATOR${priceFormat.format(originalTotalPrice)}원"
        val giftSentence = "<증정 메뉴>$LINE_SEPARATOR${gift?.asMenuAndSize() ?: "없음"}"
        val benefitsSentence = "<혜택 내역>$LINE_SEPARATOR${makeBenefitSentence()}"
        val totalBenefitPriceSentence = "<총혜택 금액>$LINE_SEPARATOR${makeTotalBenefitPriceSentence()}원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>$LINE_SEPARATOR${priceFormat.format(grandTotal)}원"
        val badgeSentence = "<12월 이벤트 배지>$LINE_SEPARATOR${badge.nickName}"
        return listOf(
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
    }

    private fun makeOrderMenuSentence(): String {
        var sentence = ""
        for ((menu, size) in orderMenu) {
            sentence += "$LINE_SEPARATOR${menu.name} ${size}개"
        }
        return sentence
    }

    private fun makeBenefitSentence(): String {
        if (benefits.isEmpty()) {
            return "없음"
        }
        return benefits.joinToString(LINE_SEPARATOR)
    }

    private fun makeTotalBenefitPriceSentence() =
        "-${priceFormat.format(totalBenefitPrice)}".takeIf { totalBenefitPrice != 0 } ?: "0"


    companion object {
        private val LINE_SEPARATOR = System.lineSeparator()
        private val priceFormat = DecimalFormat("#,###")
    }
}