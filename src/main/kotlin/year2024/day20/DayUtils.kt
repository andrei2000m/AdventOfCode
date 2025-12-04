package year2024.day20

import Directions4
import Point
import distance
import plus

fun solveTask1(input: String, cheatThreshold: Int = 100): String {
    val inputLines = input.lines()
    val (initialPosition, endPosition, grid) = inputLines.foldIndexed(Triple(Point(0, 0), Point(0, 0), listOf<Point>()))
    { i, triple, line ->
        line.foldIndexed(triple) { j, (initialPos, endPos, grid), char ->
            when (char) {
                'E' -> Triple(initialPos, Point(i, j), grid + Point(i, j))
                'S' -> Triple(Point(i, j), endPos, grid + Point(i, j))
                '#' -> Triple(initialPos, endPos, grid)
                else -> Triple(initialPos, endPos, grid + Point(i, j))
            }
        }
    }
    val traversal = traverse(listOf(), endPosition, 0, grid, initialPosition)
    return traversal.sumOf { cheat(it.first, it.second, traversal, cheatThreshold) }.toString()
}

fun solveTask2(input: String, cheatThreshold: Int = 100): String {
    val inputLines = input.lines()
    val (initialPosition, endPosition, grid) = inputLines.foldIndexed(Triple(Point(0, 0), Point(0, 0), listOf<Point>()))
    { i, triple, line ->
        line.foldIndexed(triple) { j, (initialPos, endPos, grid), char ->
            when (char) {
                'E' -> Triple(initialPos, Point(i, j), grid + Point(i, j))
                'S' -> Triple(Point(i, j), endPos, grid + Point(i, j))
                '#' -> Triple(initialPos, endPos, grid)
                else -> Triple(initialPos, endPos, grid + Point(i, j))
            }
        }
    }
    val traversal = traverse(listOf(), endPosition, 0, grid, initialPosition)
    return traversal.sumOf { cheat2(it.first, it.second, traversal, cheatThreshold) }.toString()
}

tailrec fun traverse(currentTraversal: List<Pair<Point, Int>>, currentPosition: Point, currentDistance: Int, grid: List<Point>, startPosition: Point): List<Pair<Point, Int>> {
    return if (currentPosition == startPosition) currentTraversal + Pair(startPosition, currentDistance)
    else {
        val northPos = currentPosition + Directions4.NORTH.move
        val eastPos = currentPosition + Directions4.EAST.move
        val southPos = currentPosition + Directions4.SOUTH.move
        val westPos = currentPosition + Directions4.WEST.move

        val neighbour = grid.first { it == northPos || it == eastPos || it == southPos || it == westPos }

        traverse(currentTraversal + Pair(currentPosition, currentDistance),neighbour, currentDistance + 1, grid - currentPosition, startPosition)
    }
}

fun cheat(currentPosition: Point, currentDistance: Int, grid: List<Pair<Point, Int>>, cheatThreshold: Int): Int {
    val cheatNeighbours = grid.filter { distance(currentPosition, it.first) == 2 }
    return cheatNeighbours.map { it.second - currentDistance - 2 }.count { it >= cheatThreshold }
}

fun cheat2(currentPosition: Point, currentDistance: Int, grid: List<Pair<Point, Int>>, cheatThreshold: Int): Int {
    val cheatNeighbours = grid.filter { distance(currentPosition, it.first) <= 20 }
    return cheatNeighbours.map { it.second - currentDistance - distance(currentPosition, it.first) }.count { it >= cheatThreshold }
}