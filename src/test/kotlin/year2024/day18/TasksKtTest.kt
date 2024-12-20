package year2024.day18

import readExpected
import readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class TasksKtTest {

    @Test
    fun canSolveTask1() {
        val input = readInput(year = 2024, day = 18, task = 1, test = true)
        val expected = readExpected(year = 2024, day = 18, task = 1)

        val actual = solveTask1(input, 12, 6)

        assertEquals(actual, expected)
    }

    @Test
    fun canSolveTask2() {
        val input = readInput(year = 2024, day = 18, task = 2, test = true)
        val expected = readExpected(year = 2024, day = 18, task = 2)

        val actual = solveTask2(input, 12, 6)

        assertEquals(actual, expected)
    }
}