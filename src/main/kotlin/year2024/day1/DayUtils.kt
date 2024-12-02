package year2024.day1

import split
import kotlin.math.abs

fun solveTask1(input: String): String {
    val lists = input.lines().fold(Pair<List<Long>, List<Long>>(listOf(), listOf())) { (first, second), line ->
        line.split(listOf(Regex(" {3}"))).let { ls ->
            Pair(first.plus(ls[0].toLong()), second.plus(ls[1].toLong()))
        }
    }

    return lists.component1().sorted().zip(lists.component2().sorted()).fold(0L) { sum, (e1, e2) ->
        sum + abs(e1 - e2)
    }.toString()
}

fun solveTask2(input: String): String {
    val lists = input.lines().fold(Pair<List<Long>, List<Long>>(listOf(), listOf())) { (first, second), line ->
        line.split(listOf(Regex(" {3}"))).let { ls ->
            Pair(first.plus(ls[0].toLong()), second.plus(ls[1].toLong()))
        }
    }

    val elementCount = lists.component2().groupingBy { it }.eachCount()

    return lists.component1().fold(0L) { sum, e ->
        sum + e * (elementCount[e] ?: 0)
    }.toString()
}