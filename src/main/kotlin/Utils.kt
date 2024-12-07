import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInput(year: Int, day: Int, task: Int, test: Boolean = false): String =
    Path("src/${if (test) "test" else "main"}/resources/year${year}/day${day}/task${task}.txt").readText()

fun String.split(separators: List<Regex>): List<String> = separators.fold(listOf(this)) { strings, separator ->
    strings.flatMap { it.split(separator) }
}

typealias Point = Pair<Int, Int>

operator fun Point.plus(other: Point): Point {
    return Point(this.first + other.first, this.second + other.second)
}

enum class Directions(val move: Point) {
    NORTH(Point(-1, 0)),
    SOUTH(Point(1, 0)),
    EAST(Point(0, 1)),
    WEST(Point(0, -1));

    fun turn90Clockwise(): Directions {
        return when (this) {
            NORTH -> EAST
            SOUTH -> WEST
            EAST -> SOUTH
            WEST -> NORTH
        }
    }
}

fun Any?.println() = println(this)