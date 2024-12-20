package year2024.day14

import Point
import plus
import split
import kotlin.math.ceil
import kotlin.math.floor

fun solveTask1(input: String, xSize: Int = 101, ySize: Int = 103): String {
    val quadrants = input.lines().fold(Pair(Pair(0, 0), Pair(0, 0))) { (north, south), line ->
        val splitLine = line.split(listOf(Regex("p="), Regex(" v="), Regex(",")))
        val initialPosition = Point(splitLine[1].toInt(), splitLine[2].toInt())
        val velocity = Point(splitLine[3].toInt() * 100, splitLine[4].toInt() * 100)
        val unmoddedResult = initialPosition + velocity

        val proposedNewX = unmoddedResult.first % xSize
        val newX = if (proposedNewX < 0) proposedNewX + xSize else proposedNewX

        val proposedNewY = unmoddedResult.second % ySize
        val newY = if (proposedNewY < 0) proposedNewY + ySize else proposedNewY
        if (newX < floor((xSize.toFloat() - 1) / 2) && newY < floor((ySize.toFloat() - 1) / 2)) Pair(Pair(north.first + 1, north.second), south)
        else if (newX < floor((xSize.toFloat() - 1) / 2) && newY > ceil((ySize.toFloat() - 1) / 2)) Pair(north, Pair(south.first + 1, south.second))
        else if (newX > ceil((xSize.toFloat() - 1) / 2) && newY < floor((ySize.toFloat() - 1) / 2)) Pair(Pair(north.first, north.second + 1), south)
        else if (newX > ceil((xSize.toFloat() - 1) / 2) && newY > ceil((ySize.toFloat() - 1) / 2)) Pair(north, Pair(south.first, south.second + 1))
        else Pair(north, south)
    }
    return (quadrants.first.first * quadrants.first.second * quadrants.second.first * quadrants.second.second).toString()
}

fun solveTask2(input: String, xSize: Int = 101, ySize: Int = 103): String {
    val map = input.lines().map { line ->
        val splitLine = line.split(listOf(Regex("p="), Regex(" v="), Regex(",")))
        val initialPosition = Pair(splitLine[1].toInt(), splitLine[2].toInt())
        val velocity = Pair(splitLine[3].toInt(), splitLine[4].toInt())
        Pair(initialPosition, velocity)
    }
    generateMovement(1, map, xSize, ySize)
    return ""
}

tailrec fun generateMovement(index: Int, map: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>, xSize: Int, ySize: Int) {
    val positions = map.map { (p, v) -> move(p, v, index, xSize, ySize) }
    if (positions.any { position ->
            (positions.contains(Pair(position.first + 1, position.second)) &&
            positions.contains(Pair(position.first + 2, position.second)) &&
            positions.contains(Pair(position.first + 3, position.second)) &&
            positions.contains(Pair(position.first + 4, position.second)) &&
            positions.contains(Pair(position.first + 5, position.second)) &&
            positions.contains(Pair(position.first + 6, position.second)) &&
            positions.contains(Pair(position.first + 7, position.second)) &&
            positions.contains(Pair(position.first + 8, position.second)) &&
            positions.contains(Pair(position.first + 9, position.second))) ||
            (positions.contains(Pair(position.first, position.second + 1)) &&
            positions.contains(Pair(position.first, position.second + 2)) &&
            positions.contains(Pair(position.first, position.second + 3)) &&
            positions.contains(Pair(position.first, position.second + 4)) &&
            positions.contains(Pair(position.first, position.second + 5)) &&
            positions.contains(Pair(position.first, position.second + 6)) &&
            positions.contains(Pair(position.first, position.second + 7)) &&
            positions.contains(Pair(position.first, position.second + 8)) &&
            positions.contains(Pair(position.first, position.second + 9)))
        }) {
        (0..<ySize).forEachIndexed { yIndex, _ ->
            (0..<xSize).forEachIndexed { xIndex, _ ->
                if (positions.any { it == Pair(xIndex, yIndex) }) print("@")
                else print(".")
            }
            println()
        }
        println()
    }
    if (index < 10000) generateMovement(index + 1, map, xSize, ySize)
}

fun move(position: Pair<Int, Int>, velocity: Pair<Int, Int>, times: Int, xSize: Int, ySize: Int): Pair<Int, Int> {
    val initialPosition = Point(position.first, position.second)
    val adjustedVelocity = Point(velocity.first * times, velocity.second * times)
    val unmoddedResult = initialPosition + adjustedVelocity

    val proposedNewX = unmoddedResult.first % xSize
    val newX = if (proposedNewX < 0) proposedNewX + xSize else proposedNewX

    val proposedNewY = unmoddedResult.second % ySize
    val newY = if (proposedNewY < 0) proposedNewY + ySize else proposedNewY

    return Pair(newX, newY)
}