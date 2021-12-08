package AdventOfCode.Jul2021.Day7

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day7/Input").readLines()[0].split(",").map { it.toInt() }
    var minFuel = Double.MAX_VALUE
    for (pos in input.minOf { it }..input.maxOf { it }) {
        val fuel = input.sumOf { (abs(it - pos) * (abs(it - pos) + 1)) / 2.0 }
        if (fuel < minFuel) {
            minFuel = fuel
        }
    }
    println(minFuel.toInt())
}