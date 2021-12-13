package AdventOfCode.Jul2021.Day9

import java.awt.Point
import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day9/Input").readLines().map { it.toMutableList() }
    val inputInt = input.map { it.map { c -> c.toString().toInt() } }
    println("Part one: " + inputInt
        .indices
        .flatMap { y -> inputInt[0].indices.map { x -> Point(x, y) } }
        .filter { isLocalMinimum(inputInt, it.x, it.y) }
        .sumOf { inputInt[it.y][it.x] + 1 }
    )

    println("Part two: " + inputInt
        .indices
        .flatMap { y -> inputInt[0].indices.map { x -> Point(x, y) } }
        .map {
            if (input[it.y][it.x].isDigit() && input[it.y][it.x] != '9') findBasinSize(input, it.x, it.y)
            else 0
        }
        .sortedDescending()
        .subList(0, 3)
        .reduce { acc, elem -> acc * elem }

    )
}

fun isLocalMinimum(input: List<List<Int>>, x: Int, y: Int): Boolean {
    return (y == 0 || (y > 0 && input[y - 1][x] > input[y][x])) &&
            (x == 0 || (x > 0 && input[y][x - 1] > input[y][x])) &&
            (y + 1 == input.size || (y + 1 < input.size && input[y + 1][x] > input[y][x])) &&
            (x + 1 == input.size || (x + 1 < input[0].size && input[y][x + 1] > input[y][x]))
}

fun findBasinSize(input: List<MutableList<Char>>, x: Int, y: Int): Int {
    return if (y < 0 || x < 0 || y >= input.size || x >= input[0].size || input[y][x] == '9' || !input[y][x].isDigit()) {
        0
    } else {
        input[y][x] = 'x'
        1 + findBasinSize(input, x, y - 1) +
                findBasinSize(input, x, y + 1) +
                findBasinSize(input, x - 1, y) +
                findBasinSize(input, x + 1, y)
    }
}
