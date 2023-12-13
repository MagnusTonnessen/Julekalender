package AdventOfCode.Jul2023

import java.io.File

fun main() {
    File("src/Input")
        .readLines()
        .map { it.toCharArray() }
        .run {
            val start = Pair(indexOfFirst { it.contains('S') }, find { it.contains('S') }!!.indexOf('S'))
            for (dir in listOf('N', 'E', 'S', 'W')) {
                var length = 0
                var currPos = start
                var currDir = dir
                var currChar = getOrNull(currPos.first)?.getOrNull(currPos.second)
                println("=".repeat(20))
                do {
                    val (dy, dx, newDir) = posDirMap[currChar]?.get(currDir) ?: break
                    currPos = currPos.run { Pair(first + dy, second + dx) }
                    currDir = newDir
                    currChar = getOrNull(currPos.first)?.getOrNull(currPos.second)
                    length++
                } while (currChar != null && currChar != 'S')
                if (currChar == 'S') {
                    println("Part one: ${length.div(2)}")
                    break
                }
            }
        }
}

val posDirMap = mapOf(
    'S' to mapOf(
        'N' to Triple(-1, 0, 'N'),
        'E' to Triple(0, 1, 'E'),
        'S' to Triple(1, 0, 'S'),
        'W' to Triple(0, -1, 'W')
    ),
    '|' to mapOf('N' to Triple(-1, 0, 'N'), 'S' to Triple(1, 0, 'S')),
    '-' to mapOf('E' to Triple(0, 1, 'E'), 'W' to Triple(0, -1, 'W')),
    'F' to mapOf('N' to Triple(0, 1, 'E'), 'W' to Triple(1, 0, 'S')),
    'L' to mapOf('S' to Triple(0, 1, 'E'), 'W' to Triple(-1, 0, 'N')),
    'J' to mapOf('S' to Triple(0, -1, 'W'), 'E' to Triple(-1, 0, 'N')),
    '7' to mapOf('N' to Triple(0, -1, 'W'), 'E' to Triple(1, 0, 'S')),
    '.' to null
)
