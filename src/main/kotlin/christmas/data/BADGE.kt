package christmas.data

enum class BADGE(val nickName: String, val minBenefitAmount: Int) {
    NONE("없음", 0), STAR("별", 5_000),
    TREE("트리", 10_000), SANTA("산타", 20_000);

    companion object {
        fun from(benefitAmount: Int) = entries.sortedBy {
            -it.minBenefitAmount
        }.firstOrNull {
            it.minBenefitAmount <= benefitAmount
        } ?: NONE
    }
}