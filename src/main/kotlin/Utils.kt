import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInput(year: Int, day: Int, task: Int, test: Boolean = false): String =
    Path("src/${if (test) "test" else "main"}/resources/year${year}/day${day}/task${task}.txt").readText()

fun String.split(separators: List<Regex>): List<String> = separators.fold(listOf(this)) { strings, separator ->
    strings.flatMap { it.split(separator) }
}

fun Any?.println() = println(this)