package adventofcode.jul2023

import java.io.File
import kotlin.math.abs

fun main() {
    File("src/Input.txt").readLines().map { it.toList() }.run {
        val expandingRows = indices.filter { i -> get(i).all { it == '.' } }
        val expandingCols = get(0).indices.filter { i -> all { it[i] == '.' } }
        flatMapIndexed { iRow, row -> row.mapIndexedNotNull { iCol, col -> if (col == '#') iRow.toLong() to iCol.toLong() else null } }
            .map { p ->
                Pair(
                    p.first + expandingRows.count { it < p.first },
                    p.second + expandingCols.count { it < p.second },
                )
            }.let { it.flatMapIndexed { iRow, a -> it.subList(iRow + 1, it.size).map { b -> Pair(a, b) } } }
            .sumOf { (a, b) -> abs(a.first - b.first) + abs(a.second - b.second) }
            .let { println("Part one: $it") }
        flatMapIndexed { iRow, row -> row.mapIndexedNotNull { iCol, col -> if (col == '#') iRow.toLong() to iCol.toLong() else null } }
            .map { p ->
                Pair(
                    p.first + expandingRows.count { it < p.first } * 999999,
                    p.second + expandingCols.count { it < p.second } * 999999,
                )
            }.let { it.flatMapIndexed { iRow, a -> it.subList(iRow + 1, it.size).map { b -> Pair(a, b) } } }
            .sumOf { (a, b) -> abs(a.first - b.first) + abs(a.second - b.second) }
            .let { println("Part two: $it") }
    }
}
