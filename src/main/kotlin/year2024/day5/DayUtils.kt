package year2024.day5

import println

fun solveTask1(input: String): String {
    val (rules, updates) = input.split(Regex("\\r?\\n\\r?\\n")).map(String::lines)
    val ruleMap = rules.fold(mapOf<String, List<String>>()) { map, rule ->
        val (e1, e2) = rule.split('|')
        map.plus(e1 to map.getOrDefault(e1, listOf()).plus(e2))
    }

    val isOrdered = Comparator<String> { a, b -> if (ruleMap[a]?.contains(b) == true) -1 else if (ruleMap[b]?.contains(a) == true) 1 else 0 }

    return updates.map { update -> update.split(',') }.sumOf { update ->
        update.sortedWith(isOrdered).let {
            if (it == update) update[update.size / 2].toInt() else 0
        }
    }.toString()
}

fun solveTask2(input: String): String {
    val (rules, updates) = input.split(Regex("\\r?\\n\\r?\\n")).map(String::lines)
    val ruleMap = rules.fold(mapOf<String, List<String>>()) { map, rule ->
        val (e1, e2) = rule.split('|')
        map.plus(e1 to map.getOrDefault(e1, listOf()).plus(e2))
    }

    val isOrdered = Comparator<String> { a, b -> if (ruleMap[a]?.contains(b) == true) -1 else if (ruleMap[b]?.contains(a) == true) 1 else 0 }

    return updates.map { update -> update.split(',') }.sumOf { update ->
        update.sortedWith(isOrdered).let {
            if (it != update) it[update.size / 2].toInt() else 0
        }
    }.toString()
}