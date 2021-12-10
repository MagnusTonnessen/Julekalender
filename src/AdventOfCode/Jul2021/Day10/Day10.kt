package AdventOfCode.Jul2021.Day10

import java.io.File
import java.util.*

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day10/Input").readLines()
    val mapCorrupted = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val mapComplete = mapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)
    val corrupted = mutableListOf<Char>()
    val complete = mutableListOf<Long>()
    for (line in input) {
        val stack = Stack<Char>()
        var corr = false
        for (elem in line) {
            if (elem in listOf('(', '[', '{', '<')) {
                stack.push(elem)
            } else {
                if (isCorrupted(stack.pop(), elem)) {
                    corrupted.add(elem)
                    corr = true
                    break
                }
            }
        }
        if (!corr) {
            complete.add(stack.reversed().fold(0) { acc, c -> acc * 5 + mapComplete[c]!! })
        }
    }
    println("Corrupted: ${corrupted.sumOf { mapCorrupted[it]!! }}")
    println("Complete: ${complete.sorted()[complete.size / 2]}")
}

fun isCorrupted(top: Char, next: Char): Boolean {
    return top == '(' && next != ')' ||
            top == '[' && next != ']' ||
            top == '{' && next != '}' ||
            top == '<' && next != '>'
}