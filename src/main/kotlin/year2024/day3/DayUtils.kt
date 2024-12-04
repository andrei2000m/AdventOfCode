package year2024.day3

import println

fun solveTask1(input: String): String {
    return Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)").findAll(input).map { it.destructured }.fold(0L) { sum, (e1, e2) ->
        sum + e1.toLong() * e2.toLong()
    }.toString()
}

fun solveTask2(input: String): String {
    return Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|(don't)\\(\\)|(do)\\(\\)").findAll(input).map { it.destructured }.fold(Pair(0L, true)) { (sum, enabled), (e1, e2, e3, e4) ->
        when {
            e3 == "don't" -> Pair(sum, false)
            e4 == "do" -> Pair(sum, true)
            else -> Pair(sum + if (enabled) e1.toLong() * e2.toLong() else 0L, enabled)
        }
    }.component1().toString()
}