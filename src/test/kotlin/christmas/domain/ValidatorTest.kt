package christmas.domain

import christmas.data.Menu
import christmas.data.OrderTicket
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

class ValidatorTest {

    private lateinit var validator: Validator

    @BeforeEach
    fun setUp() {
        validator = Validator()
    }

    @ParameterizedTest
    @ValueSource(strings = ["일","1일","삼","\n"," "])
    @DisplayName("Validator : changeInputToDateNum - fail(숫자가 아닌 값 입력)")
    fun `방문할 날짜로 숫자가 아닌 값을 입력하면 에러를 반환한다`(input: String) {
        // when
        val actual = assertThrows(NumberFormatException::class.java) {
            validator.changeInputToDateNum(input)
        }

        // then
        val expectedClass = NumberFormatException::class.java
        val expectedErrorMessage = Validator.VISIT_DAY_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "32", "-1", "-9999", "99", "9999"])
    @DisplayName("Validator : changeInputToDateNum - fail(숫자 범위 일치하지 않는 경우)")
    fun `방문할 날짜로 1이상 31 이하가 아닌 숫자를 입력하면 에러를 반환한다`(input: String) {
        // when
        val actual = assertThrows(IllegalArgumentException::class.java) {
            validator.changeInputToDateNum(input)
        }

        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = Validator.VISIT_DAY_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["1", "2", "15", "31"])
    @DisplayName("Validator : changeInputToDateNum - success")
    fun `방문할 날짜로 1이상 31 이하인 숫자를 입력하면 해당 숫자를 반환한다`(input: String) {
        // when
        val actual = validator.changeInputToDateNum(input)

        // then
        val expected = input.toInt()
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드ㅡ1,티본스테이크ㅡ1", "시저샐러드 1,티본스테이크 1", "시저샐러드-,티본스테이크-", "시저샐러드--,티본스테이크--", " ", "\n"])
    @DisplayName("Validator : changeInputToOrderTicket - fail(입력 포맷)")
    fun `포맷과 다르게 메뉴를 입력한 경우 에러를 반환한다`(input: String) {
        // when
        val actual = assertThrows(IllegalArgumentException::class.java) {
            validator.changeInputToOrderTicket(input)
        }

        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = Validator.ORDER_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드-1,티본스테이크-0", "시저샐러드-0,티본스테이크-1", "시저샐러드-일,티본스테이크-이", "시저샐러드--,티본스테이크-0", "시저샐러드- ,티본스테이크- "])
    @DisplayName("Validator : changeInputToOrderTicket - fail(입력한 메뉴 개수)")
    fun `입력한 메뉴의 개수가 1 이상의 숫자가 아닐 때 에러를 반환한다`(input: String) {
        // when
        val actual = assertThrows(IllegalArgumentException::class.java) {
            validator.changeInputToOrderTicket(input)
        }

        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = Validator.ORDER_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드-21", "시저샐러드-5,티본스테이크-4,크리스마스파스타-3,제로콜라-7,아이스크림-2"])
    @DisplayName("Validator : changeInputToOrderTicket - fail(총 주문 메뉴 20개 초과)")
    fun `고객이 메뉴를 20개를 초과해서 주문한 경우 에러를 반환한다`(input: String) {
        // when
        val actual = assertThrows(IllegalArgumentException::class.java) {
            validator.changeInputToOrderTicket(input)
        }

        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = Validator.ORDER_SIZE_IS_OVER
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드-5,치즈버거-4", "-3,제로콜라-7,아이스크림-2", " -2,시저샐러드-3", "시저샐러드-1, -3"])
    @DisplayName("Validator : changeInputToOrderTicket - fail(메뉴판에 없는 메뉴 주문)")
    fun `고객이 메뉴판에 없는 메뉴를 입력한 경우 에러를 반환한다`(input: String) {
        // when
        val actual = assertThrows(IllegalArgumentException::class.java) {
            validator.changeInputToOrderTicket(input)
        }

        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = Validator.ORDER_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드-5,시저샐러드-5", "아이스크림-2,아이스크림-2,아이스크림-2"])
    @DisplayName("Validator : changeInputToOrderTicket - fail(메뉴 중복 주문)")
    fun `고객이 중복메뉴를 입력한 경우 에러를 반환한다`(input: String) {
        // when
        val actual = assertThrows(IllegalArgumentException::class.java) {
            validator.changeInputToOrderTicket(input)
        }

        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = Validator.ORDER_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["제로콜라-5", "샴페인-10"])
    @DisplayName("Validator : changeInputToOrderTicket - fail(음료수만 주문)")
    fun `고객이 음료만 주문한 경우`(input: String) {
        // when
        val actual = assertThrows(IllegalArgumentException::class.java) {
            validator.changeInputToOrderTicket(input)
        }

        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = Validator.DRINK_ONLY_ORDER_IS_NOT_ALLOWED
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @MethodSource("createValueForReadMenuSuccess")
    @DisplayName("Validator : changeInputToOrderTicket - success")
    fun `고객이 정상적으로 주문한 경우, 주문한 메뉴를 반환한다`(data: Pair<String, OrderTicket>) {
        // given
        val (input, expected) = data

        // when
        val actual = validator.changeInputToOrderTicket(input)

        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        fun createValueForReadMenuSuccess() = listOf(
            "해산물파스타-2,레드와인-1,초코케이크-1" to OrderTicket(
                linkedMapOf(
                    Menu.from("해산물파스타")!! to 2,
                    Menu.from("레드와인")!! to 1,
                    Menu.from("초코케이크")!! to 1,
                )
            ),
            "양송이수프-5,티본스테이크-3,바비큐립-2,레드와인-1,초코케이크-1,아이스크림-1,샴페인-1" to OrderTicket(
                linkedMapOf(
                    Menu.from("양송이수프")!! to 5,
                    Menu.from("티본스테이크")!! to 3,
                    Menu.from("바비큐립")!! to 2,
                    Menu.from("레드와인")!! to 1,
                    Menu.from("초코케이크")!! to 1,
                    Menu.from("아이스크림")!! to 1,
                    Menu.from("샴페인")!! to 1,
                )
            ),
        )
    }
}