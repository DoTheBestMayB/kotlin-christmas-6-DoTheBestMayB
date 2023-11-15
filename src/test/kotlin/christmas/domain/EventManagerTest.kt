package christmas.domain

import christmas.data.Benefit
import christmas.data.Gift
import christmas.data.Menu
import christmas.data.OrderTicket
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class EventManagerTest {

    private lateinit var eventManager: EventManager

    @BeforeEach
    fun setUp() {
        eventManager = EventManager()
    }

    @ParameterizedTest
    @ValueSource(ints = [-999, -1, 0, 26, 31, 99])
    @DisplayName("EventManager : checkChristmasDiscount - fail")
    fun `1 ~ 25일이 아닌 값을 입력하면 크리스마스 할인을 받을 수 없다`(date: Int) {
        // when
        val actual = eventManager.checkChristmasDiscount(date)

        // then
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("createCheckChristmasDiscountValue")
    @DisplayName("EventManager : checkChristmasDiscount - true")
    fun `1 ~ 25일을 입력하면 크리스마스 할인을 받을 수 있다`(data: Pair<Int, Benefit>) {
        // given
        val (date, expected) = data

        // when
        val actual = eventManager.checkChristmasDiscount(date)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("createCheckWeekDiscountValue")
    @DisplayName("EventManager : checkWeekDiscount")
    fun `평일 할인 적용 금액을 계산한다`(data: Pair<OrderTicket, Benefit>) {
        // given
        val (orderTicket, expected) = data

        // when
        val actual = eventManager.checkWeekDiscount(orderTicket)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("createCheckWeekendDiscountValue")
    @DisplayName("EventManager : checkWeekendDiscount")
    fun `주말 할인 적용 금액을 계산한다`(data: Pair<OrderTicket, Benefit>) {
        // given
        val (orderTicket, expected) = data

        // when
        val actual = eventManager.checkWeekendDiscount(orderTicket)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(ints = [3, 10, 17, 24, 25, 31])
    @DisplayName("EventManager : checkSpecialDiscount - apply")
    fun `달력에 별이 있는 날에 주문하면 특별 할인을 적용한다`(date: Int) {
        // when
        val actual = eventManager.checkSpecialDiscount(date)

        // then
        val expected = Benefit(SPECIAL_DISCOUNT, AMOUNT_OF_SPECIAL_DISCOUNT)
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 18, 19, 20, 21, 22, 23, 26, 27, 28, 29, 30])
    @DisplayName("EventManager : checkSpecialDiscount - no apply")
    fun `달력에 별이 없는 날에 주문하면 특별 할인을 적용하지 않는다`(date: Int) {
        // when
        val actual = eventManager.checkSpecialDiscount(date)

        // then
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("createCheckSpecialDiscountValue")
    @DisplayName("EventManager : checkSpecialDiscount - apply&no apply")
    fun `구매 금액이 최소 금액 이상인지 아닌지에 따라 증정 이벤트의 증정품을 받을 수 있다`(data: Pair<OrderTicket, Gift?>) {
        // given
        val (orderTicket, expected) = data
        // when
        val actual = eventManager.checkAvailableGifts(orderTicket)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("createCanApplyEventValueTrue")
    @DisplayName("EventManager : canApplyEvent - apply")
    fun `총주문 금액이 1만 원 이상이면 이벤트 적용이 가능하다`(orderTicket: OrderTicket) {
        // when
        val actual = eventManager.canApplyEvent(orderTicket)

        // then
        val expected = true
        assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("createCanApplyEventValueFalse")
    @DisplayName("EventManager : canApplyEvent - no apply")
    fun `총주문 금액이 1만 원 미만이면 이벤트 적용이 불가능하다`(orderTicket: OrderTicket) {
        // when
        val actual = eventManager.canApplyEvent(orderTicket)

        // then
        val expected = false
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        private const val CHRISTMAS_D_DAY_DISCOUNT = "크리스마스 디데이 할인"
        private const val WEEK_DISCOUNT = "평일 할인"
        private const val WEEKEND_DISCOUNT = "주말 할인"
        private const val SPECIAL_DISCOUNT = "특별 할인"
        private const val GIFT_MENU_NAME = "샴페인"
        private const val GIFT_AMOUNT = 1
        private const val GIFT_EVENT = "증정 이벤트"
        private const val WEEK_DISCOUNT_UNIT = 2023
        private const val WEEKEND_DISCOUNT_UNIT = 2023
        private const val AMOUNT_OF_SPECIAL_DISCOUNT = 1_000

        @JvmStatic
        fun createCheckChristmasDiscountValue(): List<Pair<Int, Benefit>> {
            return listOf(
                1 to Benefit(CHRISTMAS_D_DAY_DISCOUNT, 1_000),
                2 to Benefit(CHRISTMAS_D_DAY_DISCOUNT, 1_100),
                15 to Benefit(CHRISTMAS_D_DAY_DISCOUNT, 2_400),
                25 to Benefit(CHRISTMAS_D_DAY_DISCOUNT, 3_400),
            )
        }

        @JvmStatic
        fun createCheckWeekDiscountValue(): List<Pair<OrderTicket, Benefit>> {
            return listOf(
                OrderTicket(
                    hashMapOf(
                        Menu.from("양송이수프")!! to 3,
                        Menu.from("제로콜라")!! to 2,
                    )
                ) to Benefit(WEEK_DISCOUNT, 0),
                OrderTicket(
                    hashMapOf(
                        Menu.from("초코케이크")!! to 3,
                        Menu.from("제로콜라")!! to 2,
                    )
                ) to Benefit(WEEK_DISCOUNT, WEEK_DISCOUNT_UNIT * 3),
                OrderTicket(
                    hashMapOf(
                        Menu.from("초코케이크")!! to 3,
                        Menu.from("아이스크림")!! to 2,
                        Menu.from("제로콜라")!! to 2,
                    )
                ) to Benefit(WEEK_DISCOUNT, WEEK_DISCOUNT_UNIT * 5),
            )
        }

        @JvmStatic
        fun createCheckWeekendDiscountValue(): List<Pair<OrderTicket, Benefit>> {
            return listOf(
                OrderTicket(
                    hashMapOf(
                        Menu.from("초코케이크")!! to 3,
                        Menu.from("아이스크림")!! to 2,
                        Menu.from("제로콜라")!! to 2,
                    )
                ) to Benefit(WEEKEND_DISCOUNT, 0),
                OrderTicket(
                    hashMapOf(
                        Menu.from("티본스테이크")!! to 2,
                        Menu.from("아이스크림")!! to 2,
                        Menu.from("제로콜라")!! to 2,
                    )
                ) to Benefit(WEEKEND_DISCOUNT, WEEKEND_DISCOUNT_UNIT * 2),
                OrderTicket(
                    hashMapOf(
                        Menu.from("티본스테이크")!! to 7,
                        Menu.from("제로콜라")!! to 2,
                    )
                ) to Benefit(WEEKEND_DISCOUNT, WEEKEND_DISCOUNT_UNIT * 7),
                OrderTicket(
                    hashMapOf(
                        Menu.from("티본스테이크")!! to 4,
                        Menu.from("바비큐립")!! to 2,
                        Menu.from("제로콜라")!! to 2,
                    )
                ) to Benefit(WEEKEND_DISCOUNT, WEEKEND_DISCOUNT_UNIT * 6),
                OrderTicket(
                    hashMapOf(
                        Menu.from("티본스테이크")!! to 4,
                        Menu.from("제로콜라")!! to 2,
                        Menu.from("바비큐립")!! to 2,
                        Menu.from("해산물파스타")!! to 1,
                        Menu.from("크리스마스파스타")!! to 1,
                    )
                ) to Benefit(WEEKEND_DISCOUNT, WEEKEND_DISCOUNT_UNIT * 8),
            )
        }

        @JvmStatic
        fun createCheckSpecialDiscountValue(): List<Pair<OrderTicket, Gift?>> {
            return listOf(
                OrderTicket(
                    hashMapOf(
                        Menu.from("티본스테이크")!! to 2,
                        Menu.from("제로콜라")!! to 3,
                    )
                ) to null,
                OrderTicket(
                    hashMapOf(
                        Menu.from("아이스크림")!! to 20,
                    )
                ) to null,
                OrderTicket(
                    hashMapOf(
                        Menu.from("티본스테이크")!! to 2,
                        Menu.from("제로콜라")!! to 4,
                    )
                ) to Gift(GIFT_EVENT, Menu.from(GIFT_MENU_NAME)!!, GIFT_AMOUNT),
                OrderTicket(
                    hashMapOf(
                        Menu.from("초코케이크")!! to 8,
                    )
                ) to Gift(GIFT_EVENT, Menu.from(GIFT_MENU_NAME)!!, GIFT_AMOUNT),
                OrderTicket(
                    hashMapOf(
                        Menu.from("아이스크림")!! to 10,
                        Menu.from("양송이수프")!! to 9,
                        Menu.from("크리스마스파스타")!! to 1,
                    )
                ) to Gift(GIFT_EVENT, Menu.from(GIFT_MENU_NAME)!!, GIFT_AMOUNT),
            )
        }

        @JvmStatic
        fun createCanApplyEventValueTrue(): List<OrderTicket> {
            return listOf(
                OrderTicket(
                    hashMapOf(
                        Menu.from("아이스크림")!! to 2,
                    )
                ),
                OrderTicket(
                    hashMapOf(
                        Menu.from("아이스크림")!! to 1,
                        Menu.from("제로콜라")!! to 2,
                    )
                ),
                OrderTicket(
                    hashMapOf(
                        Menu.from("타파스")!! to 2,
                    )
                ),
                OrderTicket(
                    hashMapOf(
                        Menu.from("티본스테이크")!! to 4,
                        Menu.from("초코케이크")!! to 2,
                    )
                ),
            )
        }

        @JvmStatic
        fun createCanApplyEventValueFalse(): List<OrderTicket> {
            return listOf(
                OrderTicket(
                    hashMapOf(
                        Menu.from("아이스크림")!! to 1,
                    )
                ),
                OrderTicket(
                    hashMapOf(
                        Menu.from("아이스크림")!! to 1,
                        Menu.from("제로콜라")!! to 1,
                    )
                ),
                OrderTicket(
                    hashMapOf(
                        Menu.from("양송이수프")!! to 1,
                        Menu.from("제로콜라")!! to 1,
                    )
                ),
                OrderTicket(
                    hashMapOf(
                        Menu.from("타파스")!! to 1,
                        Menu.from("제로콜라")!! to 1,
                    )
                ),
                OrderTicket(
                    hashMapOf(
                        Menu.from("시저샐러드")!! to 1,
                    )
                ),
            )
        }
    }
}