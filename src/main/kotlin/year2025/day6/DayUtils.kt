package year2025.day6

import split

fun solveTask1(input: String): String {
    val rows = input.split(listOf(Regex("\r\n")))
    val operators = rows.last().split(listOf(Regex(" +")))
    val operands = rows.subList(0, rows.size - 1).map { it.split(listOf(Regex(" +"))).filter { s -> s.trim().isNotEmpty() } }.map { l -> l.map { it.toLong() } }
    return operators.indices.sumOf { i ->
        val currentOperands = operands.map { it[i] }
        val currentOperator = operators[i]
        val initialValue = if (currentOperator == "*") 1L else 0L
        val function = if (currentOperator == "*") { x: Long -> { y: Long -> x * y } } else { x: Long -> { y: Long -> x + y } }
        currentOperands.fold(initialValue) { acc, n -> function(acc)(n) }
    }.toString()
}

fun solveTask2(input: String): String {
    val rows = input.split(listOf(Regex("\r\n")))
    val operators = rows.last().split(listOf(Regex(" +")))

    val operandStrings = rows.subList(0, rows.size - 1)
    val stringOperands = operandStrings.maxBy { it.length }.indices.map { i -> operandStrings.map { it.getOrNull(i) ?: "" }.fold("") { acc, c -> "$acc$c".trim() } }
    val operands = stringOperands.flatMapIndexed { index, s ->
        when {
            index == 0 || index == stringOperands.size - 1 -> listOf(index)
            s.isEmpty() -> listOf(index - 1, index + 1)
            else -> emptyList()
        }
    }.windowed(size = 2, step = 2) { (from, to) -> stringOperands.slice(from..to)
    }.map { l -> l.map { it.toLong() } }

    return operators.indices.sumOf { i ->
        val currentOperands = operands[i]
        val currentOperator = operators[i]
        val initialValue = if (currentOperator == "*") 1L else 0L
        val function = if (currentOperator == "*") { x: Long -> { y: Long -> x * y } } else { x: Long -> { y: Long -> x + y } }
        currentOperands.fold(initialValue) { acc, n -> function(acc)(n) }
    }.toString()
}
