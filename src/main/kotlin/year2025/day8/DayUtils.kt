package year2025.day8

import distance3D
import split
import toTriple

fun solveTask1(input: String, pairs: Int): String {
    val points = input.split(listOf(Regex("\r\n"))).map { it.split(listOf(Regex(","))).map { s -> s.toLong() }.toTriple() }.withIndex()
    val shortestConnections = points.flatMap { value -> points.filter { it.index > value.index }.map { i -> Pair(value, i) } }.map { (val1, val2) ->
        val i1 = val1.index
        val i2 = val2.index
        val dist = distance3D(val1.value, val2.value)
        Triple(i1, i2, dist)
    }.sortedBy { it.third }.take(pairs)
    val initialSets = points.map { setOf(it.index) }
    return shortestConnections.asSequence().fold(initialSets) { sets, connection ->
        val v1 = connection.first
        val v2 = connection.second
        val set1 = sets.first { it.contains(v1) }
        val set2 = sets.first { it.contains(v2) }
        sets.minusElement(set1).minusElement(set2).plusElement(set1.plus(set2))
    }.map { it.size }.sortedDescending().take(3).fold(1) { acc, i -> acc * i }.toString()
}

fun solveTask2(input: String): String {
    val points = input.split(listOf(Regex("\r\n"))).map { it.split(listOf(Regex(","))).map { s -> s.toLong() }.toTriple() }.withIndex()
    val shortestConnections = points.flatMap { value -> points.filter { it.index > value.index }.map { i -> Pair(value, i) } }.map { (val1, val2) ->
        val i1 = val1.index
        val i2 = val2.index
        val dist = distance3D(val1.value, val2.value)
        Triple(i1, i2, dist)
    }.sortedBy { it.third }
    val initialSets = points.map { setOf(it.index) }
    val (i1, i2, _) = shortestConnections.asSequence().fold(Triple(0, 0, initialSets)) { (i1, i2, sets), connection ->
        val v1 = connection.first
        val v2 = connection.second
        val set1 = sets.first { it.contains(v1) }
        val set2 = sets.first { it.contains(v2) }
        val newSets = sets.minusElement(set1).minusElement(set2).plusElement(set1.plus(set2))
        if (sets.size > 1 && newSets.size == 1) Triple(v1, v2, newSets) else Triple(i1, i2, newSets)
    }
    return (points.find { it.index == i1 }!!.value.first * points.find { it.index == i2 }!!.value.first).toString()
}
