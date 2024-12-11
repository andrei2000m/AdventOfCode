package year2024.day11

import readExpected
import readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class TasksKtTest {

    @Test
    fun canSolveTask1() {
        val input = readInput(year = 2024, day = 11, task = 1, test = true)
        val expected = readExpected(year = 2024, day = 11, task = 1)

        val actual = solveTask1(input)

        assertEquals(actual, expected)
    }
}