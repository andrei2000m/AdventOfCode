package year2024.day16

import Directions4
import Directions4.EAST
import Directions4.NORTH
import Directions4.SOUTH
import Directions4.WEST
import Point
import PriorityQueue
import plus

fun solveTask1(input: String): String {
    val grid = input.lines()
    val (endPosition, queue) = grid.foldIndexed(Pair(Point(0, 0),
        PriorityQueue<Pair<Point, Pair<Int, Directions4>>>(listOf()) { a, b -> if (a.second.first < b.second.first) -1 else if (a.second.first == b.second.first) 0 else 1 }))
    { i, pair, line ->
        line.foldIndexed(pair) { j, (endPos, queue), char ->
            when (char) {
                'E' -> Pair(Point(i, j), queue + listOf(Point(i, j) to Pair(Int.MAX_VALUE, EAST), Point(i, j) to Pair(Int.MAX_VALUE, WEST), Point(i, j) to Pair(Int.MAX_VALUE, SOUTH), Point(i, j) to Pair(Int.MAX_VALUE, NORTH)))
                'S' -> Pair(endPos, queue + listOf(Point(i, j) to Pair(0, EAST), Point(i, j) to Pair(Int.MAX_VALUE, WEST), Point(i, j) to Pair(Int.MAX_VALUE, SOUTH), Point(i, j) to Pair(Int.MAX_VALUE, NORTH)))
                '#' -> Pair(endPos, queue)
                else -> Pair(endPos, queue + listOf(Point(i, j) to Pair(Int.MAX_VALUE, EAST), Point(i, j) to Pair(Int.MAX_VALUE, WEST), Point(i, j) to Pair(Int.MAX_VALUE, SOUTH), Point(i, j) to Pair(Int.MAX_VALUE, NORTH)))
            }
        }
    }
    return dijkstra(queue, endPosition).toString()
}

tailrec fun dijkstra(queue: PriorityQueue<Pair<Point, Pair<Int, Directions4>>>, endPosition: Point): Int {
    val (currentPosition, currentInfo) = queue.peek()
    val (currentScore, currentDirection) = currentInfo
    return if (currentPosition == endPosition) return currentScore
    else {
        val nextPos = currentPosition + currentDirection.move
        val clockRotateDirection = currentDirection.turn90Clockwise()
        val clockCounterDirection = currentDirection.turn90Counter()

        val neighbours = queue.filter { (it.first == nextPos && it.second.second == currentDirection) || (it.first == currentPosition && (it.second.second == clockRotateDirection || it.second.second == clockCounterDirection) ) }
        val newNeighbours = neighbours
            .map { oldNeighbour ->
                if (oldNeighbour.second.second != currentDirection) {
                    if (oldNeighbour.second.first > currentScore + 1000) oldNeighbour.first to Pair(currentScore + 1000, oldNeighbour.second.second)
                    else oldNeighbour
                } else {
                    if (oldNeighbour.second.first > currentScore + 1) oldNeighbour.first to Pair(currentScore + 1, oldNeighbour.second.second)
                    else oldNeighbour
            }
        }

        dijkstra(queue - (neighbours + queue.peek()).toSet() + newNeighbours, endPosition)
    }
}

fun solveTask2(input: String): String {
    val grid = input.lines()
    val (endPosition, queue, map) = grid.foldIndexed(Triple(Point(0, 0),
        PriorityQueue<Pair<Point, Pair<Int, Directions4>>>(listOf()) { a, b -> if (a.second.first < b.second.first) -1 else if (a.second.first == b.second.first) 0 else 1 },
        mapOf<Pair<Point, Directions4>, Pair<Int, Set<Pair<Point, Directions4>>>>()))
    { i, triple, line ->
        line.foldIndexed(triple) { j, (endPos, queue, map), char ->
            when (char) {
                'E' -> Triple(Point(i, j),
                    queue + listOf(Point(i, j) to Pair(Int.MAX_VALUE, EAST), Point(i, j) to Pair(Int.MAX_VALUE, WEST), Point(i, j) to Pair(Int.MAX_VALUE, SOUTH), Point(i, j) to Pair(Int.MAX_VALUE, NORTH)),
                    map + listOf(Pair(Point(i, j), EAST) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), SOUTH) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), WEST) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), NORTH) to Pair(Int.MAX_VALUE, setOf())))
                'S' -> Triple(endPos,
                    queue + listOf(Point(i, j) to Pair(0, EAST), Point(i, j) to Pair(Int.MAX_VALUE, WEST), Point(i, j) to Pair(Int.MAX_VALUE, SOUTH), Point(i, j) to Pair(Int.MAX_VALUE, NORTH)),
                    map + listOf(Pair(Point(i, j), EAST) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), SOUTH) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), WEST) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), NORTH) to Pair(Int.MAX_VALUE, setOf())))
                '#' -> Triple(endPos, queue, map)
                else -> Triple(endPos,
                    queue + listOf(Point(i, j) to Pair(Int.MAX_VALUE, EAST), Point(i, j) to Pair(Int.MAX_VALUE, WEST), Point(i, j) to Pair(Int.MAX_VALUE, SOUTH), Point(i, j) to Pair(Int.MAX_VALUE, NORTH)),
                    map + listOf(Pair(Point(i, j), EAST) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), SOUTH) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), WEST) to Pair(Int.MAX_VALUE, setOf()), Pair(Point(i, j), NORTH) to Pair(Int.MAX_VALUE, setOf())))
            }
        }
    }
    return dijkstra2(queue, endPosition, map).countPoints(setOf(Pair(endPosition, EAST),Pair(endPosition, SOUTH), Pair(endPosition, WEST), Pair(endPosition, NORTH)), setOf()).size.toString()
}

tailrec fun dijkstra2(queue: PriorityQueue<Pair<Point, Pair<Int, Directions4>>>, endPosition: Point, prevNodes: Map<Pair<Point, Directions4>, Pair<Int, Set<Pair<Point, Directions4>>>>): Map<Pair<Point, Directions4>, Set<Pair<Point, Directions4>>> {
    val (currentPosition, currentInfo) = queue.peek()
    val (currentScore, currentDirection) = currentInfo
    return if (currentPosition == endPosition) return prevNodes.map { (k, v) -> k to v.second }.toMap()
    else {
        val nextPos = currentPosition + currentDirection.move
        val clockRotateDirection = currentDirection.turn90Clockwise()
        val clockCounterDirection = currentDirection.turn90Counter()

        val nextPrevNodes = prevNodes + queue.filter { (it.first == nextPos && it.second.second == currentDirection) || (it.first == currentPosition && (it.second.second == clockRotateDirection || it.second.second == clockCounterDirection)) }
            .mapNotNull { neighbour ->
                if (neighbour.second.second != currentDirection) {
                    if (currentScore + 1000 < prevNodes[neighbour.first to neighbour.second.second]!!.first) Pair(neighbour.first, neighbour.second.second) to Pair(currentScore + 1000, setOf(Pair(currentPosition, currentDirection)))
                    else if (currentScore + 1000 == prevNodes[neighbour.first to neighbour.second.second]!!.first) {
                        val currentSet = prevNodes[neighbour.first to neighbour.second.second]!!.second
                        Pair(neighbour.first, neighbour.second.second) to Pair(currentScore + 1000, (currentSet + Pair(currentPosition, currentDirection)))
                    } else null
                } else {
                    if (currentScore + 1 < prevNodes[neighbour.first to neighbour.second.second]!!.first) Pair(neighbour.first, neighbour.second.second) to Pair(currentScore + 1, setOf(Pair(currentPosition, currentDirection)))
                    else if (currentScore + 1 == prevNodes[neighbour.first to neighbour.second.second]!!.first) {
                        val currentSet = prevNodes[neighbour.first to neighbour.second.second]!!.second
                        Pair(neighbour.first, neighbour.second.second) to Pair(currentScore + 1, (currentSet + Pair(currentPosition, currentDirection)))
                    } else null
                }
            }

        val neighbours = queue.filter { (it.first == nextPos && it.second.second == currentDirection) || (it.first == currentPosition && (it.second.second == clockRotateDirection || it.second.second == clockCounterDirection) ) }
        val newNeighbours = neighbours
            .map { oldNeighbour ->
                if (oldNeighbour.second.second != currentDirection) {
                    if (oldNeighbour.second.first > currentScore + 1000) oldNeighbour.first to Pair(currentScore + 1000, oldNeighbour.second.second)
                    else oldNeighbour
                } else {
                    if (oldNeighbour.second.first > currentScore + 1) oldNeighbour.first to Pair(currentScore + 1, oldNeighbour.second.second)
                    else oldNeighbour
                }
            }

        dijkstra2(queue - (neighbours + queue.peek()).toSet() + newNeighbours, endPosition, nextPrevNodes)
    }
}

tailrec fun Map<Pair<Point, Directions4>, Set<Pair<Point, Directions4>>>.countPoints(currentPositions: Set<Pair<Point, Directions4>>, previousPositions: Set<Pair<Point, Directions4>>): Set<Point> {
    return if (currentPositions.isEmpty()) previousPositions.map { it.first }.toSet()
    else this.countPoints(currentPositions.flatMap { this[it]!! }.toSet() - previousPositions, previousPositions + currentPositions)
}