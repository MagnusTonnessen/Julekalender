package adventofcode.jul2022

import java.io.File

fun main() {
    File("src/Input.txt")
        .readText()
        .split("\r\n\r\n")
        .map { it ->
            it.lines().sumOf { it.toInt() }
        }.sortedDescending()
        .take(3)
        .also {
            println("Part one: " + it.first())
            println("Part two: " + it.sum())
        }
}
