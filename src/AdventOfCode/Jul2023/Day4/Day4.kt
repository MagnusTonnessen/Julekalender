package AdventOfCode.Jul2023.Day4

import java.io.File
import kotlin.math.pow

fun main() {
    File("src/AdventOfCode/Jul2023/Day4/Input").readLines()
        .map { it.replace("  ", " ").split(": ")[1].split(" | ").map { it.split(" ").map { it.toInt() } } }
        .map { it[0].intersect(it[1]).count() }.run {
            println("Part one: ${sumOf { 2.0.pow(it.dec()).toInt() }}")
            println(
                "Part two: ${
                    foldIndexed(MutableList(size) { 1 }) { index, cardCount, count ->
                        cardCount.apply {
                            subList(index + 1, index + 1 + count).replaceAll { it + cardCount[index] }
                        }
                    }.sum()
                }",
            )
        }
}
