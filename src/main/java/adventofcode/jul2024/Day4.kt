package adventofcode.jul2024

import adventofcode.Direction
import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    val input = File("src/Input.txt").readLines().map { it.toList() }

    input.indices.run {
        sumOf { row ->
            input[row].indices.sumOf { col ->
                Direction.eightDirections.count { dir ->
                    try {
                        input[row][col] == 'X' &&
                            input[row + 1 * dir.y][col + 1 * dir.x] == 'M' &&
                            input[row + 2 * dir.y][col + 2 * dir.x] == 'A' &&
                            input[row + 3 * dir.y][col + 3 * dir.x] == 'S'
                    } catch (e: Exception) {
                        false
                    }
                }
            }
        }.printPart1()

        sumOf { row ->
            input[row].indices.count { col ->
                try {
                    input[row][col] == 'A' &&
                        (
                            input[row - 1][col - 1] == 'M' &&
                                input[row + 1][col + 1] == 'S' ||
                                input[row - 1][col - 1] == 'S' &&
                                input[row + 1][col + 1] == 'M'
                        ) &&
                        (
                            input[row - 1][col + 1] == 'M' &&
                                input[row + 1][col - 1] == 'S' ||
                                input[row - 1][col + 1] == 'S' &&
                                input[row + 1][col - 1] == 'M'
                        )
                } catch (e: Exception) {
                    false
                }
            }
        }.printPart2()
    }
}
