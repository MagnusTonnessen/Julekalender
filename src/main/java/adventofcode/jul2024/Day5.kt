package adventofcode.jul2024

import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    File("src/Input.txt")
        .readLines()
        .filter { it.isNotBlank() }
        .partition { it.contains("|") }
        .let { (orders, updates) ->
            orders.map { it.split("|").let { it[0] to it[1] } } to
                updates.map { it.split(",") }
        }.let { (orders, updates) ->
            updates
                .partition { it.isValid(orders) }
                .let { (correctlyOrdered, incorrectlyOrdered) ->
                    correctlyOrdered
                        .sumOf { it[it.size / 2].toLong() }
                        .printPart1()

                    incorrectlyOrdered
                        .map { it.fixErrors(orders) }
                        .sumOf { it[it.size / 2].toLong() }
                        .printPart2()
                }
        }
}

fun List<String>.isValid(orders: List<Pair<String, String>>): Boolean = orders.all { (a, b) -> indexOf(b) == -1 || indexOf(a) < indexOf(b) }

fun List<String>.fixErrors(orders: List<Pair<String, String>>): List<String> {
    val list = toMutableList()
    while (!list.isValid(orders)) {
        val (a, b) = orders.first { (a, b) -> list.indexOf(b) != -1 && list.indexOf(a) > list.indexOf(b) }
        val indexA = list.indexOf(a)
        val indexB = list.indexOf(b)
        list[indexA] = b
        list[indexB] = a
    }
    return list
}
