package year2024.day9

fun solveTask1(input: String): String {
    val expandedBlocks = input.map { it.digitToInt() }.fold(Pair(0, listOf<String>())) { (id, expandedList), element ->
        if (id % 2 == 0) Pair(id + 1, expandedList.plus(generateSequence { (id / 2).toString() }.take(element)))
        else Pair(id + 1, expandedList.plus(generateSequence { "." }.take(element)))
    }.second
    return expandedBlocks.foldIndexed(Pair(0L, expandedBlocks.indexOfLast { it != "." })) { index, (checksum, lastUnmovedElement), element ->
        if (index > lastUnmovedElement) Pair(checksum, lastUnmovedElement)
        else if (element == ".") Pair(checksum + expandedBlocks[lastUnmovedElement].toLong() * index, expandedBlocks.subList(index, lastUnmovedElement).indexOfLast { it != "." } + index)
        else Pair(checksum + element.toLong() * index, lastUnmovedElement)
    }.first.toString()
}

fun solveTask2(input: String): String {
    val expandedBlocks = input.map { it.digitToInt() }.fold(Triple(0, 0L, mapOf<Long, Pair<Long, Long>>())) { (id, positions, blocks), element ->
        if (id % 2 == 0) Triple(id + 1, positions + element.toLong(), blocks.plus((id / 2).toLong() to Pair(positions, positions + element.toLong() - 1L)))
        else Triple(id + 1, positions + element.toLong(), blocks)
    }.third
    val lastKey = expandedBlocks.maxBy { it.key }.key
    val compactedMap = moveKeys(lastKey, expandedBlocks)
    return compactedMap.entries.fold(0L) { checksum, (key, value) -> checksum + key * sumBetween(value.first, value.second) }.toString()
}

tailrec fun moveKeys(largestKey: Long, blocks: Map<Long, Pair<Long, Long>>): Map<Long, Pair<Long, Long>> {
    return if (largestKey == 0L) blocks
    else {
        val position = blocks.values.filter { it.first <= blocks[largestKey]!!.first }
            .sortedBy { it.second }.zipWithNext()
            .map { (e1, e2) -> Pair(e1.second + 1, e2.first - e1.second - 1) }
            .firstOrNull { it.second >= blocks[largestKey]!!.second - blocks[largestKey]!!.first + 1 }?.first
        return if (position == null) moveKeys(largestKey - 1, blocks)
        else moveKeys(largestKey - 1, blocks.plus(largestKey to Pair(position, position + blocks[largestKey]!!.second - blocks[largestKey]!!.first)))
    }
}

fun sumBetween(e1: Long, e2: Long): Long = (e1 + e2) * (e2 - e1 + 1L) / 2L