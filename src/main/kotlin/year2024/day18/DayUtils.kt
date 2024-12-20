package year2024.day18

import Directions
import Point
import PriorityQueue
import plus

fun solveTask1(input: String, simulatedBytes: Int = 1024, maxSize: Int = 70): String {
    val coordsList = input.lines().map { it.split(',') }
    val obs = coordsList.take(simulatedBytes).map { (i, j) -> Pair(i.toInt(), j.toInt()) }
    val listOfPositions = (0..maxSize).flatMap { i ->
        (0..maxSize).map { j ->
            if (i == 0 && j == 0) Pair(Point(i, j), 0) else if (Pair(i, j) !in obs) Pair(Point(i, j), Int.MAX_VALUE) else Pair(Point(-1, -1), -1)
        }
    }.filter { (_, j) -> j != -1 }
    val queue = PriorityQueue(listOfPositions) { a, b -> a.second.compareTo(b.second) }
    return dijkstra(queue, Point(maxSize, maxSize)).toString()
}

fun solveTask2(input: String, simulatedBytes: Int = 1024, maxSize: Int = 70): String {
    val coordsList = input.lines().map { it.split(',') }
    val index = generateSequence(Pair(simulatedBytes + 1, 0)) { (n, _) ->
        val obs = coordsList.take(n).map { (i, j) -> Pair(i.toInt(), j.toInt()) }
        val listOfPositions = (0..maxSize).flatMap { i ->
            (0..maxSize).map { j ->
                if (i == 0 && j == 0) Pair(Point(i, j), 0) else if (Pair(i, j) !in obs) Pair(Point(i, j), Int.MAX_VALUE) else Pair(Point(-1, -1), -1)
            }
        }.filter { (_, j) -> j != -1 }
        val queue = PriorityQueue(listOfPositions) { a, b -> a.second.compareTo(b.second) }
        val score = dijkstra(queue, Point(maxSize, maxSize))
        Pair(n + 1, score)
    }.dropWhile { it.second != Int.MAX_VALUE }.first().first - 2 // -1 since we add 1 at the end, and -1 since n is 1-indexed
    return coordsList[index].joinToString(",")
}

tailrec fun dijkstra(queue: PriorityQueue<Pair<Point, Int>>, endPosition: Point): Int {
    val (currentPosition, currentScore) = queue.peek()
    return if (currentPosition == endPosition || currentScore == Int.MAX_VALUE) currentScore
    else {
        val northPos = currentPosition + Directions.NORTH.move
        val eastPos = currentPosition + Directions.EAST.move
        val southPos = currentPosition + Directions.SOUTH.move
        val westPos = currentPosition + Directions.WEST.move

        val neighbours = queue.filter { it.first == northPos || it.first == eastPos || it.first == southPos || it.first == westPos }
        val nextNeighbours = neighbours.map { Pair(it.first, currentScore + 1) }

        dijkstra(queue - (neighbours + queue.peek()).toSet() + nextNeighbours, endPosition)
    }
}