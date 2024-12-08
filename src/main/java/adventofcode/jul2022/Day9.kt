package adventofcode.jul2022

import java.io.File
import kotlin.math.abs
import kotlin.math.max

fun main() {
    val input = File("src/Input.txt").readLines()
    val posKnots =
        mutableListOf(
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
            Pair(0, 0),
        )

    input
        .flatMap { op ->
            val (dir, steps) = op.split(" ")
            (1..steps.toInt()).map {
                when (dir) {
                    "U" -> posKnots[0] = Pair(posKnots[0].first, posKnots[0].second + 1)
                    "D" -> posKnots[0] = Pair(posKnots[0].first, posKnots[0].second - 1)
                    "R" -> posKnots[0] = Pair(posKnots[0].first + 1, posKnots[0].second)
                    "L" -> posKnots[0] = Pair(posKnots[0].first - 1, posKnots[0].second)
                }
                moveKnots(posKnots)
                Pair(posKnots[1], posKnots[9])
            }
        }.also {
            println("Part one: ${it.distinctBy { it.first }.size}")
            println("Part one: ${it.distinctBy { it.second }.size}")
        }
}

fun dist(
    head: Pair<Int, Int>,
    tail: Pair<Int, Int>,
): Int = max(abs(head.first - tail.first), abs(head.second - tail.second))

fun moveKnots(knots: MutableList<Pair<Int, Int>>) {
    var prevPos = knots[0]
    knots.indices.drop(1).forEach {
        val currPos = knots[it]
        knots[it] =
            if (dist(currPos, prevPos) < 2) {
                currPos
            } else {
                Pair(
                    when {
                        prevPos.first > currPos.first -> currPos.first + 1
                        prevPos.first < currPos.first -> currPos.first - 1
                        else -> currPos.first
                    },
                    when {
                        prevPos.second > currPos.second -> currPos.second + 1
                        prevPos.second < currPos.second -> currPos.second - 1
                        else -> currPos.second
                    },
                )
            }
        prevPos = currPos
    }
}
