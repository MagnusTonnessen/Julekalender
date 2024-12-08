package adventofcode.jul2023

import adventofcode.Direction
import adventofcode.Direction.Companion.idxToDir
import adventofcode.Direction.Companion.toDir
import adventofcode.Position
import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("src/Input.txt").readLines().map { it.split(" ") }

    val inputPartOne = input.map { (dir, dist) -> dir.first().toDir() to dist.toInt() }

    val inputPartTwo =
        input
            .map { it[2].replace(Regex("[()#]"), "") }
            .map { it.last().idxToDir() to it.dropLast(1).toInt(16) }

    println("Part one: ${inputPartOne.toCoordinates().gaussAreaFormula()}")
    println("Part two: ${inputPartTwo.toCoordinates().gaussAreaFormula()}")
}

fun List<Pair<Direction, Int>>.toCoordinates(): List<Position> {
    val positions = mutableListOf<Position>()
    val pos = Position(0, 0)
    for ((dir, dist) in this) {
        repeat(dist) {
            val newPos = pos.move(dir)
            positions.add(newPos)
        }
    }

    return positions
}

fun List<Position>.gaussAreaFormula(): Long {
    val n = size
    var area = 0L

    for (i in indices) {
        val p1 = this[i]
        val p2 = this[(i + 1) % n]
        area += p1.x * p2.y - p1.y * p2.x
    }

    return abs(area) / 2 + size / 2 + 1
}
