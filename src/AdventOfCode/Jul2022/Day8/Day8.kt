package AdventOfCode.Jul2022.Day8

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2022/Day8/Input").readLines().map { line -> line.toCharArray().map { it.digitToInt() } }
    (input.indices).flatMap { row -> (input.indices).map { col -> visible(row, col, input) }}.count { it }.also { println("Part one: $it") }
    (input.indices).flatMap { row -> (input.indices).map { col -> scenicScore(row, col, input) }}.max().also { println("Part two: $it") }
}

fun visible(row: Int, col: Int, grid: List<List<Int>>): Boolean {
    val height = grid[row][col]
    return visibleDir(row, col, grid, height, "up") ||
            visibleDir(row, col, grid, height, "down") ||
            visibleDir(row, col, grid, height, "left") ||
            visibleDir(row, col, grid, height, "right")
 }

fun visibleDir(row: Int, col: Int, grid: List<List<Int>>, height: Int, dir: String): Boolean {
    var visible = true
    val range = when (dir) {
        "up" -> 0 until row
        "down" -> row + 1 until grid.size
        "left" -> 0 until col
        "right" -> col + 1 until grid[0].size
        else -> throw IllegalArgumentException("Invalid direction")
    }
    for (index in range) {
        visible = visible && when (dir) {
            "up", "down" -> grid[index][col]
            "left", "right" -> grid[row][index]
            else -> throw IllegalArgumentException("Invalid direction")
        } < height
    }
    return visible
}

fun scenicScore(row: Int, col: Int, grid: List<List<Int>>): Int {
    if (row == 0 || col == 0 || row == grid.lastIndex || col == grid[0].lastIndex) {
        return 0
    }
    val height = grid[row][col]
    return scenicScoreDir(row, col, grid, height, "up") *
            scenicScoreDir(row, col, grid, height, "down") *
            scenicScoreDir(row, col, grid, height, "left") *
            scenicScoreDir(row, col, grid, height, "right")}
fun scenicScoreDir(row: Int, col: Int, grid: List<List<Int>>, height: Int, dir: String): Int {
    var score = 1
    val range = when (dir) {
        "up" -> row - 1 downTo 1
        "down" -> row + 1 until grid.lastIndex
        "left" -> col - 1 downTo 1
        "right" -> col + 1 until grid[0].lastIndex
        else -> throw IllegalArgumentException("Invalid direction")
    }
    range.takeWhile { index ->
        when (dir) {
            "up", "down" -> grid[index][col]
            "left", "right" -> grid[row][index]
            else -> throw IllegalArgumentException("Invalid direction")
        } < height
    }.forEach { _ -> score++ }
    return score
}