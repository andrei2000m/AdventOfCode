import org.junit.jupiter.api.Test

class UtilsTest {

    @Test
    fun canSplitStringOnRegex() {
        val expected = listOf("test1", "test2")
        val actual = "test1],[test2".split(listOf("],\\[".toRegex()))

        expected isEqualTo actual
    }

    @Test
    fun canSplitStringOnMoreThanOneRegex() {
        val expected = listOf("test1", "test2", "test3", "test4")
        val actual = "test1],[test2.test3dtest4".split(listOf("],\\[".toRegex(), "\\.".toRegex(), "d".toRegex()))

        expected isEqualTo actual
    }
}