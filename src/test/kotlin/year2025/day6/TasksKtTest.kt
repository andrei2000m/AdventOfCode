package year2025.day6

import readExpected
import readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class TasksKtTest {

    @Test
    fun canSolveTask1() {
        val input = readInput(year = 2025, day = 6, task = 1, test = true)
        val expected = readExpected(year = 2025, day = 6, task = 1)

        val actual = solveTask1(input)

        assertEquals(expected, actual)
    }

    @Test
    fun canSolveTask2() {
        val input = readInput(year = 2025, day = 6, task = 2, test = true)
        val expected = readExpected(year = 2025, day = 6, task = 2)

        val actual = solveTask2(input)

        assertEquals(expected, actual)
    }
}