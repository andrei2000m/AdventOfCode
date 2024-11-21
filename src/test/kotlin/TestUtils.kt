import kotlin.io.path.Path
import kotlin.io.path.readText

fun readExpected(year: Int, day: Int, task: Int) =
        Path("src/test/resources/year${year}/day${day}/expected${task}.txt").readText()