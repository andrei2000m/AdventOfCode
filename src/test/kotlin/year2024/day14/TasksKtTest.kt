package year2024.day14

import readExpected
import readInput
import kotlin.test.Test
import kotlin.test.assertEquals

class TasksKtTest {

    @Test
    fun canSolveTask1() {
        val input = readInput(year = 2024, day = 14, task = 1, test = true)
        val expected = readExpected(year = 2024, day = 14, task = 1)

        val actual = solveTask1(input, 11, 7)

        assertEquals(actual, expected)
    }
}