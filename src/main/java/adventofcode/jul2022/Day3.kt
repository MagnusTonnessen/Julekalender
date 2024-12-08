package adventofcode.jul2022

import java.io.File

fun main() {
    val input = File("src/Input.txt").readLines()
    input
        .sumOf { line ->
            val checked = HashSet<Char>()
            val first = line.slice(0 until line.length / 2)
            val second = line.slice(line.length / 2 until line.length)
            first.sumOf { char ->
                if (!checked.add(char) || !second.contains(char)) {
                    0
                } else if (char.isLowerCase()) {
                    char.code - 96
                } else {
                    char.code - 64 + 26
                }
            }
        }.also { println("Part one: $it") }

    input.indices
        .step(3)
        .sumOf {
            input[it]
                .find { char ->
                    input[it + 1].contains(char) && input[it + 2].contains(char)
                }?.let { char ->
                    if (char.isLowerCase()) {
                        char.code - 96
                    } else {
                        char.code - 64 + 26
                    }
                } ?: 0
        }.also { println("Part two: $it") }
}
