package year2025.day1

import split
import kotlin.math.abs

fun solveTask1(input: String): String {
    return input.split(listOf(Regex("\r\n")))
        .map { Pair(it[0].toString(), it.substring(1)) }
        .map { (r, a) -> Rotation.valueOf(r).direction * a.toInt() }
        .fold(Pair(0, 50)) { (zeros, acc), currVal ->
            val newAcc = (currVal + acc).mod(100)
            val newZeros = if (newAcc == 0) zeros + 1 else zeros
            Pair(newZeros, newAcc)
        }.first.toString()
}

fun solveTask2(input: String): String {
    return input.split(listOf(Regex("\r\n")))
        .map { Pair(it[0].toString(), it.substring(1)) }
        .map { (r, a) -> Rotation.valueOf(r).direction * a.toInt() }
        .fold(Pair(0, 50)) { (zeros, acc), currVal ->
            val i = currVal + acc
            val newAcc = i.mod(100)
            val fullRotations = abs(i / 100)
            val newZeros = (if (i < 0 && acc != 0) zeros + 1
            else if (fullRotations == 0 && newAcc == 0) zeros + 1
            else zeros) + fullRotations
            Pair(newZeros, newAcc)
        }.first.toString()
}

enum class Rotation(val direction: Int) { L(-1), R(1) }
