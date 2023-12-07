package AdventOfCode.Jul2021

import java.io.File

fun main() {
    val input = File("src/Input").readLines()
    val word = input[0]
    val rules =
        input.drop(2).associate { Pair(Pair(it.split(" -> ")[0][0], it.split(" -> ")[0][1]), it.split(" -> ")[1][0]) }
    val pairCount = rules.keys.associateWith { 0L }.toMutableMap()
    val charCount = word.groupBy { it }.map { Pair(it.key, it.value.size.toLong()) }.toMap().toMutableMap()

    word.zip(word.substring(1)).forEach { pairCount[it] = pairCount[it]!! + 1 }

    for (step in 1..40) {
        for ((pair, count) in pairCount.toMap()) {
            val letter = rules[pair]!!
            pairCount[pair] = pairCount[pair]!! - count
            pairCount[Pair(pair.first, letter)] = pairCount[Pair(pair.first, letter)]!! + count
            pairCount[Pair(letter, pair.second)] = pairCount[Pair(letter, pair.second)]!! + count
            charCount[letter] = (charCount[letter] ?: 0) + count
        }
        if (step == 10) {
            println("Part one: ${charCount.maxOf { it.value } - charCount.minOf { it.value }}")
        }
    }

    println("Part two: ${charCount.maxOf { it.value } - charCount.minOf { it.value }}")
}
