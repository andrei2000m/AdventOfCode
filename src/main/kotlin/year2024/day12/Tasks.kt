package year2024.day12

import println
import readInput

fun main() {
    task1()
    task2()
}

fun task1() {
    val input = readInput(year = 2024, day = 12, task = 1)
    val result = solveTask1(input)
    result.println()
}

fun task2() {
    val input = readInput(year = 2024, day = 12, task = 2)
    val result = solveTask2(input)
    result.println()
}