package AdventOfCode.Jul2023

import java.io.File
import kotlin.math.min

fun main() {
    File("src/Input")
        .readText()
        .split("\n\n")
        .map { it.lines().map { it.toList() } }
        .run {
            println("Part one: ${sumOf { it.findMirror()!! }}")
            println("Part two: ${sumOf { it.findMirrorWithSmudge() }}")
        }
}

private fun List<List<Char>>.findMirrorWithSmudge(): Int {
    val mirror = findMirror()!!
    for (y in indices) {
        for (x in get(0).indices) {
            return mapIndexed { i, row ->
                if (i == y)
                    row.mapIndexed { j, c ->
                        when {
                            j == x && c == '#' -> '.'
                            j == x && c == '.' -> '#'
                            else               -> c
                        }
                    }
                else row
            }.findMirror(mirror) ?: continue
        }
    }
    throw Exception("No mirror found")
}

private fun List<List<Char>>.findMirror(skip: Int = -1): Int? =
    indices.drop(1).find { if (it == skip / 100) false else isMirrorRow(it) }?.times(100) ?:
        get(0).indices.drop(1).find { if (it == skip) false else isMirrorColumn(it) }

private fun List<List<Char>>.isMirrorColumn(col: Int): Boolean =
    min(col, get(0).size - col)
        .let { width ->
            indices.none { r ->
                get(r).subList(col - width, col) != get(r).subList(col, col + width).reversed()
            }
        }

private fun List<List<Char>>.isMirrorRow(row: Int): Boolean =
    min(row, size - row)
        .let { width ->
            get(0).indices.none { c ->
                map { it[c] }.subList(row - width, row) != map { it[c] }.subList(row, row + width).reversed()
            }
        }
