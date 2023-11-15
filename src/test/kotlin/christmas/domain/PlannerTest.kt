package christmas.domain

import christmas.data.Menu
import christmas.data.OrderTicket
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class PlannerTest {

    private val standardOut = System.out
    private lateinit var outputView: OutputView
    private lateinit var inputView: InputView
    private lateinit var pos: Pos
    private lateinit var eventManager: EventManager
    private lateinit var planner: Planner
    @BeforeEach
    fun setUp() {
        outputView = OutputView()
        inputView = mock(InputView::class.java)
        pos = Pos()
        eventManager = EventManager()
        planner = Planner(outputView, inputView, pos, eventManager)
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun `17일, 초코케이크-5,티본스테이크-1 주문`() {
        // given
        Mockito.`when`(inputView.readDate()).thenReturn(17)
        Mockito.`when`(inputView.readMenu()).thenReturn(OrderTicket(
            linkedMapOf(
                Menu.from("초코케이크")!! to 5,
                Menu.from("티본스테이크")!! to 1,
            )
        ))
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        // when
        planner.startPlan()
        val actual = outputStream.toString()

        // then
        val announceSentence = "12월 17일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        val menuSentence = "<주문 메뉴>${LINE_SEPARATOR}초코케이크 5개${LINE_SEPARATOR}티본스테이크 1개"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>${LINE_SEPARATOR}130,000원"
        val giftSentence = "<증정 메뉴>${LINE_SEPARATOR}샴페인 1개"
        val benefitsSentence = "<혜택 내역>${LINE_SEPARATOR}크리스마스 디데이 할인: -2,600원${LINE_SEPARATOR}평일 할인: -10,115원${LINE_SEPARATOR}특별 할인: -1,000원${LINE_SEPARATOR}증정 이벤트: -25,000원"
        val totalBenefitPriceSentence = "<총혜택 금액>${LINE_SEPARATOR}-38,715원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>${LINE_SEPARATOR}116,285원"
        val badgeSentence = "<12월 이벤트 배지>${LINE_SEPARATOR}산타"
        val expected = listOf(
            announceSentence,
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `17일, 바비큐립-2,양송이수프-3 주문`() {
        // given
        Mockito.`when`(inputView.readDate()).thenReturn(17)
        Mockito.`when`(inputView.readMenu()).thenReturn(OrderTicket(
            linkedMapOf(
                Menu.from("바비큐립")!! to 2,
                Menu.from("양송이수프")!! to 3,
            )
        ))
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        // when
        planner.startPlan()
        val actual = outputStream.toString()

        // then
        val announceSentence = "12월 17일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        val menuSentence = "<주문 메뉴>${LINE_SEPARATOR}바비큐립 2개${LINE_SEPARATOR}양송이수프 3개"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>${LINE_SEPARATOR}126,000원"
        val giftSentence = "<증정 메뉴>${LINE_SEPARATOR}샴페인 1개"
        val benefitsSentence = "<혜택 내역>${LINE_SEPARATOR}크리스마스 디데이 할인: -2,600원${LINE_SEPARATOR}특별 할인: -1,000원${LINE_SEPARATOR}증정 이벤트: -25,000원"
        val totalBenefitPriceSentence = "<총혜택 금액>${LINE_SEPARATOR}-28,600원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>${LINE_SEPARATOR}122,400원"
        val badgeSentence = "<12월 이벤트 배지>${LINE_SEPARATOR}산타"
        val expected = listOf(
            announceSentence,
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `12일, 초코케이크-5,티본스테이크-1 주문`() {
        // given
        Mockito.`when`(inputView.readDate()).thenReturn(12)
        Mockito.`when`(inputView.readMenu()).thenReturn(OrderTicket(
            linkedMapOf(
                Menu.from("초코케이크")!! to 5,
                Menu.from("티본스테이크")!! to 1,
            )
        ))
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        // when
        planner.startPlan()
        val actual = outputStream.toString()

        // then
        val announceSentence = "12월 12일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        val menuSentence = "<주문 메뉴>${LINE_SEPARATOR}초코케이크 5개${LINE_SEPARATOR}티본스테이크 1개"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>${LINE_SEPARATOR}130,000원"
        val giftSentence = "<증정 메뉴>${LINE_SEPARATOR}샴페인 1개"
        val benefitsSentence = "<혜택 내역>${LINE_SEPARATOR}크리스마스 디데이 할인: -2,100원${LINE_SEPARATOR}평일 할인: -10,115원${LINE_SEPARATOR}증정 이벤트: -25,000원"
        val totalBenefitPriceSentence = "<총혜택 금액>${LINE_SEPARATOR}-37,215원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>${LINE_SEPARATOR}117,785원"
        val badgeSentence = "<12월 이벤트 배지>${LINE_SEPARATOR}산타"
        val expected = listOf(
            announceSentence,
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `10일, 초코케이크-3 주문`() {
        // given
        Mockito.`when`(inputView.readDate()).thenReturn(3)
        Mockito.`when`(inputView.readMenu()).thenReturn(OrderTicket(
            linkedMapOf(
                Menu.from("초코케이크")!! to 3,
            )
        ))
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        // when
        planner.startPlan()
        val actual = outputStream.toString()

        // then
        val announceSentence = "12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        val menuSentence = "<주문 메뉴>${LINE_SEPARATOR}초코케이크 3개"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>${LINE_SEPARATOR}45,000원"
        val giftSentence = "<증정 메뉴>${LINE_SEPARATOR}없음"
        val benefitsSentence = "<혜택 내역>${LINE_SEPARATOR}크리스마스 디데이 할인: -1,200원${LINE_SEPARATOR}평일 할인: -6,069원${LINE_SEPARATOR}특별 할인: -1,000원"
        val totalBenefitPriceSentence = "<총혜택 금액>${LINE_SEPARATOR}-8,269원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>${LINE_SEPARATOR}36,731원"
        val badgeSentence = "<12월 이벤트 배지>${LINE_SEPARATOR}별"
        val expected = listOf(
            announceSentence,
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `26일, 아이스크림-3,크리스마스파스타-2 주문`() {
        // given
        Mockito.`when`(inputView.readDate()).thenReturn(26)
        Mockito.`when`(inputView.readMenu()).thenReturn(OrderTicket(
            linkedMapOf(
                Menu.from("아이스크림")!! to 3,
                Menu.from("크리스마스파스타")!! to 2,
            )
        ))
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        // when
        planner.startPlan()
        val actual = outputStream.toString()

        // then
        val announceSentence = "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        val menuSentence = "<주문 메뉴>${LINE_SEPARATOR}아이스크림 3개${LINE_SEPARATOR}크리스마스파스타 2개"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>${LINE_SEPARATOR}65,000원"
        val giftSentence = "<증정 메뉴>${LINE_SEPARATOR}없음"
        val benefitsSentence = "<혜택 내역>${LINE_SEPARATOR}평일 할인: -6,069원"
        val totalBenefitPriceSentence = "<총혜택 금액>${LINE_SEPARATOR}-6,069원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>${LINE_SEPARATOR}58,931원"
        val badgeSentence = "<12월 이벤트 배지>${LINE_SEPARATOR}별"
        val expected = listOf(
            announceSentence,
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `19일, 해산물파스타-1,제로콜라-1 주문`() {
        // given
        Mockito.`when`(inputView.readDate()).thenReturn(19)
        Mockito.`when`(inputView.readMenu()).thenReturn(OrderTicket(
            linkedMapOf(
                Menu.from("해산물파스타")!! to 1,
                Menu.from("제로콜라")!! to 1,
            )
        ))
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        // when
        planner.startPlan()
        val actual = outputStream.toString()

        // then
        val announceSentence = "12월 19일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        val menuSentence = "<주문 메뉴>${LINE_SEPARATOR}해산물파스타 1개${LINE_SEPARATOR}제로콜라 1개"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>${LINE_SEPARATOR}38,000원"
        val giftSentence = "<증정 메뉴>${LINE_SEPARATOR}없음"
        val benefitsSentence = "<혜택 내역>${LINE_SEPARATOR}크리스마스 디데이 할인: -2,800원"
        val totalBenefitPriceSentence = "<총혜택 금액>${LINE_SEPARATOR}-2,800원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>${LINE_SEPARATOR}35,200원"
        val badgeSentence = "<12월 이벤트 배지>${LINE_SEPARATOR}없음"
        val expected = listOf(
            announceSentence,
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `14일, 아이스크림-1,제로콜라-1 주문`() {
        // given
        Mockito.`when`(inputView.readDate()).thenReturn(14)
        Mockito.`when`(inputView.readMenu()).thenReturn(OrderTicket(
            linkedMapOf(
                Menu.from("아이스크림")!! to 1,
                Menu.from("제로콜라")!! to 1,
            )
        ))
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        // when
        planner.startPlan()
        val actual = outputStream.toString()

        // then
        val announceSentence = "12월 14일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        val menuSentence = "<주문 메뉴>${LINE_SEPARATOR}아이스크림 1개${LINE_SEPARATOR}제로콜라 1개"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>${LINE_SEPARATOR}8,000원"
        val giftSentence = "<증정 메뉴>${LINE_SEPARATOR}없음"
        val benefitsSentence = "<혜택 내역>${LINE_SEPARATOR}없음"
        val totalBenefitPriceSentence = "<총혜택 금액>${LINE_SEPARATOR}0원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>${LINE_SEPARATOR}8,000원"
        val badgeSentence = "<12월 이벤트 배지>${LINE_SEPARATOR}없음"
        val expected = listOf(
            announceSentence,
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `3일, 아이스크림-5,제로콜라-1 주문`() {
        // given
        Mockito.`when`(inputView.readDate()).thenReturn(3)
        Mockito.`when`(inputView.readMenu()).thenReturn(OrderTicket(
            linkedMapOf(
                Menu.from("아이스크림")!! to 5,
                Menu.from("제로콜라")!! to 1,
            )
        ))
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        // when
        planner.startPlan()
        val actual = outputStream.toString()

        // then
        val announceSentence = "12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!"
        val menuSentence = "<주문 메뉴>${LINE_SEPARATOR}아이스크림 5개${LINE_SEPARATOR}제로콜라 1개"
        val originalTotalPriceSentence = "<할인 전 총주문 금액>${LINE_SEPARATOR}28,000원"
        val giftSentence = "<증정 메뉴>${LINE_SEPARATOR}없음"
            val benefitsSentence = "<혜택 내역>${LINE_SEPARATOR}크리스마스 디데이 할인: -1,200원${LINE_SEPARATOR}평일 할인: -10,115원${LINE_SEPARATOR}특별 할인: -1,000원"
        val totalBenefitPriceSentence = "<총혜택 금액>${LINE_SEPARATOR}-12,315원"
        val grandTotalSentence = "<할인 후 예상 결제 금액>${LINE_SEPARATOR}15,685원"
        val badgeSentence = "<12월 이벤트 배지>${LINE_SEPARATOR}트리"
        val expected = listOf(
            announceSentence,
            menuSentence,
            originalTotalPriceSentence,
            giftSentence,
            benefitsSentence,
            totalBenefitPriceSentence,
            grandTotalSentence,
            badgeSentence
        ).joinToString(
            LINE_SEPARATOR + LINE_SEPARATOR
        )
        assertThat(actual).isEqualTo(expected)
    }

    companion object {
        private val LINE_SEPARATOR = System.lineSeparator()
    }
}