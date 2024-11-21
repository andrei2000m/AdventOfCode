import kotlin.io.path.Path
import kotlin.io.path.readLines

//TODO: Add resources to .gitignore
fun readInput(year: Int, day: Int, task: Int, test: Boolean = false) =
    Path("src/${if (test) "test" else "main"}/resources/year${year}/day${day}/task${task}.txt").readLines()

fun Any?.println() = println(this)