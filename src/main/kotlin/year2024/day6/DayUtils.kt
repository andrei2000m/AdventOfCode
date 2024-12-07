package year2024.day6

import Directions
import Directions.NORTH
import Point
import plus

fun solveTask1(input: String): String {
    val map = input.lines()
    val guardX = map.map { it.indexOf("^") }.indexOfFirst { it != -1 }
    val guardY = map.map { it.indexOf("^") }.find { it != -1 }!!
    return generateSequence(Triple(0, setOf<Point>(), DirectionedPoint(Point(guardX, guardY), NORTH))) {
        val proposedLocation = DirectionedPoint(it.third.point + it.third.direction.move, it.third.direction)
        val newLocation = if (map.getOrNull(proposedLocation.point.first)?.getOrNull(proposedLocation.point.second) == '#') DirectionedPoint(it.third.point, it.third.direction.turn90Clockwise()) else proposedLocation
        Triple(
            it.first + if (it.second.contains(it.third.point)) 0 else 1,
            it.second.plus(it.third.point),
            newLocation
        )
    }.dropWhile { map.getOrNull(it.third.point.first)?.getOrNull(it.third.point.second) != null }.first().first.toString()
}

fun solveTask2(input: String): String {
    val map = input.lines()
    val guardX = map.map { it.indexOf("^") }.indexOfFirst { it != -1 }
    val guardY = map.map { it.indexOf("^") }.find { it != -1 }!!
    val possiblePositions = generateSequence(Triple(0, setOf<Point>(), DirectionedPoint(Point(guardX, guardY), NORTH))) {
        val proposedLocation = DirectionedPoint(it.third.point + it.third.direction.move, it.third.direction)
        val newLocation = if (map.getOrNull(proposedLocation.point.first)?.getOrNull(proposedLocation.point.second) == '#') DirectionedPoint(it.third.point, it.third.direction.turn90Clockwise()) else proposedLocation
        Triple(
            it.first + if (it.second.contains(it.third.point)) 0 else 1,
            it.second.plus(it.third.point),
            newLocation
        )
    }.dropWhile { map.getOrNull(it.third.point.first)?.getOrNull(it.third.point.second) != null }.first().second
    return possiblePositions.sumOf { checkObstacle(map, it, guardX, guardY) }.toString()
}

fun checkObstacle(map: List<String>, obstacle: Point, guardX: Int, guardY: Int): Int {
    return generateSequence(Triple(0, setOf(DirectionedPoint(Point(guardX, guardY), NORTH)), DirectionedPoint(Point(guardX, guardY), NORTH))) {
        val proposedLocation = DirectionedPoint(it.third.point + it.third.direction.move, it.third.direction)
        val newLocation = if ((map.getOrNull(proposedLocation.point.first)?.getOrNull(proposedLocation.point.second) == '#')
            || (proposedLocation.point.first == obstacle.first && proposedLocation.point.second == obstacle.second)) DirectionedPoint(it.third.point, it.third.direction.turn90Clockwise()) else proposedLocation
        if (it.second.contains(newLocation)) Triple(1, it.second, DirectionedPoint(Point(-1, -1), NORTH))
        else Triple(it.first, it.second.plus(newLocation), newLocation)
    }.dropWhile { map.getOrNull(it.third.point.first)?.getOrNull(it.third.point.second) != null }.first().first
}

data class DirectionedPoint(val point: Point, val direction: Directions)