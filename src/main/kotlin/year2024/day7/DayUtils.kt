package year2024.day7

import split

fun solveTask1(input: String): String {
    return input.lines().map { line -> line.split(listOf(Regex(": "), Regex(" "))).map { s -> s.toLong() } }
        .sumOf { line -> if (calculate(line.subList(1, line.size), line[0])) line[0] else 0 }.toString()
}

fun calculate(operands: List<Long>, result: Long): Boolean {
    return operands.fold(listOf(0L)) { possibleValues, operand ->
            possibleValues.flatMap { value ->
                val addition = if (value + operand <= result) listOf(value + operand) else listOf()
                if (value * operand <= result) addition.plus(value * operand) else addition
            }
        }.any { it == result }
}

fun solveTask2(input: String): String {
    return input.lines().map { line -> line.split(listOf(Regex(": "), Regex(" "))).map { s -> s.toLong() } }
        .sumOf { line -> if (calculate2(line.subList(1, line.size), line[0])) line[0] else 0 }.toString()
}

fun calculate2(operands: List<Long>, result: Long): Boolean {
    return operands.fold(listOf(0L)) { possibleValues, operand ->
        possibleValues.flatMap { value ->
            val addition = if (value + operand <= result) listOf(value + operand) else listOf()
            val product = if (value * operand <= result) addition.plus(value * operand) else addition
            if ((value.toString() + operand.toString()).toLong() <= result) product.plus((value.toString() + operand.toString()).toLong()) else product
        }
    }.any { it == result }
}