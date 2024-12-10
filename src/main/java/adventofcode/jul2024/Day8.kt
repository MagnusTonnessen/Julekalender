package adventofcode.jul2024

import adventofcode.Grid
import adventofcode.Grid.Companion.toGrid
import adventofcode.Position
import adventofcode.combinations
import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt")
        .readLines()
        .toGrid()
        .run {
            positions
                .filter { get(it) != '.' }
                .groupBy { get(it) }
                .run {
                    flatMap { positions ->
                        positions.value
                            .combinations(2)
                            .flatMap { (a, b) -> listOf(a.opposite(b), b.opposite(a)) }
                            .filter { isOnGrid(it) }
                    }.toSet().size.printPart1()

                    flatMap { positions ->
                        positions.value
                            .combinations(2)
                            .flatMap { (a, b) -> getAntinodes(a, b) }
                    }.toSet().size.printPart2()
                }
        }
}

private fun Grid.getAntinodes(
    pos1: Position,
    pos2: Position,
): Set<Position> {
    val positions = mutableSetOf(pos1, pos2)
    val diff = pos1.minus(pos2)
    var currPos = pos1
    while (isOnGrid(currPos)) {
        positions.add(currPos)
        currPos = currPos.plus(diff)
    }
    currPos = pos2
    while (isOnGrid(currPos)) {
        positions.add(currPos)
        currPos = currPos.minus(diff)
    }
    return positions
}
