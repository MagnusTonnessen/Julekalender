package adventofcode.jul2024

import adventofcode.Direction
import adventofcode.Grid
import adventofcode.Grid.Companion.toGrid
import adventofcode.Position
import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt").readLines().toGrid().run {
        positions.filter { get(it) == '0' }.run {
            sumOf { findTops(it).distinctBy { it.last() }.size }.printPart1()
            sumOf { findTops(it).size }.printPart2()
        }
    }
}

private fun Grid.findTops(start: Position): Set<List<Position>> {
    val tops = mutableSetOf<List<Position>>()
    val queue = mutableListOf(listOf(start))

    while (queue.isNotEmpty()) {
        val path = queue.removeFirst()
        val current = path.last()
        Direction.fourDirections.forEach { dir ->
            val next = current.move(dir)
            if (isOnGrid(next) && getInt(next) == getInt(current) + 1) {
                if (getInt(next) == 9) {
                    tops.add(path + next)
                } else {
                    queue.add(path + next)
                }
            }
        }
    }

    return tops
}
