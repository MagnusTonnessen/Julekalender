package AdventOfCode.Jul2021.Day4

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day4/Input").readLines()
    val numbers = input[0].split(",")
    val boards = mutableListOf<MutableList<MutableList<String>>>()
    var index = 2
    while (index < input.size) {
        val board = mutableListOf<MutableList<String>>()
        repeat(5) { board.add(input[index++].split(" ").toMutableList()) }
        boards.add(board)
        index++
    }

    for (marked in numbers) {
        val finishedBoards = mutableSetOf<MutableList<MutableList<String>>>()
        for (board in boards) {
            if (markBoard(board, marked)) {
                finishedBoards.add(board)
            }
        }
        boards.removeAll { finishedBoards.contains(it) }
        if (boards.isEmpty()) {
            println(finalScore(finishedBoards.first(), marked))
            return
        }
    }
}

fun markBoard(board: MutableList<MutableList<String>>, marked: String): Boolean {
    val colCount = mutableListOf(0, 0, 0, 0, 0)
    for (row in 0..4) {
        var count = 0
        for (col in 0..4) {
            if (board[row][col] == marked || board[row][col] == "X") {
                board[row][col] = "X"
                count++
                colCount[col]++
            }
        }
        if (count == 5 || colCount.contains(5)) {
            return true
        }
    }

    return false
}

fun finalScore(board: List<List<String>>, lastMarked: String): Int {
    return board.sumOf { row -> row.sumOf { c -> if (c == "X") 0 else c.toInt() } } * lastMarked.toInt()
}