package year2025.day2

import split
import toLongOrZero
import toPair

fun solveTask1(input: String): String {
    val ranges = input.split(listOf(Regex(",")))
        .map { it.split(listOf(Regex("-"))).toPair() }
    return ranges.sumOf { findInvalid(it.first, it.second) }.toString()
}

fun solveTask2(input: String): String {
    val ranges = input.split(listOf(Regex(",")))
        .map { it.split(listOf(Regex("-"))).toPair() }
    return ranges.sumOf { findInvalidMoreRepeats(it.first, it.second) }.toString()
}

fun findInvalid(min: String, max: String): Long {
    val minLong = min.toLong()
    val maxLong = max.toLong()
    val minPrefix = min.substring(0, min.length / 2).toLongOrZero()
    val maxPrefix = max.substring(0, max.length / 2).toLongOrZero()

    val maxRange = if (maxPrefix < minPrefix) "9".repeat(min.length / 2).toLongOrZero() else maxPrefix

    return (minPrefix..maxRange).fold(0L) { sum, curr ->
        val possibleInvalid = "$curr$curr".toLong()
        if (possibleInvalid in minLong..maxLong) {
            sum + possibleInvalid
        }
        else sum
    }
}

fun findInvalidMoreRepeats(min: String, max: String): Long {
    val minLong = min.toLong()
    val maxLong = max.toLong()
    val minPrefix = min.substring(0, min.length / 2).toLongOrZero()
    val maxPrefix = max.substring(0, max.length / 2).toLongOrZero()

    val maxRange = if (maxPrefix < minPrefix) "9".repeat(min.length / 2).toLongOrZero() else maxPrefix

    return (1..maxRange).fold(setOf<Long>()) { set, curr ->
        val possibleInvalids = generateSequence("$curr".toLong()) { "$it$curr".toLong() }.dropWhile { it < minLong }.takeWhile { it <= maxLong }
        set + possibleInvalids.toSet()
    }.sum()
}
