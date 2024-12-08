package adventofcode.jul2023

import adventofcode.Direction
import adventofcode.Direction.Companion.toDir
import adventofcode.Grid
import adventofcode.Grid.Companion.toGrid
import adventofcode.Position
import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File
import java.util.LinkedList

fun main() {
    val grid = File("src/Input.txt").readLines().toGrid()
    val start = grid.getFirstPositionOfChar('.')
    val end = grid.getLastPositionOfChar('.')
    grid.longestPath(start, end, partTwo = false).printPart1()
    grid.longestPathDP(start, end).printPart2()
}

private fun Grid.longestPath(
    start: Position,
    end: Position,
    partTwo: Boolean,
): Int {
    val paths = mutableListOf<List<Position>>()
    val queue = LinkedList(listOf(listOf(start) to Direction.fourDirections))
    while (queue.isNotEmpty()) {
        val (path, validDirections) = queue.removeFirst()
        val pos = path.last()
        for (dir in validDirections) {
            val newPos = pos.move(dir)
            if (newPos == end) {
                paths.add(path.toList() + newPos)
            } else if (isOnGrid(newPos) && get(newPos) != '#' && !path.contains(newPos)) {
                val char = get(newPos)
                val newPath = path.toList() + newPos
                val newValidDirections =
                    if (char == '.' || partTwo) {
                        Direction.fourDirections
                    } else {
                        listOf(char.toDir())
                    }
                queue.add(newPath to newValidDirections)
            }
        }
    }

    return paths.maxOf { it.size - 1 }
}

private fun Grid.longestPathDP(
    start: Position,
    end: Position,
): Int {
    val visited = Array(height) { BooleanArray(width) }

    fun dfs(
        pos: Position,
        pathLength: Int,
    ): Int {
        if (pos == end) {
            return pathLength
        }

        visited[pos.y][pos.x] = true
        var maxLength = 0

        for (dir in Direction.fourDirections) {
            val newPos = pos.move(dir)
            if (isOnGrid(newPos) && get(newPos) != '#' && !visited[pos.y][pos.x]) {
                maxLength = maxOf(maxLength, dfs(newPos, pathLength + 1))
            }
        }

        visited[pos.y][pos.x] = false
        return maxLength
    }

    return dfs(start, 0)
}
