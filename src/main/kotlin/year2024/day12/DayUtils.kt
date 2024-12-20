package year2024.day12

import Directions
import Directions.EAST
import Directions.NORTH
import Directions.SOUTH
import Directions.WEST
import Point
import plus

fun solveTask1(input: String): String {
    val parsedInput = input.lines()
    val perimeterMap = parsedInput.mapIndexed { i, line -> line.mapIndexed { j, _ -> perimeter(Point(i, j), parsedInput) } }
    return parsedInput.foldIndexed(Pair<Long, List<Point>>(0L, listOf())) { i, (price, visited), line ->
        val (newPrice, newLineVisit) = line.foldIndexed(Pair(0L, visited)) { j, (linePrice, lineVisited), _ ->
            val point = Point(i, j)
            val (newArea, perimeter, newVisit) = if (!lineVisited.contains(point)) point.visit(parsedInput, perimeterMap, lineVisited + point) else Triple(0L, 0L, lineVisited)
            Pair(linePrice + newArea * perimeter, newVisit)
        }
        Pair(price + newPrice, newLineVisit)
    }.first.toString()
}

fun solveTask2(input: String): String {
    val parsedInput = input.lines()
    val perimeterMap = parsedInput.mapIndexed { i, line -> line.mapIndexed { j, _ -> perimeter2(Point(i, j), parsedInput) } }
    return parsedInput.foldIndexed(Pair<Long, List<Point>>(0L, listOf())) { i, (price, visited), line ->
        val (newPrice, newLineVisit) = line.foldIndexed(Pair(0L, visited)) { j, (linePrice, lineVisited), char ->
            val point = Point(i, j)
            val (newArea, perimeter, newVisit) = if (!lineVisited.contains(point)) point.visit2(parsedInput, perimeterMap, lineVisited + point) else Triple(0L, 0L, lineVisited)
            Pair(linePrice + newArea * perimeter, newVisit)
        }
        Pair(price + newPrice, newLineVisit)
    }.first.toString()
}

fun Point.visit2(grid: List<String>, perimeterMap: List<List<List<Directions>>>, visited: List<Point>): Triple<Long, Long, List<Point>> {
    val (x, y) = this
    val char = grid[x][y]

    val north = this + NORTH.move
    val south = this + SOUTH.move
    val east = this + EAST.move
    val west = this + WEST.move

    val borderNorth = if (perimeterMap[x][y].contains(NORTH)
        && (!east.inBoundsOf(grid) || grid[east.first][east.second] != char || !visitedBorder(east, char, grid, EAST, NORTH, visited, perimeterMap))
        && (!west.inBoundsOf(grid) || grid[west.first][west.second] != char || !visitedBorder(west, char, grid, WEST, NORTH, visited, perimeterMap)))
        1 else 0
    val borderSouth = if (perimeterMap[x][y].contains(SOUTH)
        && (!east.inBoundsOf(grid) || grid[east.first][east.second] != char || !visitedBorder(east, char, grid, EAST, SOUTH, visited, perimeterMap))
        && (!west.inBoundsOf(grid) || grid[west.first][west.second] != char || !visitedBorder(west, char, grid, WEST, SOUTH, visited, perimeterMap)))
        1 else 0
    val borderEast = if (perimeterMap[x][y].contains(EAST)
        && (!north.inBoundsOf(grid) || grid[north.first][north.second] != char || !visitedBorder(north, char, grid, NORTH, EAST, visited, perimeterMap))
        && (!south.inBoundsOf(grid) || grid[south.first][south.second] != char || !visitedBorder(south, char, grid, SOUTH, EAST, visited, perimeterMap)))
        1 else 0
    val borderWest = if (perimeterMap[x][y].contains(WEST)
        && (!north.inBoundsOf(grid) || grid[north.first][north.second] != char || !visitedBorder(north, char, grid, NORTH, WEST, visited, perimeterMap))
        && (!south.inBoundsOf(grid) || grid[south.first][south.second] != char || !visitedBorder(south, char, grid, SOUTH, WEST, visited, perimeterMap)))
        1 else 0

    val (areaNorth, perimeterNorth, visitedNorth) = if (north.inBoundsOf(grid) && grid[north.first][north.second] == char && !visited.contains(north)) north.visit2(grid, perimeterMap, visited + north) else Triple(0L, 0L, visited)
    val (areaSouth, perimeterSouth, visitedSouth) = if (south.inBoundsOf(grid) && grid[south.first][south.second] == char && !visitedNorth.contains(south)) south.visit2(grid, perimeterMap, visitedNorth + south) else Triple(0L, 0L, visitedNorth)
    val (areaEast, perimeterEast, visitedEast) = if (east.inBoundsOf(grid) && grid[east.first][east.second] == char && !visitedSouth.contains(east)) east.visit2(grid, perimeterMap, visitedSouth + east) else Triple(0L, 0L, visitedSouth)
    val (areaWest, perimeterWest, visitedWest) = if (west.inBoundsOf(grid) && grid[west.first][west.second] == char && !visitedEast.contains(west)) west.visit2(grid, perimeterMap, visitedEast + west) else Triple(0L, 0L, visitedEast)
    return Triple(areaNorth + areaSouth + areaEast + areaWest + 1, perimeterNorth + perimeterSouth + perimeterWest + perimeterEast + borderNorth + borderSouth + borderWest + borderEast, visitedWest)
}

fun visitedBorder(point: Point, char: Char, grid: List<String>, direction: Directions, borderDirection: Directions, visited: List<Point>, perimeterMap: List<List<List<Directions>>>): Boolean {
    if (grid[point.first][point.second] != char) return false
    if (visited.contains(point) && perimeterMap[point.first][point.second].contains(borderDirection)) return true
    val newPoint = point + direction.move
    if (newPoint.inBoundsOf(grid) && grid[newPoint.first][newPoint.second] == char && perimeterMap[point.first][point.second].contains(borderDirection)) return visitedBorder(newPoint, char, grid, direction, borderDirection, visited, perimeterMap)
    return false
}

fun perimeter2(coords: Point, grid: List<String>): List<Directions> {
    val (x, y) = coords
    val char = grid[x][y]

    val north = coords + NORTH.move
    val perimeter1 = if (north.inBoundsOf(grid) && grid[north.first][north.second] == char) listOf() else listOf(NORTH)
    val south = coords + SOUTH.move
    val perimeter2 = if (south.inBoundsOf(grid) && grid[south.first][south.second] == char) perimeter1 else perimeter1 + SOUTH
    val east = coords + EAST.move
    val perimeter3 = if (east.inBoundsOf(grid) && grid[east.first][east.second] == char) perimeter2 else perimeter2 + EAST
    val west = coords + WEST.move
    return if (west.inBoundsOf(grid) && grid[west.first][west.second] == char) perimeter3 else perimeter3 + WEST
}


fun Point.visit(grid: List<String>, perimeterMap: List<List<Int>>, visited: List<Point>): Triple<Long, Long, List<Point>> {
    val (x, y) = this
    val char = grid[x][y]

    val north = this + NORTH.move
    val (areaNorth, perimeterNorth, visitedNorth) = if (north.inBoundsOf(grid) && grid[north.first][north.second] == char && !visited.contains(north)) north.visit(grid, perimeterMap, visited + north) else Triple(0L, 0L, visited)
    val south = this + SOUTH.move
    val (areaSouth, perimeterSouth, visitedSouth) = if (south.inBoundsOf(grid) && grid[south.first][south.second] == char && !visitedNorth.contains(south)) south.visit(grid, perimeterMap, visitedNorth + south) else Triple(0L, 0L, visitedNorth)
    val east = this + EAST.move
    val (areaEast, perimeterEast, visitedEast) = if (east.inBoundsOf(grid) && grid[east.first][east.second] == char && !visitedSouth.contains(east)) east.visit(grid, perimeterMap, visitedSouth + east) else Triple(0L, 0L, visitedSouth)
    val west = this + WEST.move
    val (areaWest, perimeterWest, visitedWest) = if (west.inBoundsOf(grid) && grid[west.first][west.second] == char && !visitedEast.contains(west)) west.visit(grid, perimeterMap, visitedEast + west) else Triple(0L, 0L, visitedEast)
    return Triple(areaNorth + areaSouth + areaEast + areaWest + 1, perimeterNorth + perimeterSouth + perimeterWest + perimeterEast + perimeterMap[x][y], visitedWest)
}

fun perimeter(coords: Point, grid: List<String>): Int {
    val (x, y) = coords
    val char = grid[x][y]

    val north = coords + NORTH.move
    val perimeter1 = if (north.inBoundsOf(grid) && grid[north.first][north.second] == char) 3 else 4
    val south = coords + SOUTH.move
    val perimeter2 = if (south.inBoundsOf(grid) && grid[south.first][south.second] == char) perimeter1 - 1 else perimeter1
    val east = coords + EAST.move
    val perimeter3 = if (east.inBoundsOf(grid) && grid[east.first][east.second] == char) perimeter2 - 1 else perimeter2
    val west = coords + WEST.move
    return if (west.inBoundsOf(grid) && grid[west.first][west.second] == char) perimeter3 - 1 else perimeter3
}

fun Point.inBoundsOf(grid: List<String>): Boolean {
    val (x, y) = this
    return x >= 0 && y >= 0 && x < grid.size && y < grid[0].length
}