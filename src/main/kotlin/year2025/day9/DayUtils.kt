package year2025.day9

import split
import toPair
import java.awt.Polygon
import java.awt.Rectangle
import java.awt.geom.Area
import kotlin.math.abs
import kotlin.math.min

fun solveTask1(input: String): String {
    val points = input.split(listOf(Regex("\r\n"))).map { it.split(listOf(Regex(","))).map { s -> s.toLong() }.toPair() }.withIndex()
    return points.flatMap { value -> points.filter { it.index > value.index }.map { i -> Pair(value.value, i.value) } }
        .maxOfOrNull { (val1, val2) -> (abs(val1.first - val2.first) + 1) * (abs(val1.second - val2.second) + 1) }
        .toString()
}

fun solveTask2(input: String): String {
    val points = input.split(listOf(Regex("\r\n"))).map { it.split(listOf(Regex(","))).map { s -> s.toInt() }.toPair() }.withIndex()
    val xs = input.split(listOf(Regex("\r\n"))).map { it.split(listOf(Regex(","))).map { s -> s.toInt() }.toPair() }.map { it.first }
    val ys = input.split(listOf(Regex("\r\n"))).map { it.split(listOf(Regex(","))).map { s -> s.toInt() }.toPair() }.map { it.second }
    val shape = Area(Polygon(xs.toIntArray(), ys.toIntArray(), xs.size))
    val rectangleDiagonals = points.flatMap { value -> points.filter { it.index > value.index }.map { i -> Pair(value.value, i.value) } }
        .sortedByDescending { (val1, val2) -> (abs(val1.first - val2.first) + 1) * (abs(val1.second - val2.second) + 1) }
    val biggestRectangle = rectangleDiagonals.first { d ->
        val rectangle = Rectangle(min(d.first.first, d.second.first), min(d.first.second, d.second.second), abs(d.first.first - d.second.first), abs(d.first.second - d.second.second))
        shape.contains(rectangle)
    }
    return ((abs(biggestRectangle.first.first - biggestRectangle.second.first) + 1) * (abs(biggestRectangle.first.second - biggestRectangle.second.second) + 1)).toString()
}
