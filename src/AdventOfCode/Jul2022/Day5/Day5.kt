package AdventOfCode.Jul2022.Day5

import java.io.File

var gridPart1 = Array(9) { Array(81) { '-' } }
var gridPart2 = Array(9) { Array(81) { '-' } }
fun main() {
    val input = File("src/AdventOfCode/Jul2022/Day5/Input").readLines()
    input.take(8).reversed().forEach {
        var index = 0
        while (index < it.length) {
            val char = it.slice(index..index+2)
            if (char != "   ") {
                add(index / 4, char[1], gridPart1)
                add(index / 4, char[1], gridPart2)
            }
            index += 4
        }
    }
    
    input.drop(10).forEach { 
        val op = it.replace("[a-zA-Z]+ ".toRegex(), "").split(" ").map { num -> num.toInt() }
        movePart1(op[0], op[1] - 1, op[2] - 1)
        movePart2(op[0], op[1] - 1, op[2] - 1)
    }

    (0 until 9).map { remove(it, gridPart1) }.also { println("Part one: ${it.joinToString("")}") }
    (0 until 9).map { remove(it, gridPart2) }.also { println("Part two: ${it.joinToString("")}") }}

fun movePart1(num: Int, fromCol: Int, toCol: Int) {
    for (i in 0 until num) {
        add(toCol, remove(fromCol, gridPart1), gridPart1)
    }
}

fun movePart2(num: Int, fromCol: Int, toCol: Int) {
    (0 until num)
        .map { remove(fromCol, gridPart2) }
        .reversed()
        .forEach { add(toCol, it, gridPart2) }
}

fun remove(col: Int, grid: Array<Array<Char>>): Char {
    for (row in 0..80) {
        if (grid[col][row] != '-') {
            val num = grid[col][row]
            grid[col][row] = '-'
            return num
        }
    }
    throw Exception("No char found")
}

fun add(col: Int, char: Char, grid: Array<Array<Char>>) {
    for (row in 0 .. 81) {
        if (row == 81 || grid[col][row] != '-') {
            grid[col][row - 1] = char
            return
        }
    }
    throw Exception("No space found")
}