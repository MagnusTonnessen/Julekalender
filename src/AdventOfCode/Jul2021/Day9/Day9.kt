package AdventOfCode.Jul2021.Day9

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day9/Input").readLines().map { it.toMutableList() }
    val inputInt = input.map { it.map { c -> c.toString().toInt() } }

    val localMins = mutableListOf<Int>()
    for (y in input.indices) {
        for (x in input[0].indices) {
            if (localMinimum(inputInt, x, y)) {
                localMins.add(inputInt[y][x])
            }
        }
    }
    println(localMins.sumOf { it + 1 })

    val basinSize = mutableListOf<Int>()
    var mark = 97
    for (y in input.indices) {
        for (x in input[0].indices) {
            if (input[y][x] == '9' || !input[y][x].isDigit()) {
                continue
            }
            findBasinSize(input, x, y, mark.toChar())
            basinSize.add(countSize(input, mark.toChar()))
            mark++
        }
    }
    println(basinSize.sortedDescending().subList(0, 3).reduce { acc, elem -> acc * elem })
}

fun localMinimum(input: List<List<Int>>, x: Int, y: Int): Boolean {
    return (y == 0 || (y > 0 && input[y - 1][x] > input[y][x])) &&
            (x == 0 || (x > 0 && input[y][x - 1] > input[y][x])) &&
            (y + 1 == input.size || (y + 1 < input.size && input[y + 1][x] > input[y][x])) &&
            (x + 1 == input.size || (x + 1 < input[0].size && input[y][x + 1] > input[y][x]))
}

fun findBasinSize(input: List<MutableList<Char>>, x: Int, y: Int, mark: Char) {
    if (y < 0 || x < 0 || y >= input.size || x >= input[0].size || input[y][x] == '9' || !input[y][x].isDigit()) {
        return
    }
    input[y][x] = mark
    findBasinSize(input, x, y - 1, mark)
    findBasinSize(input, x, y + 1, mark)
    findBasinSize(input, x - 1, y, mark)
    findBasinSize(input, x + 1, y, mark)
}

fun countSize(input: List<List<Char>>, mark: Char): Int {
    return input.sumOf { it.count { c -> c == mark } }
}