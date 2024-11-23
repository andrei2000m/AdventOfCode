import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun readExpected(year: Int, day: Int, task: Int): String =
        Path("src/test/resources/year${year}/day${day}/expected${task}.txt").readText()

infix fun <T> T.isEqualTo(other: T) = assertEquals(this, other)

infix fun <T> T.isNotEqualTo(other: T) = assertNotEquals(this, other)