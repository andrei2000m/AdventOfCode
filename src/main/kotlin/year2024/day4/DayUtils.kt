package year2024.day4

fun solveTask1(input: String): String {
    val grid = input.lines().map { line -> line.toList() }
    val lineLength = grid[0].size
    // We add dots on all sides to prevent out of bounds
    val safe = listOf(".".repeat(lineLength + 6).toList(), ".".repeat(lineLength + 6).toList(), ".".repeat(lineLength + 6).toList())
    val safeGrid = safe + grid.map { line -> ".".repeat(3).toList() + line + ".".repeat(3).toList() } + safe
    val xs = safeGrid.flatMapIndexed { index, line -> line.indicesOf('X').map { index to it } }
    return xs.sumOf { coords -> "XMAS".findAll(safeGrid, coords) }.toString()
}

fun solveTask2(input: String): String {
    val grid = input.lines().map { line -> line.toList() }
    val lineLength = grid[0].size
    // We add dots on all sides to prevent out of bounds
    val safe = listOf(".".repeat(lineLength + 4).toList(), ".".repeat(lineLength + 4).toList(), ".".repeat(lineLength + 4).toList())
    val safeGrid = safe + grid.map { line -> ".".repeat(2).toList() + line + ".".repeat(2).toList() } + safe
    val aCoords = safeGrid.flatMapIndexed { index, line -> line.indicesOf('A').map { index to it } }
    return aCoords.sumOf { coords -> if ("MAS".findX(safeGrid, coords)) 1L else 0L }.toString()
}

fun String.findX(grid: List<List<Char>>, coords: Pair<Int, Int>): Boolean {
    val primary = setOf(grid[coords.first][coords.second], grid[coords.first + 1][coords.second + 1], grid[coords.first - 1][coords.second - 1])
    val secondary = setOf(grid[coords.first + 1][coords.second - 1], grid[coords.first][coords.second], grid[coords.first - 1][coords.second + 1])
    return (primary == secondary) && (primary == this.toSet())
}

fun String.findAll(grid: List<List<Char>>, coords: Pair<Int, Int>): Long {
    return (-1..1).flatMap { x -> (-1..1).map { y -> x to y } }.map { this[0].findWord(grid, coords, this.drop(1), it) }.sumOf { if (it) 1L else 0L }
}

fun Char.findWord(grid: List<List<Char>>, coords: Pair<Int, Int>, leftover: String, direction: Pair<Int, Int>): Boolean =
    if (grid[coords.first][coords.second] == this)
        if (leftover.isEmpty()) true
        else leftover[0].findWord(grid, Pair(coords.first + direction.first, coords.second + direction.second), leftover.drop(1), direction)
    else false

fun <T> List<T>.indicesOf(element: T): List<Int> = this.mapIndexedNotNull { index, t -> index.takeIf { t == element } }