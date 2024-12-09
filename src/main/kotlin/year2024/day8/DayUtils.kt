package year2024.day8

import Point
import minus
import plus

fun solveTask1(input: String): String {
    val grid = input.lines().foldIndexed(mapOf<Point, Char>()) { indexX, map, line ->
        line.foldIndexed(map) { indexY, innerMap, chr ->
            innerMap.plus(Point(indexX, indexY) to chr)
        }
    }
    val maxPoint = grid.keys.maxBy { it.first + it.second }
    val antennas = grid.filter { it.value.toString().matches(Regex("[a-zA-Z0-9]")) }.entries.groupBy({it.value}) { it.key }
    return antennas.entries.fold(setOf<Point>()) { positionSet, antennaGroup ->
        val antennaList = antennaGroup.value
        val pairs = antennaList.flatMapIndexed { index, antenna ->
            antennaList.slice(index + 1 until antennaList.size).map { Pair(antenna, it) }
        }
        val antiNodePositions = pairs.fold(setOf<Point>()) { set, pair ->
            val d = pair.first - pair.second
            val p1 = pair.first + d
            val p2 = pair.second - d
            setOf(p1, p2).filter { it.inBounds(Point(0, 0), maxPoint) }.plus(set).toSet()
        }
        positionSet.plus(antiNodePositions)
    }.size.toString()
}

fun solveTask2(input: String): String {
    val grid = input.lines().foldIndexed(mapOf<Point, Char>()) { indexX, map, line ->
        line.foldIndexed(map) { indexY, innerMap, chr ->
            innerMap.plus(Point(indexX, indexY) to chr)
        }
    }
    val maxPoint = grid.keys.maxBy { it.first + it.second }
    val antennas = grid.filter { it.value.toString().matches(Regex("[a-zA-Z0-9]")) }.entries.groupBy({it.value}) { it.key }
    return antennas.entries.fold(setOf<Point>()) { positionSet, antennaGroup ->
        val antennaList = antennaGroup.value
        val pairs = antennaList.flatMapIndexed { index, antenna ->
            antennaList.slice(index + 1 until antennaList.size).map { Pair(antenna, it) }
        }
        val antiNodePositions = pairs.fold(setOf<Point>()) { set, pair ->
            val d = pair.first - pair.second
            val set1 = generateSequence(pair.first) { it + d }.takeWhile { it.inBounds(Point(0, 0), maxPoint) }.toSet()
            val set2 = generateSequence(pair.second) { it - d }.takeWhile { it.inBounds(Point(0, 0), maxPoint) }.toSet()
            set.plus(set1).plus(set2)
        }
        positionSet.plus(antiNodePositions)
    }.size.toString()
}

fun Point.inBounds(minPoint: Point, maxPoint: Point): Boolean =
    this.first >= minPoint.first && this.second >= minPoint.second && this.first <= maxPoint.first && this.second <= maxPoint.second