package year2024.day2

import println

fun solveTask1(input: String): String {
    return input.lines().map { line -> line.split(" ").map { it.toInt() } }.fold(0) { sum, line ->
        sum + if (line.isSafe()) 1 else 0
    }.toString()
}

fun solveTask2(input: String): String {
    return input.lines().map { line -> line.split(" ").map { it.toInt() } }.fold(0) { sum, line ->
        sum + if (line.isTolerablySafe()) 1 else 0
    }.toString()
}

fun List<Int>.isTolerablySafe(): Boolean {
    return this.isSafe() || indices
        .map { i -> this.filterIndexed { index, _ -> index != i }.isSafe()}
        .any { it }
}

fun List<Int>.isSafe(): Boolean {
    return this.asSequence().zipWithNext { a, b -> a < b && b - a <= 3 }.all { it }
            || this.asSequence().zipWithNext { a, b -> a > b && a - b <= 3 }.all { it }
}