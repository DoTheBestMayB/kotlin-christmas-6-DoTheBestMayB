package christmas.data

import java.text.DecimalFormat

data class Receipt(
    val orderMenu: HashMap<Menu, Int>,
    val originalTotalPrice: Int,
    val gift: Gift?,
    val benefits: List<Benefit>,
    val totalBenefitAmount: Int,
    val expectedPayPrice: Int,
    val badge: Badge,
) {

    override fun toString(): String {
        val menuSentence = "<주문 메뉴>${makeOrderMenuSentence()}"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>$LINE_SEPARATOR${makeOriginalTotalPriceSentence()}원"
        val giftSentence = "<증정 메뉴>$LINE_SEPARATOR${makeGiftSentence()}"
        val benefitsSentence = "<혜택 내역>$LINE_SEPARATOR${makeBenefitSentence()}"
        val totalBenefitPriceSentence = "<총혜택 금액>$LINE_SEPARATOR${makeTotalBenefitPriceSentence()}원"
        val expectedPayPrice = "<할인 후 예상 결제 금액>$LINE_SEPARATOR${makeExpectedPayPriceSentence()}원"
        val badgeSentence = "<12월 이벤트 배지>$LINE_SEPARATOR${makeBadgeSentence()}"
        return listOf(
            menuSentence, originalTotalPriceSentence, giftSentence, benefitsSentence,
            totalBenefitPriceSentence, expectedPayPrice, badgeSentence,
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
    }

    private fun makeOrderMenuSentence(): String {
        var sentence = ""
        for ((menu, size) in orderMenu) {
            sentence += "$LINE_SEPARATOR${menu.koreanName} ${size}개"
        }
        return sentence
    }

    private fun makeOriginalTotalPriceSentence() = priceFormat.format(originalTotalPrice)

    private fun makeGiftSentence() = gift?.asMenuAndSize() ?: "없음"

    private fun makeBenefitSentence(): String {
        if (benefits.isEmpty()) {
            return "없음"
        }
        return benefits.joinToString(LINE_SEPARATOR)
    }

    private fun makeTotalBenefitPriceSentence() =
        "-${priceFormat.format(totalBenefitAmount)}".takeIf { totalBenefitAmount != 0 } ?: "0"

    private fun makeExpectedPayPriceSentence() = priceFormat.format(expectedPayPrice)

    private fun makeBadgeSentence() = badge.nickName

    companion object {
        private val LINE_SEPARATOR = System.lineSeparator()
        private val priceFormat = DecimalFormat("#,###")
    }
}