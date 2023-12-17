package AdventOfCode.Jul2023

import java.io.File
import kotlin.math.abs

fun main() {
    File("src/Input")
        .readLines()
        .map { it.toCharArray() }
        .run {
            val expandingRows = indices.filter { i -> get(i).all { it == '.' } }
            val expandingCols = get(0).indices.filter { i -> all { it[i] == '.' } }
            val expandedUniverse = flatMapIndexed { iRow, row ->
                val newRow = row.flatMapIndexed { iCol, col -> if (iCol in expandingCols) { listOf(col, col) } else { listOf(col) } }
                if (iRow in expandingRows) { listOf(newRow, newRow) } else { listOf(newRow) }
            }
            expandedUniverse
                .flatMapIndexed { iRow, row -> row.mapIndexedNotNull { iCol, col -> if (col == '#') iRow to iCol else null } }
                .let { it.flatMapIndexed { i, x -> it.subList(i + 1, it.size).map { y -> Pair(x, y) } } }
                .sumOf { (a, b) -> abs(a.first - b.first) + abs(a.second - b.second) }
                .let { println("Part one: $it") }
        }
}
