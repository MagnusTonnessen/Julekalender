package AdventOfCode.Jul2023.Day10

import java.io.File

fun main() {
    File("src/AdventOfCode/Jul2022/Day10/Input")
        .readLines()
        .flatMap { if (it == "noop") { listOf(it) } else { it.split(" ") } }
        .foldIndexed(Triple(1, 0, "")) { cycle, (cumSum, signalStrength, pixels), op ->
            Triple(
                cumSum + try { op.toInt() } catch (_: Exception) { 0 },
                signalStrength + if (((cycle + 1) - 20) % 40 == 0) cumSum * (cycle + 1) else 0,
                pixels + when {
                    (cycle + 1) % 40 in cumSum until cumSum + 3 && (cycle + 1) % 40 == 0 -> "#\n"
                    (cycle + 1) % 40 in cumSum until cumSum + 3 -> "#"
                    (cycle + 1) % 40 == 0 -> ".\n"
                    else -> "."
                },
            )
        }.also {
            println("Part 1: ${it.second}")
            println("Part 2:\n${it.third}")
        }
}