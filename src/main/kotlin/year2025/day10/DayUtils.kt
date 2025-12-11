package year2025.day10

import arrow.core.tail
import com.google.ortools.Loader
import com.google.ortools.sat.CpModel
import com.google.ortools.sat.CpSolver
import com.google.ortools.sat.LinearExpr
import split
import kotlin.math.min

fun solveTask1(input: String): String {
    return input.split(listOf(Regex("\r\n"))).map { it.split(listOf(Regex(" "))) }
        .map { Pair(it.first(), it.tail().dropLast(1)) }.map {
            val lights = it.first.drop(1).dropLast(1).map { c -> c != '.' }
            val buttons = it.second.map { b ->
                b.drop(1).dropLast(1).split(listOf(Regex(","))).fold(List(lights.size) { false }) { acc, s ->
                    val index = s.toInt()
                    acc.subList(0, index) + true + acc.subList(index + 1, acc.size)
                }
            }
            Pair(lights, buttons)
        }.sumOf { (lights, buttons) -> dfs(Int.MAX_VALUE, 0, lights, List(lights.size) { false }, buttons) }.toString()
}

fun solveTask2(input: String): String {
    val lines = input.split(listOf(Regex("\r\n"))).map { it.split(listOf(Regex(" "))) }
        .map { Pair(it.last(), it.tail().dropLast(1)) }.map {
            val lights = it.first.drop(1).dropLast(1).split(listOf(Regex(","))).map(String::toInt)
            val buttons = it.second.map { b ->
                b.drop(1).dropLast(1).split(listOf(Regex(","))).fold(List(lights.size) { 0 }) { acc, s ->
                    val index = s.toInt()
                    acc.subList(0, index) + 1 + acc.subList(index + 1, acc.size)
                }
            }
            Pair(lights, buttons)
        }
    return lines.sumOf { (lights, buttons) -> ilp(lights, buttons) }.toString()
}

fun dfs(minimum: Int, steps: Int, goal: List<Boolean>, current: List<Boolean>, buttons: List<List<Boolean>>): Int {
    if (steps >= minimum) return minimum
    if (current == goal) return steps
    return buttons.fold(minimum) { minAcc, button ->
        val next = current.mapIndexed { index, b -> if (button[index]) !b else b }
        min(minAcc, dfs(minAcc, steps + 1, goal, next, buttons.minusElement(button)))
    }
}

fun ilp(lights: List<Int>, buttons: List<List<Int>>): Long {
    Loader.loadNativeLibraries()
    val model = CpModel()
    val variables = buttons.indices.map { index -> model.newIntVar(0, Int.MAX_VALUE.toLong(), index.toString()) }

    // constraints
    lights.mapIndexed { index, light ->
        model.addEquality(buttons.map { it[index] }.foldIndexed(LinearExpr.newBuilder()) { buttonIndex, acc, value -> acc.addTerm(variables[buttonIndex], value.toLong()) }.build(), light.toLong())
    }

    val objective = variables.fold(LinearExpr.newBuilder()) { acc, variable -> acc.addTerm(variable, 1) }.build()
    model.minimize(objective)

    val solver = CpSolver()
    solver.solve(model)
    return solver.objectiveValue().toLong()
}
