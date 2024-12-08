package adventofcode.jul2022

import java.io.File

fun main() {
    val input = File("src/Input.txt").readLines().map { line -> line.toCharArray().map { it } }

    findDistances(input, findS(input))[findE(input)]!!.also { println("Part one: $it") }
    input.indices
        .flatMap { y -> input[0].indices.map { x -> Pair(y, x) } }
        .filter { input[it.first][it.second] == 'a' }
        .map { findDistances(input, Pair(it.first, it.second)) }
        .filter { it.containsKey(findE(input)) }
        .minOf { it[findE(input)]!! }
        .also { println("Part two: $it") }
}

fun findS(input: List<List<Char>>): Pair<Int, Int> {
    input.indices.forEach { y ->
        input[y].indices.forEach { x ->
            if (input[y][x] == 'S') {
                return Pair(y, x)
            }
        }
    }
    throw Exception("No S found")
}

fun findE(input: List<List<Char>>): Pair<Int, Int> {
    input.indices.forEach { y ->
        input[y].indices.forEach { x ->
            if (input[y][x] == 'E') {
                return Pair(y, x)
            }
        }
    }
    throw Exception("No E found")
}

fun findDistances(
    input: List<List<Char>>,
    start: Pair<Int, Int>,
): MutableMap<Pair<Int, Int>, Int> {
    val distances = mutableMapOf<Pair<Int, Int>, Int>()
    distances[start] = 0
    val queue = mutableListOf<Pair<Int, Int>>()
    queue.add(start)
    while (queue.isNotEmpty()) {
        val pos = queue.removeAt(0)
        val distance = distances[pos]!!
        for (nPos in listOf(
            Pair(pos.first + 1, pos.second),
            Pair(pos.first - 1, pos.second),
            Pair(pos.first, pos.second + 1),
            Pair(
                pos.first,
                pos.second - 1,
            ),
        )) {
            if (nPos.first >= 0 && nPos.first <= input.lastIndex && nPos.second >= 0 && nPos.second <= input[0].lastIndex) {
                if (distances[nPos] == null || distances[nPos]!! > distance + 1) {
                    if (input[nPos.first][nPos.second] == 'E' && input[pos.first][pos.second] != 'z') {
                        continue
                    }
                    if (input[pos.first][pos.second] == 'S' ||
                        input[nPos.first][nPos.second].code <= input[pos.first][pos.second].code + 1
                    ) {
                        distances[nPos] = distance + 1
                        queue.add(nPos)
                    }
                }
            }
        }
    }
    return distances
}
