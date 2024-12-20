package year2024.day19

import arrow.core.Cache4kMemoizationCache
import arrow.core.MemoizedDeepRecursiveFunction
import arrow.core.buildCache4K

val cache = buildCache4K<Pair<List<Int>, List<List<Int>>>, Boolean> { }
val cache2 = buildCache4K<Pair<List<Int>, List<List<Int>>>, Long> { }

fun solveTask1(input: String): String {
    val (towelsString, designsString) = input.split(Regex("\r?\n\r?\n"))
    val towels = towelsString.split(Regex(", ")).map { it.convertTowel() }
    val designs = designsString.lines().map { it.convertTowel() }
    return designs.map { if (canDoDesign(Pair(it, towels))) 1 else 0 }.sum().toString()
}

fun solveTask2(input: String): String {
    val (towelsString, designsString) = input.split(Regex("\r?\n\r?\n"))
    val towels = towelsString.split(Regex(", ")).map { it.convertTowel() }
    val designs = designsString.lines().map { it.convertTowel() }
    return designs.sumOf { canDoDesign2(Pair(it, towels)) }.toString()
}

val canDoDesign = MemoizedDeepRecursiveFunction(Cache4kMemoizationCache(cache)) { (design, towels) ->
    if (design.isEmpty()) true
    else {
        val possibleTowels = towels.filter { it.size <= design.size && design.subList(0, it.size) == it }
        possibleTowels.any {
            val newDesign = design.subList(it.size, design.size)
            callRecursive(Pair(newDesign, towels))
        }
    }
}

val canDoDesign2 = MemoizedDeepRecursiveFunction(Cache4kMemoizationCache(cache2)) { (design, towels) ->
    if (design.isEmpty()) 1L
    else {
        val possibleTowels = towels.filter { it.size <= design.size && design.subList(0, it.size) == it }
        possibleTowels.sumOf {
            val newDesign = design.subList(it.size, design.size)
            callRecursive(Pair(newDesign, towels))
        }
    }
}

fun String.convertTowel(): List<Int> = this.map { it.convertStripe() }

fun Char.convertStripe(): Int = when (this) {
    'w' -> 0
    'u' -> 1
    'b' -> 2
    'r' -> 3
    'g' -> 4
    else -> throw RuntimeException("Something went wrong")
}