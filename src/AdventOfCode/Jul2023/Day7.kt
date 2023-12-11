package AdventOfCode.Jul2023

import java.io.File

var withJoker = false

fun main() {
    File("src/Input")
        .readLines()
        .map { it.split(" ") }
        .run {
            sortedWith { o1, o2 -> o1.first().comparator(o2.first()) }
                .mapIndexed { index: Int, hand: List<String> -> (index + 1) * hand[1].toLong() }
                .sum()
                .let { println("Part one: $it") }
            withJoker = true
            sortedWith { o1, o2 -> o1.first().comparator(o2.first()) }
                .mapIndexed { index: Int, hand: List<String> -> (index + 1) * hand[1].toLong() }
                .sum()
                .let { println("Part two: $it") }
        }
}

private val cardValue = {
    c: Char
    ->
    when (c) {
        'A' -> 14
        'K' -> 13
        'Q' -> 12
        'J' -> if (withJoker) { 1 } else { 11 }
        'T' -> 10
        else -> c.digitToInt()
    }
}

private fun String.isFiveOfAKind(): Boolean =
    groupBy { it }.let {
        any { c -> it[c]?.size == 5 } ||
        withJoker && any { c -> c != 'J' && it[c]!!.size + (it['J']?.size ?: 0) >= 5 }
    }

private fun String.isFourOfAKind(): Boolean =
    groupBy { it }.let {
        any { c -> it[c]?.size == 4 } ||
        withJoker && any { c -> c != 'J' && it[c]!!.size + (it['J']?.size ?: 0) >= 4 }
    }

private fun String.isFullHouse(): Boolean =
    groupBy { it }.let {
        it.size == 2 && it.values.any { it.size == 3 } ||
        withJoker && it.size == 3 && !it['J'].isNullOrEmpty()
    }

private fun String.isThreeOfAKind(): Boolean =
    groupBy { it }.let {
        any { c -> it[c]?.size == 3 } ||
        withJoker && any { c -> c != 'J' && it[c]!!.size + (it['J']?.size ?: 0) >= 3 }
    }

private fun String.isTwoPairs(): Boolean =
    groupBy { it }.let {
        it.values.count { it.size >= 2 } == 2 ||
        withJoker && it['J'] != null && it.size == 4
    }
private fun String.isOnePair(): Boolean =
    groupBy { it }.size == 4 || withJoker && contains('J')

private fun String.comparator(other: String): Int =
    when {
        isFiveOfAKind() && other.isFiveOfAKind()   -> compare(other)
        isFiveOfAKind()                            -> 1
        other.isFiveOfAKind()                      -> -1

        isFourOfAKind() && other.isFourOfAKind()   -> compare(other)
        isFourOfAKind()                            -> 1
        other.isFourOfAKind()                      -> -1

        isFullHouse() && other.isFullHouse()       -> compare(other)
        isFullHouse()                              -> 1
        other.isFullHouse()                        -> -1

        isThreeOfAKind() && other.isThreeOfAKind() -> compare(other)
        isThreeOfAKind()                           -> 1
        other.isThreeOfAKind()                     -> -1

        isTwoPairs() && other.isTwoPairs()         -> compare(other)
        isTwoPairs()                               -> 1
        other.isTwoPairs()                         -> -1

        isOnePair() && other.isOnePair()           -> compare(other)
        isOnePair()                                -> 1
        other.isOnePair()                          -> -1

        else                                       -> compare(other)
    }

private fun String.compare(other: String): Int {
    forEachIndexed { index, c ->
        when {
            cardValue(c) > cardValue(other[index]) -> return 1
            cardValue(c) < cardValue(other[index]) -> return -1
        }
    }
    return 0

}
