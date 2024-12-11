package year2024.day11

val mem = HashMap<Pair<Long, Int>, Long>()

fun solveTask1(input: String): String {
    val initialRocks = input.split(" ").map { rock -> rock.toLong() }
    return initialRocks.sumOf { rock -> size(rock, 25) }.toString()
}

fun solveTask2(input: String): String {
    val initialRocks = input.split(" ").map { rock -> rock.toLong() }
    return initialRocks.map { rock -> {
        size(rock, 75)
    } }.sumOf{ it() }.toString()
}

fun size(rock: Long, repeats: Int): Long {
    if (mem.containsKey(Pair(rock, repeats))) return mem[Pair(rock, repeats)]!!
    val s = rock.toString()
    return if (repeats == 0) 1L
    else when {
        rock == 0L -> {
            val result = size(1, repeats - 1)
            mem[Pair(1, repeats - 1)] = result
            result
        }
        s.length % 2 == 0 -> {
            val result1 = size(s.substring(0, s.length / 2).toLong(), repeats - 1)
            mem[Pair(s.substring(0, s.length / 2).toLong(), repeats - 1)] = result1
            val result2 = size(s.substring(s.length / 2, s.length).toLong(), repeats - 1)
            mem[Pair(s.substring(s.length / 2, s.length).toLong(), repeats - 1)] = result2
            result1 + result2
        }
        else -> {
            val result = size(rock * 2024, repeats - 1)
            mem[Pair(rock * 2024, repeats - 1)] = result
            result
        }
    }
}