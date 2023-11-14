package christmas.domain

import christmas.data.MENU_TYPE
import christmas.data.Menu

class MenuManager {

    private val appetizerMenu = listOf(
        Menu(MENU_TYPE.APPETIZER, "양송이수프", 6000),
        Menu(MENU_TYPE.APPETIZER, "타파스", 5_500),
        Menu(MENU_TYPE.APPETIZER, "시저샐러드", 8_000),
    )

    private val mainMenu = listOf(
        Menu(MENU_TYPE.MAIN, "티본스테이크", 55_000),
        Menu(MENU_TYPE.MAIN, "바비큐립", 54_000),
        Menu(MENU_TYPE.MAIN, "해산물파스타", 35_000),
        Menu(MENU_TYPE.MAIN, "크리스마스파스타", 25_000),
    )

    private val dessertMenu = listOf(
        Menu(MENU_TYPE.DESSERT, "초코케이크", 15_000),
        Menu(MENU_TYPE.DESSERT, "아이스크림", 5_000),
    )

    private val drinkMenu = listOf(
        Menu(MENU_TYPE.DRINK, "제로콜라", 3_000),
        Menu(MENU_TYPE.DRINK, "레드와인", 60_000),
        Menu(MENU_TYPE.DRINK, "샴페인", 25_000),
    )

    fun getMenu(menuName: String): Menu? {
        return getAllAvailableMenu().firstOrNull {
            it.name == menuName
        }
    }

    private fun getAllAvailableMenu() = appetizerMenu + mainMenu + dessertMenu + drinkMenu
}