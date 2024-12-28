package year2024.day22

fun solveTask1(input: String): String = input.lines().map { it.toLong() }.sumOf { secretNumber -> (1..2000).fold(secretNumber) { it, _ -> nextStep(it) } }.toString()

fun solveTask2(input: String): String {
    return input.lines().map { it.toLong() }.flatMap { secretNumber -> (1..2000).fold(Pair(secretNumber, listOf(secretNumber % 10))) { (number, numberList), _ ->
        val nextNumber = nextStep(number)
        Pair(nextNumber, numberList + (nextNumber % 10))
    }.second.windowed(5).map { window -> Pair(window.zipWithNext().map { it.second - it.first }, window.last()) }
        .groupBy(keySelector = { it.first }, valueTransform = { it.second }).mapValues { it.value.first() }.entries.map { it.toPair() }
    }.groupingBy { it.first }.aggregate { _, accumulator: Long?, element, first ->
        if (first) element.second else accumulator!! + element.second
    }.maxBy { it.value }.value.toString()
}

fun nextStep(secretNumber: Long): Long {
    val step1 = (secretNumber * 64).mix(secretNumber).prune()
    val step2 = (step1 / 32).mix(step1).prune()
    val step3 = (step2 * 2048).mix(step2).prune()
    return step3
}

fun Long.mix(secretNumber: Long): Long = this.xor(secretNumber)

fun Long.prune(): Long = this % 16777216