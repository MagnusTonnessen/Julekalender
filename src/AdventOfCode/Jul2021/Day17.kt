package AdventOfCode.Jul2021

import java.awt.Point
import java.io.File

fun main() {
    val input = File("src/Input").readLines()[0].split(",").map { it.toInt() }
    // val input = "20,30,-10,-5".split(",").map { it.toInt() }

    var maxY = 0
    var validVelocities = 0
    for (dy in input[2]..1000) {
        for (dx in 0..input[1]) {
            var pointVelocity = Pair(Point(0, 0), Point(dx, dy))
            var currMaxY = pointVelocity.first.y
            while (pointVelocity.first.y > input[2] && pointVelocity.first.x <= input[1] && !valid(
                    pointVelocity,
                    input
                )
            ) {
                pointVelocity = step(pointVelocity)
                currMaxY = maxOf(currMaxY, pointVelocity.first.y)
            }
            if (valid(pointVelocity, input)) {
                maxY = maxOf(maxY, currMaxY)
                validVelocities++
            }
        }
    }
    println("Part one: $maxY")
    println("Part two: $validVelocities")
}

fun step(pointVelocity: Pair<Point, Point>): Pair<Point, Point> {
    val point = Point(pointVelocity.first.x + pointVelocity.second.x, pointVelocity.first.y + pointVelocity.second.y)
    val velocity = Point(pointVelocity.second.x + pointVelocity.second.x.compare(), pointVelocity.second.y - 1)
    return Pair(point, velocity)
}

fun valid(pointVelocity: Pair<Point, Point>, bounds: List<Int>): Boolean {
    return pointVelocity.first.x >= bounds[0] &&
            pointVelocity.first.x <= bounds[1] &&
            pointVelocity.first.y >= bounds[2] &&
            pointVelocity.first.y <= bounds[3]
}

private fun Int.compare(): Int {
    return when {
        this > 0 -> -1
        this < 0 -> 1
        else -> 0
    }
}
