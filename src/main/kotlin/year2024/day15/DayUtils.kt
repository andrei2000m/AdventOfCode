package year2024.day15

import Directions
import Directions.EAST
import Directions.NORTH
import Directions.SOUTH
import Directions.WEST
import Point
import plus

fun solveTask1(input: String): String {
    val (preGrid, preMoves) = input.split(Regex("\r?\n\r?\n"))
    val grid = preGrid.lines()
    val (initialPosition, boxes, obstacles) = grid.foldIndexed(Triple(Point(0, 0), listOf<Point>(), listOf<Point>())) { i, triple, line ->
        line.foldIndexed(triple) { j, (initPos, box, obs), char ->
            when (char) {
                'O' -> Triple(initPos, box + Point(i, j), obs)
                '@' -> Triple(Point(i, j), box, obs)
                '#' -> Triple(initPos, box, obs + Point(i, j))
                else -> Triple(initPos, box, obs)
            }
        }
    }

    val moves = preMoves.fold(listOf<Directions>()) { mvs, char ->
        when (char) {
            '<' -> mvs + WEST
            '>' -> mvs + EAST
            '^' -> mvs + NORTH
            'v' -> mvs + SOUTH
            else -> mvs
        }
    }

    return moves.fold(Pair(initialPosition, boxes)) { (currentPosition, currentBoxes), direction ->
        val newPosition = currentPosition + direction.move
        when (Point(newPosition.first, newPosition.second)) {
            in obstacles -> Pair(currentPosition, currentBoxes)
            in currentBoxes -> {
                val newBoxPosition = moveBox(newPosition, direction, obstacles, currentBoxes)

                if (newBoxPosition == Point(-1, -1)) Pair(currentPosition, currentBoxes)
                else Pair(newPosition, currentBoxes - newPosition + newBoxPosition)
            }
            else -> Pair(newPosition, currentBoxes)
        }
    }.second.sumOf { it.first.toLong() * 100L + it.second.toLong() }.toString()
}

tailrec fun moveBox(position: Point, direction: Directions, obstacles: List<Point>, boxes: List<Point>): Point {
    return when (val newPosition = position + direction.move) {
        in obstacles -> Point(-1, -1)
        in boxes -> moveBox(newPosition, direction, obstacles, boxes)
        else -> newPosition
    }
}

fun solveTask2(input: String): String {
    val (preGrid, preMoves) = input.split(Regex("\r?\n\r?\n"))
    val grid = preGrid.lines()
    val (initialPosition, boxes, obstacles) = grid.foldIndexed(Triple(Point(0, 0), listOf<Pair<Point, Point>>(), listOf<Point>())) { i, triple, line ->
        line.foldIndexed(triple) { j, (initPos, box, obs), char ->
            when (char) {
                'O' -> Triple(initPos, box + Pair(Point(i, 2 * j), Point(i, 2 * j + 1)), obs)
                '@' -> Triple(Point(i, 2 * j), box, obs)
                '#' -> Triple(initPos, box, obs + Point(i, 2 * j) + Point(i, 2 * j + 1))
                else -> Triple(initPos, box, obs)
            }
        }
    }

    val moves = preMoves.fold(listOf<Directions>()) { mvs, char ->
        when (char) {
            '<' -> mvs + WEST
            '>' -> mvs + EAST
            '^' -> mvs + NORTH
            'v' -> mvs + SOUTH
            else -> mvs
        }
    }

    return moves.fold(Pair(initialPosition, boxes)) { (currentPosition, currentBoxes), direction ->
        val newPosition = currentPosition + direction.move
        when (Point(newPosition.first, newPosition.second)) {
            in obstacles -> Pair(currentPosition, currentBoxes)
            in currentBoxes.map { it.first } -> {
                val (canMove, newBoxes) = moveBigBox(Pair(newPosition, newPosition + EAST.move), direction, obstacles, currentBoxes)

                if (!canMove) Pair(currentPosition, currentBoxes)
                else Pair(newPosition, newBoxes)
            }
            in currentBoxes.map { it.second } -> {
                val (canMove, newBoxes) = moveBigBox(Pair(newPosition + WEST.move, newPosition), direction, obstacles, currentBoxes)

                if (!canMove) Pair(currentPosition, currentBoxes)
                else Pair(newPosition, newBoxes)
            }
            else -> Pair(newPosition, currentBoxes)
        }
    }.second.sumOf { it.first.first.toLong() * 100L + it.first.second.toLong() }.toString()
}

fun moveBigBox(position: Pair<Point, Point>, direction: Directions, obstacles: List<Point>, boxes: List<Pair<Point, Point>>): Pair<Boolean, List<Pair<Point, Point>>> {
    val newPositions = Pair(position.first + direction.move, position.second + direction.move)
    val (canMoveFirst, interimBoxes) = when (val firstPosition = newPositions.first) {
        in boxes.map { it.first } -> moveBigBox(newPositions, direction, obstacles, boxes)
        in boxes.map { it.second } -> if (direction != EAST) moveBigBox(Pair(firstPosition + WEST.move, firstPosition), direction, obstacles, boxes)
                                      else Pair(true, boxes)
        in obstacles -> Pair(false, boxes)
        else -> Pair(true, boxes)
    }

    return if (!canMoveFirst) Pair(false, boxes)
    else {
        val (canMoveSecond, finalBoxes) = when (val secondPosition = newPositions.second) {
            in interimBoxes.map { it.first } -> if (direction != WEST) moveBigBox(Pair(secondPosition, secondPosition + EAST.move), direction, obstacles, interimBoxes)
                                                else Pair(true, interimBoxes)
            // This next condition should never happen as it is covered by the first when, but is included for case coverage
            in interimBoxes.map { it.second } -> moveBigBox(newPositions, direction, obstacles, interimBoxes)
            in obstacles -> Pair(false, interimBoxes)
            else -> Pair(true, interimBoxes)
        }

        if (!canMoveSecond) Pair(false, boxes)
        else Pair(true, finalBoxes - position + newPositions)
    }
}