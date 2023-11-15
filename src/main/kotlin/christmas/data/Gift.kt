package christmas.data

data class Gift(
    val name: String,
    val menu: Menu,
    val size: Int,
) {

    fun getDiscountAmount() = menu.price * size

    override fun toString() = "${menu.koreanName} ${size}개"
}
