package AdventOfCode.Jul2023.Day4

import java.io.File
import kotlin.math.pow

fun main() {
    File("src/AdventOfCode/Jul2023/Day4/Input")
        .readLines()
        .map { it.replace("  ", " ").split(": ")[1].split(" | ").map { it.split(" ").map { it.toInt() } } }
        .run {
            map { it[0].intersect(it[1]) }
                .sumOf { 2.0.pow(it.size.dec()).toInt() }
                .let { println("Part one: $it") }
            var cards = 0
            val newCards = mapIndexed { index, card -> Pair(index, card) }.toMutableList()
            while (newCards.isNotEmpty()) {
                val (index, card) = newCards.removeFirst()
                val newCard = drop(index + 1).take(card[0].intersect(card[1]).count()).mapIndexed { i, c -> Pair(index + 1 + i, c) }
                newCards.addAll(newCard)
                cards++
            }
            println("Part two: $cards")
        }
}
