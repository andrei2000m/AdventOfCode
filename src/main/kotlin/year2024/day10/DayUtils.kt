package year2024.day10

import Directions4.EAST
import Directions4.NORTH
import Directions4.SOUTH
import Directions4.WEST
import Point
import plus

fun solveTask1(input: String): String {
    val grid = input.lines().map { line -> line.map { c -> c.digitToInt() } }
    return grid.mapIndexed { lineIndex, line -> line.mapIndexed { pointIndex, point -> if (point == 0) climb(grid, Point(lineIndex, pointIndex)).size else 0 }.sum() }.sum().toString()
}

fun climb(map: List<List<Int>>, location: Point): Set<Point> {
    return if (map[location.first][location.second] == 9) setOf(location)
    else {
        val northPoint = location + NORTH.move
        val northSet = if (northPoint.inGrid(map) && map[northPoint.first][northPoint.second] - map[location.first][location.second] == 1) climb(map, northPoint) else setOf()
        val southPoint = location + SOUTH.move
        val southSet = if (southPoint.inGrid(map) && map[southPoint.first][southPoint.second] - map[location.first][location.second] == 1) climb(map, southPoint) else setOf()
        val eastPoint = location + EAST.move
        val eastSet = if (eastPoint.inGrid(map) && map[eastPoint.first][eastPoint.second] - map[location.first][location.second] == 1) climb(map, eastPoint) else setOf()
        val westPoint = location + WEST.move
        val westSet = if (westPoint.inGrid(map) && map[westPoint.first][westPoint.second] - map[location.first][location.second] == 1) climb(map, westPoint) else setOf()
        northSet + southSet + eastSet + westSet
    }
}

fun solveTask2(input: String): String {
    val grid = input.lines().map { line -> line.map { c -> c.digitToInt() } }
    return grid.mapIndexed { lineIndex, line -> line.mapIndexed { pointIndex, point -> if (point == 0) climb2(grid, Point(lineIndex, pointIndex)) else 0 }.sum() }.sum().toString()
}

fun climb2(map: List<List<Int>>, location: Point): Int {
    return if (map[location.first][location.second] == 9) 1
    else {
        val northPoint = location + NORTH.move
        val northScore = if (northPoint.inGrid(map) && map[northPoint.first][northPoint.second] - map[location.first][location.second] == 1) climb2(map, northPoint) else 0
        val southPoint = location + SOUTH.move
        val southScore = if (southPoint.inGrid(map) && map[southPoint.first][southPoint.second] - map[location.first][location.second] == 1) climb2(map, southPoint) else 0
        val eastPoint = location + EAST.move
        val eastScore = if (eastPoint.inGrid(map) && map[eastPoint.first][eastPoint.second] - map[location.first][location.second] == 1) climb2(map, eastPoint) else 0
        val westPoint = location + WEST.move
        val westScore = if (westPoint.inGrid(map) && map[westPoint.first][westPoint.second] - map[location.first][location.second] == 1) climb2(map, westPoint) else 0
        northScore + southScore + eastScore + westScore
    }
}

fun Point.inGrid(map: List<List<Int>>): Boolean =
    this.first >= 0 && this.second >= 0 && this.first < map.size && this.second < map[0].size