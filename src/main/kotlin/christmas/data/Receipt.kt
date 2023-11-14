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
        return """
            
<주문 메뉴>
${orderMenuSentence()}
<할인 전 총주문 금액>
${priceFormat.format(originalTotalPrice)}원

<증정 메뉴>
${gift?.asMenuAndSize() ?: "없음"}

<혜택 내역>
${"없음".takeIf { benefits.isEmpty() } ?: benefits.joinToString("\n")}

<총혜택 금액>
${"-${priceFormat.format(totalBenefitPrice)}".takeIf { totalBenefitPrice != 0 } ?: 0}원

<할인 후 예상 결제 금액>
${priceFormat.format(grandTotal)}원

<12월 이벤트 배지>
${badge.nickName}""".trimIndent()
    }

    private fun orderMenuSentence(): String {
        var sentence = ""
        for ((menu, size) in orderMenu) {
            sentence += "${menu.name} ${size}개\n"
        }
        return sentence
    }

    companion object {
        private val priceFormat = DecimalFormat("#,###")
    }
}