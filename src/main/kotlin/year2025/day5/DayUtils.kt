package year2025.day5

import split
import toPair
import kotlin.math.max

fun solveTask1(input: String): String {
    val (rangeStrings, ingredientsString) = input.split(listOf(Regex("\r\n\r\n"))).map { l -> l.split(listOf(Regex("\r\n"))) }
    val ranges = rangeStrings.map { it.split("-").map { i -> i.toLong() }.toPair() }
    val ingredients = ingredientsString.map { it.toLong() }
    return ingredients.count { ingredient -> ranges.any { it.first <= ingredient && ingredient <= it.second } }.toString()
}

fun solveTask2(input: String): String {
    val rangeStrings = input.split(listOf(Regex("\r\n\r\n"))).map { l -> l.split(listOf(Regex("\r\n"))) }[0]
    val ranges = rangeStrings.map { it.split("-").map { i -> i.toLong() }.toPair() }
    return ranges.fold(listOf<Pair<Long, Long>>()) { acc, pair ->
        val lowerElements = acc.takeWhile { it.first <= pair.first }
        val interim = lowerElements + pair + acc.dropWhile { it.first <= pair.first }
        concatenateRangesAtIndex(interim, lowerElements.size)
    }.sumOf { it.second - it.first + 1 }.toString()
}

fun concatenateRangesAtIndex(ranges: List<Pair<Long, Long>>, index: Int): List<Pair<Long, Long>> {
    val element = ranges[index]
    val previousElement = ranges.getOrElse(index - 1) { Pair(-1L, -1L) }
    val nextElement = ranges.getOrElse(index + 1) { Pair(Long.MAX_VALUE, Long.MAX_VALUE) }

    val combineWithPrevious = previousElement.second >= element.first
    val combineWithNext = nextElement.first <= element.second

    return if (combineWithPrevious && combineWithNext) {
        val combinedElement = Pair(previousElement.first, max(element.second, nextElement.second))
        val newRanges = ranges.subList(0, index - 1) + combinedElement + ranges.subList(index + 2, ranges.size)
        concatenateRangesAtIndex(newRanges, index - 1)
    } else if (combineWithPrevious) {
        val combinedElement = Pair(previousElement.first, max(previousElement.second, element.second))
        val newRanges = ranges.subList(0, index - 1) + combinedElement + ranges.subList(index + 1, ranges.size)
        concatenateRangesAtIndex(newRanges, index - 1)
    } else if (combineWithNext) {
        val combinedElement = Pair(element.first, max(element.second, nextElement.second))
        val newRanges = ranges.subList(0, index) + combinedElement + ranges.subList(index + 2, ranges.size)
        concatenateRangesAtIndex(newRanges, index)
    } else ranges
}
