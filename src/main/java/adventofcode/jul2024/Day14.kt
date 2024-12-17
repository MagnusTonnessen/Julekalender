package adventofcode.jul2024

import adventofcode.Position
import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File

fun main() {
    val height = 103
    val width = 101

    File("src/Input.txt")
        .readLines()
        .map {
            Regex("-?\\d+")
                .findAll(it)
                .map { it.value.toInt() }
                .toList()
                .let { (x, y, vx, vy) ->
                    Position(y, x) to Position(vy, vx)
                }
        }.run {
            var robots = this
            repeat(100) {
                robots = robots.map { it.first.plus(it.second).mod(height, width) to it.second }
            }

            robots
                .map { it.first }
                .run {
                    count { it.y < height / 2 && it.x < width / 2 }.toLong() *
                        count { it.y < height / 2 && it.x > width / 2 }.toLong() *
                        count { it.y > height / 2 && it.x < width / 2 }.toLong() *
                        count { it.y > height / 2 && it.x > width / 2 }.toLong()
                }.printPart1()

            robots = this
            var seconds = 1
            while (true) {
                robots = robots.map { it.first.plus(it.second).mod(height, width) to it.second }
                val tenDiagonalRobots =
                    robots
                        .map { it.first }
                        .run {
                            any { robot ->
                                (1..9).all { contains(Position(robot.y + it, robot.x + it)) }
                            }
                        }

                if (tenDiagonalRobots) {
                    seconds.printPart2()
                    break
                }
                seconds++
            }
        }
}
