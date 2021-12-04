package AdventOfCode.Jul2021.Day1

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day1/Input").readLines()
    var count = 0
    for (i in 3 until input.size) {
        if (input[i].toInt() + input[i - 1].toInt() + input[i - 2].toInt() > input[i - 1].toInt() + input[i - 2].toInt() + input[i - 3].toInt()) {
            count++
        }
    }
    println(count)
}