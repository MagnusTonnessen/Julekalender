package adventofcode.jul2021

import java.io.File

fun main() {
    var horPos = 0L
    var verPosPartOne = 0L
    var verPosPartTwo = 0L
    File("src/AdventOfCode/Jul2021/Day2/Input.txt")
        .readLines()
        .map { Pair(it.split(" ")[0], it.split(" ")[1].toInt()) }
        .forEach {
            when (it.first) {
                "forward" -> {
                    horPos += it.second
                    verPosPartTwo += verPosPartOne * it.second
                }
                "backward" -> horPos -= it.second
                "up" -> verPosPartOne -= it.second
                "down" -> verPosPartOne += it.second
            }
        }
    println("Part one: ${horPos * verPosPartOne}")
    println("Part two: ${horPos * verPosPartTwo}")
}
