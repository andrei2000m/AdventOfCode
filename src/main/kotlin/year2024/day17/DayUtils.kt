package year2024.day17

import println
import split
import java.math.BigInteger
import kotlin.math.pow

fun solveTask1(input: String): String {
    val (registersString, programString) = input.split(Regex("\r?\n\r?\n"))
    val (registerA, registerB, registerC) = registersString.lines().map { line -> line.split(Regex(": "))[1].toInt() }
    val program = programString.split(listOf(Regex(": "), Regex(","))).drop(1).map { it.toInt() }
    return executeInstructions(program, 0, Triple(registerA, registerB, registerC), listOf()).joinToString(",")
}

tailrec fun executeInstructions(program: List<Int>, currentIndex: Int, registers: Triple<Int, Int, Int>, output: List<Int>): List<Int> {
    return if (currentIndex >= program.size) output
    else {
        val operation = program[currentIndex]
        val operand = program[currentIndex + 1]
        val combo = when (operand) {
            in 0..3 -> operand
            4 -> registers.first
            5 -> registers.second
            6 -> registers.third
            else -> throw RuntimeException("Something went wrong")
        }
        return when (operation) {
            0 -> executeInstructions(program, currentIndex + 2, Triple(registers.first / 2.0.pow(combo).toInt(), registers.second, registers.third), output)
            1 -> executeInstructions(program, currentIndex + 2, Triple(registers.first, registers.second.xor(operand), registers.third), output)
            2 -> executeInstructions(program, currentIndex + 2, Triple(registers.first, combo and 7, registers.third), output)
            3 -> {
                val jump = if (registers.first == 0) currentIndex + 2 else operand
                executeInstructions(program, jump, registers, output)
            }
            4 -> executeInstructions(program, currentIndex + 2, Triple(registers.first, registers.second.xor(registers.third), registers.third), output)
            5 -> executeInstructions(program, currentIndex + 2, registers, output + (combo and 7))
            6 -> executeInstructions(program, currentIndex + 2, Triple(registers.first, registers.first / 2.0.pow(combo).toInt(), registers.third), output)
            7 -> executeInstructions(program, currentIndex + 2, Triple(registers.first, registers.second, registers.first / 2.0.pow(combo).toInt()), output)
            else -> throw RuntimeException("Something went wrong")
        }
    }
}

fun solveTask2(input: String): String {
    val (registersString, programString) = input.split(Regex("\r?\n\r?\n"))
    val (_, registerB, registerC) = registersString.lines().map { line -> line.split(Regex(": "))[1].toInt() }
    val program = programString.split(listOf(Regex(": "), Regex(","))).drop(1).map { it.toInt() }

    return program.reversed().fold(Triple(BigInteger.ZERO, registerB.toBigInteger(), registerC.toBigInteger())) { (regA, regB, regC), output ->
        output.println()
        // The last 2 digits are go back to the beginning which we do not care about
        val nextDigit = if (regA != BigInteger.ZERO) {
            (0..7).map { Pair(it, executeInstructions2(program.dropLast(2), 0, Triple(regA * 8.toBigInteger() + it.toBigInteger(), regB, regC), listOf())) }
                .filter { it.second == listOf(output) }.minBy { it.first }.first
        } else {
            (1..7).map { Pair(it, executeInstructions2(program.dropLast(2), 0, Triple(regA * 8.toBigInteger() + it.toBigInteger(), regB, regC), listOf())) }
                .filter { it.second == listOf(output) }.minBy { it.first }.first
        }
        Triple(regA * 8.toBigInteger() + nextDigit.toBigInteger(), regB, regC)
    }.first.toString()
}

tailrec fun executeInstructions2(program: List<Int>, currentIndex: Int, registers: Triple<BigInteger, BigInteger, BigInteger>, output: List<Int>): List<Int> {
    registers.first.println()
    return if (currentIndex >= program.size) { output.println(); output }
    else {
        val operation = program[currentIndex]
        val operand = program[currentIndex + 1]
        val combo = when (operand) {
            in 0..3 -> operand
            4 -> registers.first.toInt()
            5 -> registers.second.toInt()
            6 -> registers.third.toInt()
            else -> throw RuntimeException("Something went wrong")
        }
        return when (operation) {
            0 -> executeInstructions2(program, currentIndex + 2, Triple(registers.first / 2.0.pow(combo).toBigDecimal().toBigInteger(), registers.second, registers.third), output)
            1 -> executeInstructions2(program, currentIndex + 2, Triple(registers.first, registers.second.xor(operand.toBigInteger()), registers.third), output)
            2 -> executeInstructions2(program, currentIndex + 2, Triple(registers.first, (combo and 7).toBigInteger(), registers.third), output)
            3 -> {
                val jump = if (registers.first == BigInteger.ZERO) currentIndex + 2 else operand
                executeInstructions2(program, jump, registers, output)
            }
            4 -> executeInstructions2(program, currentIndex + 2, Triple(registers.first, registers.second.xor(registers.third), registers.third), output)
            5 -> executeInstructions2(program, currentIndex + 2, registers, output + (combo and 7))
            6 -> executeInstructions2(program, currentIndex + 2, Triple(registers.first, registers.first / 2.0.pow(combo).toBigDecimal().toBigInteger(), registers.third), output)
            7 -> executeInstructions2(program, currentIndex + 2, Triple(registers.first, registers.second, registers.first / 2.0.pow(combo).toBigDecimal().toBigInteger()), output)
            else -> throw RuntimeException("Something went wrong")
        }
    }
}