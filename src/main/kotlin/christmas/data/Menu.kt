package christmas.data

data class Menu(
    val type: MENU_TYPE,
    val name: String,
    val price: Int,
) {
    fun isSameMenu(menu: Menu) = type == menu.type && name == menu.name
}