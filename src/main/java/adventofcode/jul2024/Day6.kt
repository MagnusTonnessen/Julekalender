package adventofcode.jul2024

import adventofcode.Direction
import adventofcode.Direction.N
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
            runSimulation()
                .first
                .distinctBy { it.first to it.second }
                .size
                .printPart1()

            yIndices
                .sumOf { y ->
                    xIndices.count { x ->
                        get(y, x) !in "#^" && copyAndReplace(y, x, '#').runSimulation().second
                    }
                }.printPart2()

            sumRows { y ->
                countCol { x ->
                    get(y, x) !in "#^" && copyAndReplace(y, x, '#').runSimulation().second
                }
            }.printPart2()
        }
}

private fun Grid.runSimulation(): Pair<Set<Pair<Position, Direction>>, Boolean> {
    val pos = getFirstPositionOfChar('^')
    var dir = N
    val visited = mutableSetOf(pos to dir)
    while (isOnGrid(pos)) {
        val nPos = pos.move(dir)
        when {
            visited.contains(pos to dir) -> return visited to true
            !isOnGrid(nPos) -> break
            getChar(pos) == '#' -> dir = dir.turnRight90()
            else -> {
                val newPos = pos.move(dir)
                visited.add(newPos to dir)
            }
        }
    }
    return visited to false
}
