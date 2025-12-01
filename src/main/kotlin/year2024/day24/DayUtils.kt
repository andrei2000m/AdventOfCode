package year2024.day24

import split
import java.math.BigInteger

fun solveTask1(input: String): String {
    val (initialString, operationsString) = input.split(Regex("\r?\n\r?\n"))
    val initialMap = initialString.lines().fold(mapOf<String, Int>()) { map, line ->
        val (variable, value) = line.split(Regex(": "))
        map + (variable to value.toInt())
    }
    val initialOperations = operationsString.lines().map { it.split(listOf(Regex(" -> "), Regex(" "))) }
    val resultMap = solveOperations(initialMap, initialOperations)
    val zString = resultMap.filter { it.key.startsWith('z') }.toSortedMap().reversed().values.joinToString(separator = "")
    return BigInteger(zString, 2).toString()
}

fun solveOperations(variableMap: Map<String, Int>, operationsMap: List<List<String>>): Map<String, Int> {
    val zs = operationsMap.filter { it[3].startsWith('z') }
    return if (zs.isEmpty()) variableMap
    else {
        val (solvable, unsolvable) = operationsMap.partition { variableMap.keys.containsAll(listOf(it[0], it[2])) }
        val newMap = solvable.fold(variableMap) { map, (var1, operation, var2, result) ->
            val resultValue = solveOperation(map[var1]!!, operation, map[var2]!!)
            map + (result to resultValue)
        }
        solveOperations(newMap, unsolvable)
    }
}

fun solveOperation(var1: Int, operation: String, var2: Int): Int = when (operation) {
    "AND" -> var1.and(var2)
    "OR" -> var1.or(var2)
    "XOR" -> var1.xor(var2)
    else -> throw UnsupportedOperationException()
}

fun solveTask2(input: String): String {
    return ""
}