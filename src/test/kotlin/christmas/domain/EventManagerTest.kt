package christmas.domain

import christmas.data.Benefit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
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

    companion object {
        private const val CHRISTMAS_D_DAY_DISCOUNT = "크리스마스 디데이 할인"

        @JvmStatic
        fun createCheckChristmasDiscountValue(): List<Pair<Int, Benefit>> {
            return listOf(
                1 to Benefit(CHRISTMAS_D_DAY_DISCOUNT, 1_000),
                2 to Benefit(CHRISTMAS_D_DAY_DISCOUNT, 1_100),
                15 to Benefit(CHRISTMAS_D_DAY_DISCOUNT, 2_400),
                25 to Benefit(CHRISTMAS_D_DAY_DISCOUNT, 3_400),
            )
        }
    }
}