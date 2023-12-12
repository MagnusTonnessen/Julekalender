package AdventOfCode.Jul2023

import java.io.File
import kotlin.math.max

fun main() {
    File("src/Input")
        .readLines()
        .run {
            val op = first()
            val map = drop(2).associate {
                Pair(
                    it.substring(0..2),
                    Pair(
                        it.substring(7..9),
                        it.substring(12..14)
                    )
                )
            }
            var node = "AAA"
            var index = 0
            do {
                node = when (op[index++ % op.length]) {
                    'L'  -> map[node]!!.first
                    'R'  -> map[node]!!.second
                    else -> throw Exception()
                }
            } while (node != "ZZZ")
            println("Part one: $index")

            map.keys
                .filter { it.endsWith('A') }
                .map {
                    index = 0
                    node = it
                    do {
                        node = when (op[index++ % op.length]) {
                            'L'  -> map[node]!!.first
                            'R'  -> map[node]!!.second
                            else -> throw Exception()
                        }
                    } while (!node.endsWith('Z'))
                    index.toLong()
                }
                .reduce { acc, l -> lcm(acc, l) }
                .let { println("Part two: $it") }
        }
}

private fun lcm(a: Long, b: Long): Long {
    var lcm = max(a, b)
    while (lcm <= a * b) {
        if (lcm.mod(a) == 0L && lcm.mod(b) == 0L) {
            return lcm
        }
        lcm += max(a, b)
    }
    return a * b
}
