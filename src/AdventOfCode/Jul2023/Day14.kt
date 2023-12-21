package AdventOfCode.Jul2023

import java.io.File

fun main() {
    File("src/Input")
        .readLines()
        .map { it.toList() }
        .run {
            println("Part one: ${tilt('N').totalLoad()}")
            println("Part two: ${cycle(1000000000)}")
        }
}

private fun List<List<Char>>.totalLoad(): Int =
    reversed().mapIndexed { i, row -> (i + 1) * row.count { it == 'O' } }.sum()

private fun List<List<Char>>.cycle(n: Int): Int {
    var tilted = this
    val loads = mutableListOf(totalLoad())
    repeat(n) {
        tilted = tilted.tilt('N').tilt('W').tilt('S').tilt('E')
        loads.apply { add(tilted.totalLoad()) }.findPattern()?.let { (startIndex, pattern) ->
            return loads[startIndex + (n - startIndex) % pattern.size]
        }
    }
    return tilted.totalLoad()
}

fun List<Int>.findPattern(): Pair<Int, List<Int>>? {
    for (startIndex in 0 until size / 2) {
        for (patternSize in 1 until (size - startIndex) / 2) {
            val pattern = subList(startIndex, startIndex + patternSize)
            if (drop(startIndex).windowed(patternSize, patternSize).all { it == pattern }) {
                return Pair(startIndex, pattern)
            }
        }
    }
    return null
}

private fun List<List<Char>>.tilt(dir: Char): List<List<Char>> {
    val tilted = toMutableList().map { it.toMutableList() }
    val yIndices = if (dir == 'N') indices else indices.reversed()
    val xIndices = if (dir == 'W') indices else indices.reversed()
    yIndices.forEach { y ->
        xIndices.forEach { x ->
            if (tilted[y][x] == 'O') {
                val range = when (dir) {
                    'N' -> y downTo  1
                    'W' -> x downTo  1
                    'S' -> y until lastIndex
                    else -> x until lastIndex
                }
                for (i in range) {
                    val currY = if (dir in listOf('N', 'S')) i else y
                    val currX = if (dir in listOf('W', 'E')) i else x
                    val nextY = if (dir == 'N') i - 1 else if (dir == 'S') i + 1 else y
                    val nextX = if (dir == 'W') i - 1 else if (dir == 'E') i + 1 else x
                    if (tilted[nextY][nextX] == '.') {
                        tilted[nextY][nextX] = 'O'
                        tilted[currY][currX] = '.'
                    } else {
                        break
                    }
                }
            }
        }
    }
    return tilted
}
