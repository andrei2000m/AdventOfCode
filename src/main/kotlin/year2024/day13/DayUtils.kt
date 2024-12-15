package year2024.day13

import println
import split
import java.math.BigDecimal
import java.math.BigInteger

fun solveTask1(input: String): String {
    return input.split(Regex("\\r?\\n\\r?\\n")).sumOf { machine ->
        // Will always be 3 elements long
        val machineNumbers = machine.lines().map { line ->
            val split = line.split(listOf(Regex(": X="), Regex(": X\\+"), Regex(", Y\\+"), Regex(", Y=")))
            Pair(split[1].toLong(), split[2].toLong())
        }
        val a1 = machineNumbers[0].first.toDouble()
        val b1 = machineNumbers[1].first.toDouble()
        val c1 = -machineNumbers[2].first.toDouble()
        val a2 = machineNumbers[0].second.toDouble()
        val b2 = machineNumbers[1].second.toDouble()
        val c2 = -machineNumbers[2].second.toDouble()

        val x = (b1 * c2 - b2 * c1) / (a1 * b2 - a2 * b1)
        val y = (a2 * c1 - a1 * c2) / (a1 * b2 - a2 * b1)
        if (x in 0.0..100.0 && y in 0.0..100.0 && x % 1.0 == 0.0 && y % 1.0 == 0.0) x.toLong() * 3 + y.toLong() else 0
    }.toString()
}

fun solveTask2(input: String): String {
    return input.split(Regex("\\r?\\n\\r?\\n")).sumOf { machine ->
        // Will always be 3 elements long
        val machineNumbers = machine.lines().mapIndexed { index, line ->
            val split = line.split(listOf(Regex(": X="), Regex(": X\\+"), Regex(", Y\\+"), Regex(", Y=")))
            if (index < 2) Pair(split[1].toBigInteger(), split[2].toBigInteger()) else Pair(
                BigInteger.valueOf(
                    10000000000000L
                ) + split[1].toBigInteger(), BigInteger.valueOf(10000000000000L) + split[2].toBigInteger()
            )
        }
        val a1 = machineNumbers[0].first.toDouble()
        val b1 = machineNumbers[1].first.toDouble()
        val c1 = -machineNumbers[2].first.toDouble()
        val a2 = machineNumbers[0].second.toDouble()
        val b2 = machineNumbers[1].second.toDouble()
        val c2 = -machineNumbers[2].second.toDouble()

        val x = (b1 * c2 - b2 * c1) / (a1 * b2 - a2 * b1)
        val y = (a2 * c1 - a1 * c2) / (a1 * b2 - a2 * b1)
        if (x >= 0.0 && y >= 0.0 && x % 1.0 == 0.0 && y % 1.0 == 0.0) x.toLong() * 3 + y.toLong() else 0
    }.toString()
}