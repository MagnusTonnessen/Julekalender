package Knowit.Jul2021.Day2

import java.awt.geom.Point2D
import java.io.File
import java.lang.Math.toRadians
import kotlin.math.*

fun main() {
    val input = File("src/Knowit/Jul2021/Day2/cities.csv")
        .readLines()
        .asSequence()
        .map { it.split(",Point(")[1].replace(")", "") }
        .map { Point2D.Double(it.split(" ")[0].toDouble(), it.split(" ")[1].toDouble()) }
        .sortedBy { distNorthPole(it) }
        .toMutableList()

    var curr = input.removeFirst()
    var distance = distNorthPole(curr)
    while (input.isNotEmpty()) {
        input.sortBy { dist(curr, it) }
        val next = input.removeFirst()
        distance += dist(curr, next)
        curr = next
    }
    distance += distNorthPole(curr)
    println(distance)
}

fun distNorthPole(p: Point2D): Double {
    return toRadians(90 - p.y) * 6371.0
}

fun dist(p: Point2D, q: Point2D): Double {
    val r = 6371.0
    val latDist = toRadians(q.y.minus(p.y))
    val lonDist = toRadians(q.x.minus(p.x))
    val a = sin(latDist / 2.0).pow(2) + cos(toRadians(p.y)) * cos(toRadians(q.y)) * sin(lonDist / 2.0).pow(2)
    val c = 2.0 * atan2(sqrt(a), sqrt(1 - a))
    return r * c
}
