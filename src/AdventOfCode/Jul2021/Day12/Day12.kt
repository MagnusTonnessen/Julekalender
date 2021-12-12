package AdventOfCode.Jul2021.Day12

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day12/Input").readLines()
    val edges = mutableMapOf<String, MutableList<String>>()
    for (edge in input) {
        val a = edge.split("-")[0]
        val b = edge.split("-")[1]
        edges.putIfAbsent(a, mutableListOf())
        edges.putIfAbsent(b, mutableListOf())
        edges[a]!! += b
        edges[b]!! += a
    }
    val visited = edges.keys.associateWith { 0 }.toMutableMap()
    val waysPart1 = dfs("start", edges, visited, partTwo = false)
    val waysPart2 = dfs("start", edges, visited, partTwo = true)
    println("Part 1: $waysPart1")
    println("Part 2: $waysPart2")
}

fun dfs(
    currNode: String,
    edges: Map<String, List<String>>,
    visited: MutableMap<String, Int>,
    visitedTwice: Boolean = false,
    partTwo: Boolean
): Int {
    var ways = 0
    visited[currNode] = visited[currNode]!!.inc()
    for (edge in edges[currNode]!!) {
        if (edge == "start") {
            continue
        } else if (edge == "end") {
            ways++
        } else if (edge[0].isLowerCase() && visited[edge]!! < 1) {
            ways += dfs(edge, edges, visited, visitedTwice, partTwo)
        } else if (partTwo && edge[0].isLowerCase() && visited[edge]!! < 2 && !visitedTwice) {
            ways += dfs(edge, edges, visited, true, partTwo)
        } else if (edge[0].isUpperCase() && visited[edge]!! < 100) {
            ways += dfs(edge, edges, visited, visitedTwice, partTwo)
        }
    }
    visited[currNode] = visited[currNode]!!.dec()
    return ways
}