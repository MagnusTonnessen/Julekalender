package adventofcode.jul2024

import adventofcode.Direction
import adventofcode.Direction.Companion.toDir
import adventofcode.Direction.E
import adventofcode.Direction.N
import adventofcode.Direction.S
import adventofcode.Direction.W
import adventofcode.Grid
import adventofcode.Grid.Companion.toGrid
import adventofcode.Position
import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt").readLines().run {
        val instructions = takeLastWhile { it.isNotEmpty() }.joinToString("")

        val grid1 = takeWhile { it.isNotEmpty() }.toGrid()
        var position1 = grid1.getFirstPositionOfChar('@')

        val doubleSize =
            mapOf(
                '@' to "@.",
                '#' to "##",
                'O' to "[]",
                '.' to "..",
            )

        val grid2 = takeWhile { it.isNotEmpty() }.map { it.map { doubleSize[it] }.joinToString("") }.toGrid()

        var position2 = grid2.getFirstPositionOfChar('@')

        instructions.forEach {
            position1 = grid1.movePart1(position1, it.toDir())
            position2 = grid2.movePart2(position2, it.toDir())
        }

        grid1.positionsWithChar
            .filter { it.second == 'O' }
            .sumOf { 100 * it.first.y + it.first.x }
            .printPart1()

        grid2.positionsWithChar
            .filter { it.second == '[' }
            .sumOf { 100 * it.first.y + it.first.x }
            .printPart2()
    }
}

private fun Grid.canMove(
    position: Position,
    direction: Direction,
): Boolean {
    val newPosition = position.move(direction)
    return when (get(newPosition)) {
        '[' ->
            when (direction) {
                N, S -> canMove(newPosition, direction) && canMove(newPosition.move(E), direction)
                E -> canMove(newPosition.move(E), direction)
                else -> canMove(newPosition, direction)
            }
        ']' ->
            when (direction) {
                N, S -> canMove(newPosition, direction) && canMove(newPosition.move(W), direction)
                W -> canMove(newPosition.move(W), direction)
                else -> canMove(newPosition, direction)
            }
        '#' -> false
        '.' -> true
        else -> canMove(newPosition, direction)
    }
}

private fun Grid.movePart1(
    position: Position,
    direction: Direction,
): Position {
    if (canMove(position, direction)) {
        val newPosition = position.move(direction)
        if (get(newPosition) == 'O') movePart1(newPosition, direction)
        swap(position, newPosition)
        return newPosition
    }
    return position
}

private fun Grid.movePart2(
    position: Position,
    direction: Direction,
): Position {
    if (canMove(position, direction)) {
        val newPosition = position.move(direction)
        val char = get(newPosition)
        if (char in "[]") {
            if (direction in setOf(N, S)) {
                if (char == '[') movePart2(newPosition.move(E), direction)
                if (char == ']') movePart2(newPosition.move(W), direction)
            }
            movePart2(newPosition, direction)
        }
        swap(position, newPosition)
        return newPosition
    }
    return position
}
