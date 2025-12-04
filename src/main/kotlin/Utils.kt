import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs

fun readInput(year: Int, day: Int, task: Int, test: Boolean = false): String =
    Path("src/${if (test) "test" else "main"}/resources/year${year}/day${day}/task${task}.txt").readText()

fun String.split(separators: List<Regex>): List<String> = separators.fold(listOf(this)) { strings, separator ->
    strings.flatMap { it.split(separator) }
}

fun <T> List<T>.toPair() = Pair(this[0], this[1])

fun String.toLongOrZero() = try { this.toLong() } catch (e: NumberFormatException) { 0 }

typealias Point = Pair<Int, Int>

operator fun Point.plus(other: Point): Point = Point(this.first + other.first, this.second + other.second)

operator fun Point.minus(other: Point): Point = Point(this.first - other.first, this.second - other.second)

fun distance(p1: Point, p2: Point): Int = abs(p1.first - p2.first) + abs(p1.second - p2.second)

fun <T> List<T>.indicesOf(element: T): List<Int> = this.mapIndexedNotNull { index, t -> index.takeIf { t == element } }

enum class Directions4(val move: Point) {
    NORTH(Point(-1, 0)),
    SOUTH(Point(1, 0)),
    EAST(Point(0, 1)),
    WEST(Point(0, -1));

    fun turn90Clockwise(): Directions4 = when (this) {
        NORTH -> EAST
        SOUTH -> WEST
        EAST -> SOUTH
        WEST -> NORTH
    }

    fun turn90Counter(): Directions4 = when (this) {
        NORTH -> WEST
        SOUTH -> EAST
        EAST -> NORTH
        WEST -> SOUTH
    }
}

enum class Directions8(val move: Point) {
    NORTH(Point(-1, 0)),
    NORTHEAST(Point(-1, 1)),
    EAST(Point(0, 1)),
    SOUTHEAST(Point(1, 1)),
    SOUTH(Point(1, 0)),
    SOUTHWEST(Point(1, -1)),
    WEST(Point(0, -1)),
    NORTHWEST(Point(-1, -1));
}

fun Any?.println() = println(this)