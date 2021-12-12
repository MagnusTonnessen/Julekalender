package AdventOfCode.Jul2021.Day11

import java.io.File

fun main() {
    val input =
        File("src/AdventOfCode/Jul2021/Day11/Input").readLines().map { it.map { c -> c.digitToInt() }.toMutableList() }
    var flashes = 0
    var step = 1
    while (input.any { it.any { c -> c != 0 } } || step <= 100) {
        val increase = mutableListOf<Pair<Int, Int>>()
        for (y in input.indices) {
            for (x in input[0].indices) {
                input[y][x]++
                if (input[y][x] == 10) {
                    flashes++
                    increase.addAll(adjacent(y, x))
                }
            }
        }
        while (increase.isNotEmpty()) {
            val next = increase.first()
            increase.remove(next)
            input[next.first][next.second]++
            if (input[next.first][next.second] == 10) {
                flashes++
                increase.addAll(adjacent(next.first, next.second))
            }
        }
        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[y][x] > 9) {
                    input[y][x] = 0
                }
            }
        }
        if (step == 100) {
            println("100: $flashes")
        }
        if (input.all { it.all { c -> c == 0 } }) {
            println("All flash: $step")
        }
        step++
    }
}

fun adjacent(y: Int, x: Int): Set<Pair<Int, Int>> {
    val set = mutableSetOf<Pair<Int, Int>>()
    val ys = listOf(-1, -1, -1, 0, 0, 1, 1, 1)
    val xs = listOf(-1, 0, 1, -1, 1, -1, 0, 1)
    for (i in 0 until 8) {
        if (valid(y + ys[i], x + xs[i])) {
            set.add(Pair(y + ys[i], x + xs[i]))
        }
    }
    return set
}

fun valid(y: Int, x: Int): Boolean {
    return x >= 0 && y >= 0 && x <= 9 && y <= 9
}