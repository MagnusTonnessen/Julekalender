package AdventOfCode.Jul2021.Day8

import java.io.File

fun main() {

    println(File("src/AdventOfCode/Jul2021/Day8/Input")
        .readLines()
        .map { it.split(" | ") }
        .sumOf { line ->
            val rule = line[0].split(" ")
            val code = line[1].split(" ")

            val map = mutableMapOf<Int, Set<Char>>()
            map[1] = rule.first { it.length == 2 }.toSet()
            map[4] = rule.first { it.length == 4 }.toSet()
            map[7] = rule.first { it.length == 3 }.toSet()
            map[8] = rule.first { it.length == 7 }.toSet()
            map[3] = rule.first { it.length == 5 && map[1]!!.all { c -> it.contains(c) } }.toSet()
            map[9] = rule.first { it.length == 6 && map[4]!!.all { c -> it.contains(c) } }.toSet()
            map[0] = rule.first { it.length == 6 && map[1]!!.all { c -> it.contains(c) } && it.toSet() != map[9] }
                .toSet()
            map[6] = rule.first { it.length == 6 && it.toSet() != map[0] && it.toSet() != map[9] }.toSet()
            map[5] = rule.first { it.length == 5 && it.all { c -> map[6]!!.contains(c) } }.toSet()
            map[2] = rule.first { it.length == 5 && it.toSet() != map[5] && it.toSet() != map[3] }.toSet()

            return@sumOf code.map { map.filterValues { value -> it.toSet() == value.toSet() }.keys.first() }
                .joinToString("").toInt()
        }
    )
}