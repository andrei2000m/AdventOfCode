package year2024.day11

import arrow.core.Cache4kMemoizationCache
import arrow.core.MemoizedDeepRecursiveFunction
import arrow.core.buildCache4K

val cache = buildCache4K<Pair<Long, Int>, Long> { }

fun solveTask1(input: String): String {
    val initialRocks = input.split(" ").map { rock -> rock.toLong() }
    return initialRocks.sumOf { rock -> memoSize(Pair(rock, 25)) }.toString()
}

fun solveTask2(input: String): String {
    val initialRocks = input.split(" ").map { rock -> rock.toLong() }
    return initialRocks.sumOf { rock -> memoSize(Pair(rock, 75)) }.toString()
}

val memoSize = MemoizedDeepRecursiveFunction(Cache4kMemoizationCache(cache)) { (rock, repeats) ->
    val s = rock.toString()
    when {
        repeats == 0 -> 1L
        rock == 0L -> callRecursive(Pair(1, repeats - 1))
        s.length % 2 == 0 -> callRecursive(Pair(s.substring(0, s.length / 2).toLong(), repeats - 1)) +
                    callRecursive(Pair(s.substring(s.length / 2, s.length).toLong(), repeats - 1))
        else -> callRecursive(Pair(rock * 2024, repeats - 1))
    }
}