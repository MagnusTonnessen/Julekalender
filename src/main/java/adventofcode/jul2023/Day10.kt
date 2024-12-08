package adventofcode.jul2023

import java.io.File

fun main() {
    File("src/Input.txt").readLines().map { it.toCharArray() }.run {
        val start = Pair(indexOfFirst { it.contains('S') }, find { it.contains('S') }!!.indexOf('S'))
        val pipePositions = mutableSetOf<Pair<Int, Int>>()
        for (dir in listOf('N', 'E', 'S', 'W')) {
            pipePositions.clear()
            var currPos = start
            var currDir = dir
            var currChar = getOrNull(currPos.first)?.getOrNull(currPos.second)
            do {
                pipePositions.add(currPos)
                val (dy, dx, newDir) = posDirMap[currChar]?.get(currDir) ?: break
                currPos = currPos.run { Pair(first + dy, second + dx) }
                currDir = newDir
                currChar = getOrNull(currPos.first)?.getOrNull(currPos.second)
            } while (currChar != null && currChar != 'S')
            if (currChar == 'S') {
                println("Part one: ${pipePositions.size.div(2)}")
                break
            }
        }

        var inside = false
        var count = 0
        var previousCorner: Char? = null
        flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                if (pipePositions.contains(Pair(y, x))) {
                    when (char) {
                        'S' -> 'J' // www.hackySolutions.mt
                        else -> char
                    }
                } else {
                    '.'
                }
            }
        }.forEach {
            when (it) {
                '.' ->
                    if (inside) {
                        count++
                    }

                '|' -> inside = !inside
                'F' -> previousCorner = 'F'
                'L' -> previousCorner = 'L'

                '7' ->
                    if (previousCorner == 'L') {
                        inside = !inside
                    }

                'J' ->
                    if (previousCorner == 'F') {
                        inside = !inside
                    }
            }
        }
        println("Part two: $count")
    }
}

val posDirMap =
    mapOf(
        'S' to
            mapOf(
                'N' to Triple(-1, 0, 'N'),
                'E' to Triple(0, 1, 'E'),
                'S' to Triple(1, 0, 'S'),
                'W' to Triple(0, -1, 'W'),
            ),
        '|' to mapOf('N' to Triple(-1, 0, 'N'), 'S' to Triple(1, 0, 'S')),
        '-' to mapOf('E' to Triple(0, 1, 'E'), 'W' to Triple(0, -1, 'W')),
        'F' to mapOf('N' to Triple(0, 1, 'E'), 'W' to Triple(1, 0, 'S')),
        'L' to mapOf('S' to Triple(0, 1, 'E'), 'W' to Triple(-1, 0, 'N')),
        'J' to mapOf('S' to Triple(0, -1, 'W'), 'E' to Triple(-1, 0, 'N')),
        '7' to mapOf('N' to Triple(0, -1, 'W'), 'E' to Triple(1, 0, 'S')),
        '.' to null,
    )
