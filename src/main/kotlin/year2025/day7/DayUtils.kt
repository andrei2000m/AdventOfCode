package year2025.day7

import split

fun solveTask1(input: String): String {
    val grid = input.split(listOf(Regex("\r\n"))).map { it.toList() }
    val firstLine = grid[0].map { if (it == 'S') '|' else it }
    return calculateBeamSplits(1, grid, firstLine, 0).toString()
}

fun solveTask2(input: String): String {
    val grid = input.split(listOf(Regex("\r\n"))).map { it.toList() }
    val firstLine = grid[0].map { if (it == 'S') 1L else 0L }
    return calculateTimelines(1, grid, firstLine).toString()
}

tailrec fun calculateBeamSplits(row: Int, grid: List<List<Char>>, beamsOnPreviousRow: List<Char>, beamSplits: Long): Long {
    return if (row >= grid.size) beamSplits
    else {
        val currentRow = grid[row]
        val (splitsOnRow, beamsOnCurrentRow) = beamsOnPreviousRow.zip(currentRow)
            .foldIndexed(Pair(0L, listOf<Char>())) { index, acc, pair ->
                val splitsOnCurrentRow = acc.first
                val currentRowSoFar = acc.second
                val previousRowChar = pair.first
                val currentRowChar = pair.second

                if (currentRowChar == '^' && previousRowChar == '|') Pair(splitsOnCurrentRow + 1, currentRowSoFar.dropLast(1) + '|' + '^' + '|')
                else if (index + 1 == currentRowSoFar.size) Pair(splitsOnCurrentRow, currentRowSoFar)
                else if (previousRowChar == '^') Pair(splitsOnCurrentRow, currentRowSoFar + '.')
                else Pair(splitsOnCurrentRow, currentRowSoFar + previousRowChar)
            }
        calculateBeamSplits(row + 1, grid, beamsOnCurrentRow, beamSplits + splitsOnRow)
    }
}

tailrec fun calculateTimelines(row: Int, grid: List<List<Char>>, beamsOnPreviousRow: List<Long>): Long {
    return if (row >= grid.size) beamsOnPreviousRow.sum()
    else {
        val currentRow = grid[row]
        val beamsOnCurrentRow = beamsOnPreviousRow.zip(currentRow)
            .foldIndexed(listOf<Long>()) { index, currentRowSoFar, pair ->
                val previousRowBeams = pair.first
                val currentRowChar = pair.second

                if (currentRowChar == '^' && previousRowBeams > 0) currentRowSoFar.dropLast(1) + (currentRowSoFar.last() + previousRowBeams) + 0 + previousRowBeams
                else if (index + 1 == currentRowSoFar.size) currentRowSoFar.dropLast(1) + (currentRowSoFar.last() + previousRowBeams)
                else currentRowSoFar + previousRowBeams
            }
        calculateTimelines(row + 1, grid, beamsOnCurrentRow)
    }
}
