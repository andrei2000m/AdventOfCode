package year2025.day3

import split

fun solveTask1(input: String): String {
    return calculate(input, 2)
}

fun solveTask2(input: String): String {
    return calculate(input, 12)
}

fun calculate(input: String, batteriesToSelect: Int): String {
    return input.split(listOf(Regex("\r\n")))
        .map { it.map { c -> c.digitToInt() } }
        .sumOf { findJoltage(it, 0, batteriesToSelect) }
        .toString()
}

tailrec fun findJoltage(bank: List<Int>, current: Long, remaining: Int): Long {
    if (remaining == 0) return current
    val nextSelected = bank.subList(0, bank.size - remaining + 1).withIndex().maxBy { it.value }
    return findJoltage(bank.subList(nextSelected.index + 1, bank.size), "$current${nextSelected.value}".toLong(), remaining - 1)
}
