package AdventOfCode.Jul2021.Day5

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day5/Input")
        .readLines()
        .map {
            it.split(Regex(" -> |,"))
                .map(String::toInt)
        }

    val arr = Array(input.maxOf { maxOf(it[1], it[3]) } + 1) { Array(input.maxOf { maxOf(it[0], it[2]) + 1 }) { 0 } }
    for (line in input) {
        if (line[0] == line[2]) {
            for (y in minOf(line[1], line[3])..maxOf(line[1], line[3])) {
                arr[y][line[0]] = arr[y][line[0]] + 1
            }
        } else if (line[1] == line[3]) {
            for (x in minOf(line[0], line[2])..maxOf(line[0], line[2])) {
                arr[line[1]][x] = arr[line[1]][x] + 1
            }
        } else {
            var y = minOf(line[1], line[3])
            val startX = if (line[1] < line[3]) line[0] else line[2]
            var x = startX
            while (y <= maxOf(line[1], line[3])) {
                arr[y][x] = arr[y][x] + 1
                x = when {
                    startX == line[0] && line[0] < line[2] -> x + 1
                    startX == line[0] && line[0] > line[2] -> x - 1
                    startX == line[2] && line[0] > line[2] -> x + 1
                    startX == line[2] && line[0] < line[2] -> x - 1
                    else -> {
                        x
                    }
                }
                y++
            }
        }
    }

    println(arr.sumOf { row -> row.count { v -> v > 1 } })
}
