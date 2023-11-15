package christmas.data

import java.text.DecimalFormat

data class Benefit(
    val name: String,
    val discountAmount: Int,
) {

    override fun toString() = "$name: -${priceFormat.format(discountAmount)}원"

    companion object {
        private val priceFormat = DecimalFormat("#,###")
    }
}
