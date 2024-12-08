package adventofcode.jul2021

import java.io.File

fun main() {
    val input = File("src/Input.txt").readLines()
    var gamma = ""
    var epsilon = ""
    val O2 = input.toMutableList()
    val CO2 = input.toMutableList()

    for (i in input[0].indices) {
        gamma += if (input.count { it[i] == '0' } > input.size / 2) "0" else "1"
        epsilon += if (input.count { it[i] == '0' } < input.size / 2) "0" else "1"

        if (O2.size > 1) {
            O2.removeIf {
                (O2.count { c -> c[i] == '0' } > O2.size / 2) &&
                    (it[i] != '0') ||
                    (O2.count { c -> c[i] == '0' } <= O2.size / 2) &&
                    (it[i] != '1')
            }
        }

        if (CO2.size > 1) {
            CO2.removeIf { it ->
                (CO2.count { it[i] == '0' } <= CO2.size / 2) &&
                    (it[i] != '0') ||
                    (CO2.count { it[i] == '0' } > CO2.size / 2) &&
                    (it[i] != '1')
            }
        }
    }

    println("Part one: ${gamma.toInt(2) * epsilon.toInt(2)}")
    println("Part two: ${O2[0].toInt(2) * CO2[0].toInt(2)}")
}
