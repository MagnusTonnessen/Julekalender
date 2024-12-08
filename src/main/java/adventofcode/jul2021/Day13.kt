package adventofcode.jul2021

import java.awt.Point
import java.io.File

fun main() {
    val input = File("src/Input.txt").readLines()
    var points =
        input
            .takeWhile { it.contains(",") }
            .map { Point(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }
            .toSet()

    val folds =
        input
            .takeLastWhile { it.contains("fold") }
            .map { it.removePrefix("fold along ").split("=") }
            .map { Pair(it[0], it[1].toInt()) }

    println("Part one: ${fold(points, folds[0]).size}")

    folds.forEach { points = fold(points, it) }

    val arr = Array(points.maxOf { it.y } + 1) { Array(points.maxOf { it.x } + 1) { " " } }

    points.forEach { arr[it.y][it.x] = "@" }

    println("Part two: ")
    println(arr.joinToString("\n") { it.joinToString("") })
}

private fun fold(
    points: Set<Point>,
    fold: Pair<String, Int>,
): Set<Point> =
    points
        .map {
            if (fold.first == "x" && it.x > fold.second) {
                Point(it.x - 2 * (it.x - fold.second), it.y)
            } else if (fold.first == "y" && it.y > fold.second) {
                Point(it.x, it.y - 2 * (it.y - fold.second))
            } else {
                it
            }
        }.toSet()
