package AdventOfCode.Jul2021.Day6

import java.io.File

fun main() {
    val input =
        File("src/AdventOfCode/Jul2021/Day6/Input").readLines().first().split(",").map { it.toInt() }.toMutableList()

    var map = mutableMapOf(
        0 to input.count { it == 0 }.toLong(),
        1 to input.count { it == 1 }.toLong(),
        2 to input.count { it == 2 }.toLong(),
        3 to input.count { it == 3 }.toLong(),
        4 to input.count { it == 4 }.toLong(),
        5 to input.count { it == 5 }.toLong(),
        6 to input.count { it == 6 }.toLong(),
        7 to input.count { it == 7 }.toLong(),
        8 to input.count { it == 8 }.toLong()
    )
    for (day in 0 until 256) {
        map = mutableMapOf(
            0 to map[1]!!,
            1 to map[2]!!,
            2 to map[3]!!,
            3 to map[4]!!,
            4 to map[5]!!,
            5 to map[6]!!,
            6 to map[7]!! + map[0]!!,
            7 to map[8]!!,
            8 to map[0]!!
        )
    }
    println(map.values.sum())
}