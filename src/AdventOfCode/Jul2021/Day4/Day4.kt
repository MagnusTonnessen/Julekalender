package AdventOfCode.Jul2021.Day4

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day4/Input").readLines()
    val numbers = input[0].split(",").toMutableList()
    val boards = mutableListOf<MutableList<MutableList<String>>>()
    var index = 1
    while (++index < input.size) {
        val board = mutableListOf<MutableList<String>>()
        repeat(5) { board.add(input[index++].split(" ").toMutableList()) }
        boards.add(board)
    }

    val initSize = boards.size
    while (boards.isNotEmpty()) {
        val marked = numbers.removeAt(0)
        boards.apply {
            forEach {
                if (markBoard(it, marked) && size == initSize) {
                    println("Part one: ${finalScore(it, marked)}")
                }
                if (markBoard(it, marked) && size == 1) {
                    println("Part two: ${finalScore(it, marked)}")
                }
            }
            removeAll { markBoard(it, marked) }
        }
    }
}

fun markBoard(board: MutableList<MutableList<String>>, marked: String): Boolean {
    board.find { it.contains(marked) }?.apply { set(indexOf(marked), "X") }
    return board.any { it.count { c -> c == "X" } == 5 } ||
            board[0].indices.map { board.map { row -> row[it] } }.any { it.count { c -> c == "X" } == 5 }
}

fun finalScore(board: List<List<String>>, lastMarked: String): Int {
    return board.sumOf { row -> row.sumOf { c -> if (c == "X") 0 else c.toInt() } } * lastMarked.toInt()
}