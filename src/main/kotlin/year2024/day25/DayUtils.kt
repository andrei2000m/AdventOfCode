package year2024.day25

fun solveTask1(input: String): String {
    val (locks, keys) = input.split(Regex("\r?\n\r?\n")).fold(Pair(listOf<List<Int>>(), listOf<List<Int>>())) { (locks, keys), schematic ->
        val schematicLines = schematic.lines()
        if (schematic.startsWith('#')) {
            // Lock
            Pair(locks.plusElement((0..<schematicLines[0].length).fold(listOf()) { heights, pin ->
                heights + schematicLines.indices.fold(-1) { height, index ->
                    if (schematicLines[index][pin] == '#') height + 1 else height
                }
            }), keys)
        } else {
            // Key
            Pair(locks, keys.plusElement((0..<schematicLines[0].length).fold(listOf()) { heights, pin ->
                heights + schematicLines.indices.fold(-1) { height, index ->
                    if (schematicLines[index][pin] == '#') height + 1 else height
                }
            }))
        }
    }
    return locks.sumOf { lock ->
        keys.sumOf { key ->
            lock.indices.fold(1L) { fits, index -> if (lock[index] + key[index] <= 5) fits else 0L }
        }
    }.toString()
}

fun solveTask2(input: String): String {
    return ""
}