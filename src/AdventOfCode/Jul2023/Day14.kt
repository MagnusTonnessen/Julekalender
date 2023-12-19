package AdventOfCode.Jul2023

import java.io.File

fun main() {
    File("src/Input")
        .readLines()
        .map { it.toList() }
        .run {
            println("Part one: ${tilt('N').totalLoad()}")
            println("Part two: ${cycle(1000000).totalLoad()}")
        }
}

private fun List<List<Char>>.totalLoad(): Int =
    reversed().mapIndexed { i, row -> (i + 1) * row.count { it == 'O' } }.sum()

private fun List<List<Char>>.cycle(n: Int): List<List<Char>> {
    var count = 0
    var tilted = this
    do {
        val (changed, next) = Pair(false, tilted).tilt('N').tilt('W').tilt('S').tilt('E')
        tilted = next
    } while (changed && ++count < n)
    return tilted
}

private fun List<List<Char>>.tilt(dir: Char): List<List<Char>> =
    Pair(false, this).tilt(dir).second

private fun Pair<Boolean, List<List<Char>>>.tilt(dir: Char): Pair<Boolean, List<List<Char>>> {
    var changed = first
    val tilted = second.toMutableList().map { it.toMutableList() }
    second.indices.forEach { y ->
        second.indices.forEach { x ->
            val char = second.get(y)[x]
            if (char == 'O') {
                when (dir) {
                    'N' -> {
                        for (i in y downTo 1) {
                            if (tilted[i - 1][x] == '.') {
                                tilted[i - 1][x] = 'O'
                                tilted[i][x] = '.'
                                changed = true
                            } else {
                                break
                            }
                        }
                    }
                    'W' -> {
                        for (i in x downTo 1) {
                            if (tilted[y][i - 1] == '.') {
                                tilted[y][i - 1] = 'O'
                                tilted[y][i] = '.'
                                changed = true
                            } else {
                                break
                            }
                        }
                    }
                    'S' -> {
                        for (i in y until second.lastIndex) {
                            if (tilted[i + 1][x] == '.') {
                                tilted[i + 1][x] = 'O'
                                tilted[i][x] = '.'
                                changed = true
                            } else {
                                break
                            }
                        }
                    }
                    'E' -> {
                        for (i in x until second.lastIndex) {
                            if (tilted[y][i + 1] == '.') {
                                tilted[y][i + 1] = 'O'
                                tilted[y][i] = '.'
                                changed = true
                            } else {
                                break
                            }
                        }
                    }
                }
            }
        }
    }
    return Pair(changed, tilted)
}
