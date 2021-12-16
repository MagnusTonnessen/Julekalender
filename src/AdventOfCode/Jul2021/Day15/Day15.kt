package AdventOfCode.Jul2021.Day15

import java.awt.Point
import java.io.File

fun main() {
    val inputPartOne = File("src/AdventOfCode/Jul2021/Day15/Input").readLines().map { it.map { c -> c.digitToInt() } }
    val distPartOne = dijkstra(inputPartOne, Point(0, 0))
    println("Part one: ${distPartOne.last()}")

    val inputPartTwo = mutableListOf<List<Int>>()
    for (y in 0..4) {
        for (r in inputPartOne.indices) {
            val row = mutableListOf<List<Int>>()
            for (x in 0..4) {
                row.add(inputPartOne[r].map { if (it + y + x > 9) (it + y + x).mod(10) + 1 else (it + y + x) })
            }
            inputPartTwo.add(row.flatten())
        }
    }
    val distPartTwo = dijkstra(inputPartTwo, Point(0, 0))
    println("Part two: ${distPartTwo.last()}")
}

fun dijkstra(input: List<List<Int>>, src: Point): LongArray {
    val unvisited = input.indices.flatMap { y -> input[0].indices.map { x -> Point(x, y) } }.toMutableSet()
    val dist = LongArray(input.size * input[0].size) { Long.MAX_VALUE }
    dist[0] = 0
    var currNode = src
    while (!isEnd(currNode, input)) {
        adjacent(currNode, input).filter { it in unvisited }.forEach {
            dist[index(it, input)] = minOf(dist[index(it, input)], dist[index(currNode, input)] + input[it.y][it.x])
        }
        unvisited.remove(currNode)
        currNode = unvisited.minByOrNull { dist[index(it, input)] }!!
    }
    return dist
}

fun index(pos: Point, input: List<List<Int>>): Int {
    return pos.y * input[0].size + pos.x
}

fun isEnd(pos: Point, input: List<List<Int>>): Boolean {
    return pos.y == input[0].lastIndex && pos.x == input.lastIndex
}

fun valid(pos: Point, input: List<List<Int>>): Boolean {
    return pos.x >= 0 && pos.y >= 0 && pos.x < input[0].size && pos.y < input.size
}

fun adjacent(pos: Point, input: List<List<Int>>): List<Point> {
    return listOf(
        Point(pos.x - 1, pos.y),
        Point(pos.x + 1, pos.y),
        Point(pos.x, pos.y - 1),
        Point(pos.x, pos.y + 1)
    ).filter { valid(it, input) }
}