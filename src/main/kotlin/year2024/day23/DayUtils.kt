package year2024.day23

fun solveTask1(input: String): String {
    val connections = input.lines().fold(mapOf<String, List<String>>()) { map, line ->
        val (first, second) = line.split('-')
        val firstList = map[first] ?: listOf()
        val secondList = map[second] ?: listOf()
        map + (first to (firstList + second)) + (second to (secondList + first))
    }
    return connections.entries.sumOf { (key, list) ->
        list.sumOf { connection ->
            connections[connection]?.filter { connections[it]?.contains(key) ?: false }
                ?.filter { key.startsWith('t') || connection.startsWith('t') || it.startsWith('t') }?.size ?: 0
        }
    }.div(6).toString()
}

fun solveTask2(input: String): String {
    return ""
}

//val bronKerbosch = DeepRecursiveFunction<Pair<Map<String, List<String>>, Triple<List<String>, List<String>, List<String>>>, List<String>>
//{ (connections, disjointSets) ->
//    if (disjointSets.second.isEmpty() && disjointSets.third.isEmpty()) disjointSets.first
//    else {
//        disjointSets.second
//    }
//}