package AdventOfCode.Jul2022.Day3

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2022/Day3/Input").readLines()
    var part1 = 0
    var part2 = 0
    var lines = mutableListOf<String>()
    input.forEach { line ->
        lines.add(line)
        if (lines.size == 3) {
            loop@
            for (char in lines[0]) {
                if (lines[1].contains(char) && lines[2].contains(char)) {
                    part2 += if (char.isLowerCase()) { char.code - 96 } else { char.code - 64 + 26}
                    break@loop
                }
            }
            lines.clear()
        }
        val checked = HashSet<Char>()
        val first = line.subSequence(0, line.length / 2).toString()
        val second = line.subSequence(line.length / 2, line.length).toString()
        first.forEach { char ->
            if (checked.add(char) && second.contains(char)) {
                part1 += if (char.isLowerCase()) { char.code - 96 } else { char.code - 64 + 26}
            }
        }
    }.also { println("Part one: $part1"); println("Part two: $part2") }
}