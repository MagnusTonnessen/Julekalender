package adventofcode.jul2024

import adventofcode.Direction.Companion.fourDirections
import adventofcode.Direction.E
import adventofcode.Direction.S
import adventofcode.Grid.Companion.toGrid
import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt").readLines().toGrid().run {
        val distanceFromStart = dijkstra(start, E)
        val shortestDistance = distanceFromStart[end]!!.minOf { it.value }.also { it.printPart1() }
        val distancesFromEnd = dijkstra(end, S)

        positions
            .count { pos ->
                fourDirections.any { dir ->
                    distanceFromStart[pos]!![dir]!! + distancesFromEnd[pos]!![dir.turn180()]!! == shortestDistance
                }
            }.printPart2()
    }
}
