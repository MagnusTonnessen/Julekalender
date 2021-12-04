package AdventOfCode.Jul2021.Day3

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day3/Input").readLines()
    var gamma = ""
    var epsilon = ""
    var O2 = input.toMutableList()
    var CO2 = input.toMutableList()

    for (i in 0..11) {
        var zeros = 0
        var ones = 0
        for (bin in input) {
            if (bin[i] == '0') {
                zeros++
            } else {
                ones++
            }
        }
        gamma += if (zeros > ones) "0" else "1"
        epsilon += if (zeros < ones) "0" else "1"
    }

    for (i in 0..11) {
        var zeros = 0
        var ones = 0
        for (bin in O2) {
            if (bin[i] == '0') {
                zeros++
            } else {
                ones++
            }
        }
        if (O2.size > 1) {
            O2 = O2.filter { (zeros > ones) && (it[i] == '0') || (zeros <= ones) && (it[i] == '1') }.toMutableList()
        }
    }

    for (i in 0..11) {
        var zeros = 0
        var ones = 0
        for (bin in CO2) {
            if (bin[i] == '0') {
                zeros++
            } else {
                ones++
            }
        }
        if (CO2.size > 1) {
            CO2 = CO2.filter { (zeros <= ones) && (it[i] == '0') || (zeros > ones) && (it[i] == '1') }.toMutableList()
        }
    }
    println("Gamma times epsilon = ${gamma.toLong(2).times(epsilon.toLong(2))}")
    println("O2 times CO2 = ${O2[0].toLong(2).times(CO2[0].toLong(2))}")
}