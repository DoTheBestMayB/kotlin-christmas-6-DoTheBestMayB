package christmas.domain

import christmas.data.Menu

class Pos {

    private val menus = mutableSetOf<Menu>()

    fun register(menu: Menu) {
        for (existingMenu in menus) {
            if (existingMenu.isSameMenu(menu)) {
                throw IllegalArgumentException(ALREADY_REGISTERED)
            }
        }
        menus.add(menu)
    }

    companion object {
        private const val ALREADY_REGISTERED = "[ERROR] 이미 등록된 메뉴입니다."
    }
}