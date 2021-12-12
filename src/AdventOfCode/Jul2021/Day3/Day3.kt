package AdventOfCode.Jul2021.Day3

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day3/Input").readLines()
    var gamma = ""
    var epsilon = ""
    var O2 = input.toMutableList()
    var CO2 = input.toMutableList()

    for (i in input[0].indices) {
        gamma += if (input.count { it[i] == '0' } > input.size / 2) "0" else "1"
        epsilon += if (input.count { it[i] == '0' } < input.size / 2) "0" else "1"

        if (O2.size > 1) {
            O2 =
                O2.filter { (O2.count { c -> c[i] == '0' } > O2.size / 2) && (it[i] == '0') || (O2.count { c -> c[i] == '0' } <= O2.size / 2) && (it[i] == '1') }
                    .toMutableList()
        }

        if (CO2.size > 1) {
            CO2 =
                CO2.filter { (CO2.count { it[i] == '0' } <= CO2.size / 2) && (it[i] == '0') || (CO2.count { it[i] == '0' } > CO2.size / 2) && (it[i] == '1') }
                    .toMutableList()
        }
    }

    println("Part one: ${gamma.toInt(2) * epsilon.toInt(2)}")
    println("Part two: ${O2[0].toLong(2).times(CO2[0].toLong(2))}")
}