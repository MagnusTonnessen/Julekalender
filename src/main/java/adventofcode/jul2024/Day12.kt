package adventofcode.jul2024

import adventofcode.Direction
import adventofcode.Grid
import adventofcode.Grid.Companion.toGrid
import adventofcode.Position
import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt")
        .readLines()
        .toGrid()
        .run {
            positionsWithChar
                .map { it.second to getRegion(it.first) }
                .distinct()
                .map { it.second }
                .run {
                    sumOf { countSides(it) * it.size }.printPart1()
                    sumOf { countStraightSides(it) * it.size }.printPart2()
                }
        }
}

private fun Grid.getRegion(position: Position): Set<Position> {
    val queue = mutableListOf(position)
    val region = mutableSetOf(position)

    while (queue.isNotEmpty()) {
        val current = queue.removeAt(0)
        fourNeighbours(current)
            .filter { isOnGrid(it) }
            .filter { get(it) == get(position) }
            .filter { it !in region }
            .forEach {
                queue.add(it)
                region.add(it)
            }
    }

    return region
}

private fun Grid.countSides(region: Set<Position>): Int =
    region.sumOf {
        fourNeighbours(it).count { it !in region }
    }

private fun countStraightSides(region: Set<Position>): Int =
    Direction.fourDirections.sumOf { dir ->
        region
            .map { it.move(dir) }
            .filter { it !in region }
            .findFences(dir)
            .size
    }

private fun List<Position>.findFences(direction: Direction): Set<Set<Position>> =
    when (direction) {
        Direction.N, Direction.S -> map { it.findFence(this, Direction.E to Direction.W) }.toSet()
        Direction.E, Direction.W -> map { it.findFence(this, Direction.N to Direction.S) }.toSet()
        else -> error("Invalid direction")
    }

private fun Position.findFence(
    fencePositions: List<Position>,
    directions: Pair<Direction, Direction>,
): Set<Position> {
    val line = mutableSetOf(this)
    val queue = mutableListOf(this)
    while (queue.isNotEmpty()) {
        val current = queue.removeAt(0)
        val pos1 = current.move(directions.first)
        val pos2 = current.move(directions.second)
        if (pos1 in fencePositions && pos1 !in line) {
            line.add(pos1)
            queue.add(pos1)
        }
        if (pos2 in fencePositions && pos2 !in line) {
            line.add(pos2)
            queue.add(pos2)
        }
    }
    return line
}
