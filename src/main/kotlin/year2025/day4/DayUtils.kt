package year2025.day4

import Directions8
import Point
import indicesOf
import plus

fun solveTask1(input: String): String {
    val grid = input.lines().map { line -> line.toList() }
    val lineLength = grid[0].size
    // We add dots on all sides to prevent out of bounds
    val safe = listOf(".".repeat(lineLength + 2).toList())
    val safeGrid = safe + grid.map { line -> listOf('.') + line + listOf('.') } + safe
    val rolls = safeGrid.flatMapIndexed { index, line -> line.indicesOf('@').map { index to it } }
    return rolls.filter { findRollsAround(it, safeGrid) < 4 }.size.toString()
}

fun solveTask2(input: String): String {
    val grid = input.lines().map { line -> line.toList() }
    val lineLength = grid[0].size
    // We add dots on all sides to prevent out of bounds
    val safe = listOf(".".repeat(lineLength + 2).toList())
    val safeGrid = safe + grid.map { line -> listOf('.') + line + listOf('.') } + safe
    return removeRolls(safeGrid, 0).toString()
}

fun removeRolls(grid: List<List<Char>>, rollsRemoved: Long): Long {
    val rolls = grid.flatMapIndexed { index, line -> line.indicesOf('@').map { index to it } }
    val removableRolls = rolls.filter { findRollsAround(it, grid) < 4 }
    val newGrid = grid.mapIndexed { index, line -> line.mapIndexed { lineIndex, char -> if (removableRolls.contains(Pair(index, lineIndex))) '.' else char } }
    return if (newGrid == grid) rollsRemoved else removeRolls(newGrid, rollsRemoved + removableRolls.size)
}

fun findRollsAround(coords: Point, grid: List<List<Char>>): Int {
    return Directions8.entries.map { it.move.plus(coords) }.filter { grid[it.first][it.second] == '@' }.size
}
