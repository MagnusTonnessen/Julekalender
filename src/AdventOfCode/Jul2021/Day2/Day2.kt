package AdventOfCode.Jul2021.Day2

import java.io.File

fun main() {
    var horPos = 0L
    var verPos = 0L
    var aim = 0L
    File("src/AdventOfCode/Jul2021/Day2/Input")
        .readLines()
        .map { Pair(it.split(" ")[0], it.split(" ")[1].toInt()) }
        .forEach {
            when (it.first) {
                "forward" -> {
                    horPos += it.second
                    verPos += aim * it.second
                }
                "backward" -> horPos -= it.second
                "up" -> aim -= it.second
                "down" -> aim += it.second
            }
        }
    println(horPos * verPos)
}