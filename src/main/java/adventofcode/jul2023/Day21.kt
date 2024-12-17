package adventofcode.jul2023

import adventofcode.Direction
import adventofcode.Grid.Companion.toGrid
import adventofcode.Position
import adventofcode.printPart1
import java.io.File

fun main() {
    val grid = File("src/Input.txt").readLines().toGrid()
    val pos = grid.getFirstPositionOfChar('S')

    val currentLayer = mutableSetOf(pos)
    val previousLayer = mutableSetOf<Position>()
    var oddPositions = 0L
    var evenPositions = 1L

    val n = 6
    repeat(n) { i ->
        currentLayer
            .flatMap { pos ->
                Direction.fourDirections.mapNotNull { dir ->
                    val newPos = pos.move(dir)
                    if (grid.get(newPos) != '#' && !previousLayer.contains(newPos)) {
                        newPos
                    } else {
                        null
                    }
                }
            }.also {
                previousLayer.clear()
                previousLayer.addAll(currentLayer)

                println("$i: ${currentLayer.map { it.toString() }}")

                currentLayer.clear()
                currentLayer.addAll(it)
            }

        if (i % 2 == 0) {
            oddPositions += currentLayer.size
        } else {
            evenPositions += currentLayer.size
        }
        if (i == 63) {
            println("Part one: $evenPositions")
        }
    }

    oddPositions.printPart1()
}
