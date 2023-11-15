package christmas.domain

import christmas.data.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class PosTest {

    private lateinit var pos: Pos

    @BeforeEach
    fun setUp() {
        pos = Pos()
    }

    @ParameterizedTest
    @MethodSource("createReceiptValue")
    @DisplayName("Pos : createReceipt")
    fun `주문 내역에 따라 Receipt를 생성한다`(data: ReceiptTestData) {
        // given
        val orderTicket = data.orderTicket
        val priceDiscounts = data.priceDiscounts
        val gift = data.gift
        val expected = data.receipt

        // when
        val actual = pos.createReceipt(orderTicket, priceDiscounts, gift)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun createReceiptValue(): List<ReceiptTestData> {
            return listOf(
                // 17일 주문, 초코케이크-5,티본스테이크-1
                ReceiptTestData(
                    orderTicket = OrderTicket(
                        orderedMenu = linkedMapOf(
                            Menu.from("초코케이크")!! to 5,
                            Menu.from("티본스테이크")!! to 1,
                        ),
                    ),
                    priceDiscounts = listOf(
                        Benefit(EventManager.CHRISTMAS_D_DAY_DISCOUNT, 2600),
                        Benefit(EventManager.WEEK_DISCOUNT, 2023 * 5),
                        Benefit(EventManager.SPECIAL_DISCOUNT, 1000),
                    ),
                    gift = Gift(
                        EventManager.GIFT_EVENT, Menu.from(EventManager.GIFT_MENU_NAME)!!, EventManager.GIFT_AMOUNT
                    ),
                    receipt = Receipt(
                        orderMenu = linkedMapOf(
                            Menu.from("초코케이크")!! to 5,
                            Menu.from("티본스테이크")!! to 1,
                        ),
                        originalTotalPrice = 130_000,
                        gift = Gift(
                            EventManager.GIFT_EVENT, Menu.from(EventManager.GIFT_MENU_NAME)!!, EventManager.GIFT_AMOUNT
                        ),
                        benefits = listOf(
                            Benefit(EventManager.CHRISTMAS_D_DAY_DISCOUNT, 2600),
                            Benefit(EventManager.WEEK_DISCOUNT, 2023 * 5),
                            Benefit(EventManager.SPECIAL_DISCOUNT, 1000),
                            Benefit(
                                EventManager.GIFT_EVENT, Gift(
                                    EventManager.GIFT_EVENT,
                                    Menu.from(EventManager.GIFT_MENU_NAME)!!,
                                    EventManager.GIFT_AMOUNT
                                ).getDiscountAmount()
                            )
                        ),
                        totalBenefitPrice = 38_715,
                        grandTotal = 116_285,
                        badge = Badge.SANTA,
                    )
                ),
                // 19일 주문, 아이스크림-1,제로콜라-1
                ReceiptTestData(
                    orderTicket = OrderTicket(
                        orderedMenu = linkedMapOf(
                            Menu.from("아이스크림")!! to 1,
                            Menu.from("제로콜라")!! to 1,
                        ),
                    ),
                    priceDiscounts = listOf(),
                    gift = null,
                    receipt = Receipt(
                        orderMenu = linkedMapOf(
                            Menu.from("아이스크림")!! to 1,
                            Menu.from("제로콜라")!! to 1,
                        ),
                        originalTotalPrice = 8_000,
                        gift = null,
                        benefits = listOf(),
                        totalBenefitPrice = 0,
                        grandTotal = 8_000,
                        badge = Badge.NONE,
                    )
                )
            )
        }
    }
}