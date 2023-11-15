package christmas.data

enum class Badge(val nickName: String, val minBenefitAmount: Int) {
    NONE("없음", 0), STAR("별", 5_000),
    TREE("트리", 10_000), SANTA("산타", 20_000);

    companion object {
        // 리턴 타입으로 Badge를 명시하지 않으면 BadgeTest.kt 에서 Badge에 대해 Unresolved Reference Error 발생
        fun from(benefitAmount: Int): Badge = entries.sortedBy {
            -it.minBenefitAmount
        }.firstOrNull {
            it.minBenefitAmount <= benefitAmount
        } ?: NONE
    }
}