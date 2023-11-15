package christmas.data

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BadgeTest {
    
    @ParameterizedTest
    @ValueSource(ints = [20_000, 30_000, 999_999])
    @DisplayName("Badge - SANTA")
    fun `2만 원 이상의 혜택을 받으면 SANTA를 반환한다`(benefitAmount: Int) {
        // when
        val actual = Badge.from(benefitAmount)

        // then
        val expected = Badge.SANTA
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(ints = [19_999, 15_000, 10_000])
    @DisplayName("Badge - TREE")
    fun `1만 원 이상 2만 원 미만의 혜택을 받으면 TREE를 반환한다`(benefitAmount: Int) {
        // when
        val actual = Badge.from(benefitAmount)

        // then
        val expected = Badge.TREE
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(ints = [5_000, 5_001, 7_689, 9_999])
    @DisplayName("Badge - STAR")
    fun `5천 원 이상 1만 원 미만의 혜택을 받으면 STAR를 반환한다`(benefitAmount: Int) {
        // when
        val actual = Badge.from(benefitAmount)

        // then
        val expected = Badge.STAR
        Assertions.assertThat(actual).isEqualTo(expected)
    }

    @ParameterizedTest
    @ValueSource(ints = [-1_000, -1, 0, 1, 2, 100, 1_000, 4_999])
    @DisplayName("Badge - NONE")
    fun `그 외의 혜택을 받으면 NONE을 반환한다`(benefitAmount: Int) {
        // when
        val actual = Badge.from(benefitAmount)

        // then
        val expected = Badge.NONE
        Assertions.assertThat(actual).isEqualTo(expected)
    }
}