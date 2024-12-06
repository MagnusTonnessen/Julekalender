package adventofcode.jul2024

import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    val input = File("src/Input.txt").readText()
    val pattern = """mul\(\d+,\d+\)|do\(\)|don't\(\)""".toRegex()
    pattern
        .findAll(input)
        .map { it.value }
        .run {
            filter { it.contains("mul") }
                .sumOf(::product)
                .printPart1()

            fold(Pair(true, 0L)) { (active, sum), it ->
                when (it) {
                    "do()" -> Pair(true, sum)
                    "don't()" -> Pair(false, sum)
                    else -> if (active) Pair(active, sum + product(it)) else Pair(active, sum)
                }
            }.second.printPart2()
        }
}

private fun product(s: String) = """\d+""".toRegex().findAll(s).map { it.value.toLong() }.reduce { a, b -> a * b }
