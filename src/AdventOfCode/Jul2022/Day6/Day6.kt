package AdventOfCode.Jul2022.Day6

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2022/Day6/Input").readText().split("")
    for (index in input.indices) {
        if (input.slice(index..index + 3).distinct().size == 4) {
            println("Part one: ${index + 4}")
            break
        }
    }
    for (index in input.indices) {
        if (input.slice(index..index+13).distinct().size == 14) {
            println("Part two: ${index + 14}")
            break
        }
    }
}