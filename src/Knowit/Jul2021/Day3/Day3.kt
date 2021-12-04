package Knowit.Jul2021.Day3

import java.io.File

fun main() {
    val input = File("src/Knowit/Jul2021/Day3/input.txt").readLines()[0]

    for (length in input.length downTo 1 step 2) {
        if (length.mod(100) == 0 || length < 100 && length.mod(10) == 0) {
            println("Length = $length")
        }
        for (index in 0 until input.length - length) {
            if (balanced(input.substring(index, index + length))) {
                println("$length, $index")
                return
            }
        }
    }
}

fun balanced(s: String): Boolean {
    return s.count { it == 'J' }.toDouble() == s.length / 2.0
}