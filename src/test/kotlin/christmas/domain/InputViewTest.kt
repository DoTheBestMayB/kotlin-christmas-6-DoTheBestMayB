package christmas.domain

import camp.nextstep.edu.missionutils.Console
import christmas.data.MENU_TYPE
import christmas.data.Menu
import christmas.data.OrderTicket
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.io.ByteArrayInputStream

class InputViewTest {

    private lateinit var validator: Validator
    private lateinit var outputView: OutputView
    private lateinit var inputView: InputView

    @BeforeEach
    fun setUp() {
        validator = Validator.getInstance()
        outputView = OutputView()
        inputView = InputView(outputView, validator)
    }

    @AfterEach
    fun tearDown() {
        Console.close()
        Validator.releaseInstance()
    }

    @ParameterizedTest
    @ValueSource(strings = ["일","1일","삼","\n"," "])
    @DisplayName("InputView : readDate - fail(숫자가 아닌 값 입력)")
    fun `방문할 날짜로 숫자가 아닌 값을 입력하면 에러를 반환한다`(input: String) {
        // given
        setInput(input)

        // when
        val actual = assertThrows(NumberFormatException::class.java) {
            inputView.readDate()
        }

        // then
        val expectedClass = NumberFormatException::class.java
        val expectedErrorMessage = InputView.VISIT_DAY_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "32", "-1", "-9999", "99", "9999"])
    @DisplayName("InputView : readDate - fail(숫자 범위 일치하지 않는 경우)")
    fun `방문할 날짜로 1이상 31 이하가 아닌 숫자를 입력하면 에러를 반환한다`(input: String) {
        // given
        setInput(input)

        // when
        val actual = assertThrows(IllegalArgumentException::class.java) {
            inputView.readDate()
        }

        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = InputView.VISIT_DAY_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["1", "2", "15", "31"])
    @DisplayName("InputView : readDate - success")
    fun `방문할 날짜로 1이상 31 이하인 숫자를 입력하면 해당 숫자를 반환한다`(input: String) {
        // given
        setInput(input)

        // when
        val actual = inputView.readDate()

        // then
        val expected = input.toInt()
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드ㅡ1,티본스테이크ㅡ1", "시저샐러드 1,티본스테이크 1", "시저샐러드-,티본스테이크-", "시저샐러드--,티본스테이크--", " ", "\n"])
    @DisplayName("InputView : readMenu - fail(입력 포맷)")
    fun `포맷과 다르게 메뉴를 입력한 경우 에러를 반환한다`(input: String) {
        // given
        val chef = Chef()
        setInput(input)

        // when
        val actual: java.lang.IllegalArgumentException = assertThrows(IllegalArgumentException::class.java) {
            inputView.readMenu(chef)
        }
        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = InputView.ORDER_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드-1,티본스테이크-0", "시저샐러드-0,티본스테이크-1", "시저샐러드-일,티본스테이크-이", "시저샐러드--,티본스테이크-0", "시저샐러드- ,티본스테이크- "])
    @DisplayName("InputView : readMenu - fail(입력한 메뉴 개수)")
    fun `입력한 메뉴의 개수가 1 이상의 숫자가 아닐 때 에러를 반환한다`(input: String) {
        // given
        val chef = Chef()
        setInput(input)

        // when
        val actual: java.lang.IllegalArgumentException = assertThrows(IllegalArgumentException::class.java) {
            inputView.readMenu(chef)
        }
        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = InputView.ORDER_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드-21", "시저샐러드-5,티본스테이크-4,크리스마스파스타-3,제로콜라-7,아이스크림-2"])
    @DisplayName("InputView : readMenu - fail(총 주문 메뉴 20개 초과)")
    fun `고객이 메뉴를 20개를 초과해서 주문한 경우 에러를 반환한다`(input: String) {
        // given
        val chef = Chef()
        setInput(input)

        // when
        val actual: java.lang.IllegalArgumentException = assertThrows(IllegalArgumentException::class.java) {
            inputView.readMenu(chef)
        }
        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = InputView.ORDER_SIZE_IS_OVER
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드-5,치즈버거-4", "-3,제로콜라-7,아이스크림-2", " -2,시저샐러드-3", "시저샐러드-1, -3"])
    @DisplayName("InputView : readMenu - fail(메뉴판에 없는 메뉴 주문)")
    fun `고객이 메뉴판에 없는 메뉴를 입력한 경우 에러를 반환한다`(input: String) {
        // given
        val chef = Chef()
        setInput(input)

        // when
        val actual: java.lang.IllegalArgumentException = assertThrows(IllegalArgumentException::class.java) {
            inputView.readMenu(chef)
        }
        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = InputView.ORDER_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["시저샐러드-5,시저샐러드-5", "아이스크림-2,아이스크림-2,아이스크림-2"])
    @DisplayName("InputView : readMenu - fail(메뉴 중복 주문)")
    fun `고객이 중복메뉴를 입력한 경우 에러를 반환한다`(input: String) {
        // given
        val chef = Chef()
        setInput(input)

        // when
        val actual: java.lang.IllegalArgumentException = assertThrows(IllegalArgumentException::class.java) {
            inputView.readMenu(chef)
        }
        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = InputView.ORDER_IS_NOT_VALID
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @ValueSource(strings = ["제로콜라-5", "샴페인-10"])
    @DisplayName("InputView : readMenu - fail(음료수만 주문)")
    fun `고객이 음료만 주문한 경우`(input: String) {
        // given
        val chef = Chef()
        setInput(input)

        // when
        val actual: java.lang.IllegalArgumentException = assertThrows(IllegalArgumentException::class.java) {
            inputView.readMenu(chef)
        }
        // then
        val expectedClass = IllegalArgumentException::class.java
        val expectedErrorMessage = InputView.DRINK_ONLY_ORDER_IS_NOT_ALLOWED
        Assertions.assertThat(actual).isInstanceOf(expectedClass)
        Assertions.assertThat(actual).hasMessageContaining(expectedErrorMessage)
    }

    @ParameterizedTest
    @MethodSource("createValueForReadMenuSuccess")
    @DisplayName("InputView : readMenu - success")
    fun `고객이 정상적으로 주문한 경우, 주문한 메뉴를 반환한다`(data: Pair<String, OrderTicket>) {
        // given
        val (input, expected) = data
        val chef = Chef()
        setInput(input)

        // when
        val actual = inputView.readMenu(chef)

        // then
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    private fun setInput(input: String) {
        val inputStream = ByteArrayInputStream(input.toByteArray())
        System.setIn(inputStream)
    }

    companion object {
        @JvmStatic
        fun createValueForReadMenuSuccess() = listOf(
            "해산물파스타-2,레드와인-1,초코케이크-1" to OrderTicket(
                hashMapOf(
                    Menu(MENU_TYPE.MAIN, "해산물파스타", 35_000) to 2,
                    Menu(MENU_TYPE.DRINK, "레드와인", 60_000) to 1,
                    Menu(MENU_TYPE.DESSERT, "초코케이크", 15_000) to 1,
                )
            ),
            "양송이수프-5,티본스테이크-3,바비큐립-2,레드와인-1,초코케이크-1,아이스크림-1,샴페인-1" to OrderTicket(
                hashMapOf(
                    Menu(MENU_TYPE.APPETIZER, "양송이수프", 6000) to 5,
                    Menu(MENU_TYPE.MAIN, "티본스테이크", 55_000) to 3,
                    Menu(MENU_TYPE.MAIN, "바비큐립", 35_000) to 2,
                    Menu(MENU_TYPE.DRINK, "레드와인", 60_000) to 1,
                    Menu(MENU_TYPE.DESSERT, "초코케이크", 15_000) to 1,
                    Menu(MENU_TYPE.DESSERT, "아이스크림", 5_000) to 1,
                    Menu(MENU_TYPE.DRINK, "샴페인", 25_000) to 1,
                )
            ),
        )
    }
}