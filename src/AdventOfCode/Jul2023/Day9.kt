package AdventOfCode.Jul2023

import java.io.File
import kotlin.math.abs

fun main() {
    File("src/Input")
        .readLines()
        .map { it.split(" ").map { it.toInt() } }
//        .onEach { println(it) }
        .run {
            sumOf {
                var list = it
                var extrapolatedValue = it.last()
                    println("List: $list")
                while (list.size > 1 && list.max() > 0 ) {
                    list = List(list.lastIndex) { abs(list[it] - list[it + 1]) }
                    extrapolatedValue += list.last()
                    println("List: $list")
                }
                extrapolatedValue
            }.let { println("Part one: $it") }
        }
}