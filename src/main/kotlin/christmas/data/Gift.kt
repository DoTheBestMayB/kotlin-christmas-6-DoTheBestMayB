package christmas.data

data class Gift(
    val name: String,
    val menu: Menu,
    val size: Int,
) {
    fun getDiscountAmount() = menu.price * size

    fun asMenuAndSize() = "${menu.name} ${size}개"
}
