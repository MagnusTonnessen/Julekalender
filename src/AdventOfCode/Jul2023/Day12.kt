package AdventOfCode.Jul2023

import java.io.File
import java.util.concurrent.atomic.AtomicInteger

val progressCount = AtomicInteger(0)

fun main() {
    File("src/Input")
        .readLines()
        .map { it.split(" ") }
        .map { Pair(it.first(), it.last().split(",").map { it.toInt() }) }
        .run {
            println(
                "Part one: ${
                    sumOf { (line, pattern) ->
                        generateCombinations(
                            line,
                            pattern
                        ).count { it.isValid(pattern) }
                    }
                }"
            )
            /*
            map { (line, pattern) ->
                generateCombinations(
                    List(5) { line }.joinToString("?"),
                    List(5) { pattern }.flatten()
                ).count { it.isValid(List(5) { pattern }.flatten()) }.also {
                    progressCount.incrementAndGet().also {
                        System.out.flush()
                        print(
                            "\r▕${"█".repeat(it * 100 / size)}${" ".repeat(100 - it * 100 / size)}▏ ${
                                it.toFloat().div(size).times(100)
                            } %"
                        )
                    }
                }
            }
            */
            sumOf { (line, pattern) ->
                findVariations(List(5) { line }.joinToString("?"),
                    List(5) { pattern }.flatten()
                )
            }
                .let { println("Part two: $it") }
        }
}

private fun String.isValid(pattern: List<Int>): Boolean =
    !contains('?') && trim('.').split("\\.+".toRegex()).map { it.length } == pattern

private fun String.isPartlyValid(pattern: List<Int>): Boolean =
    takeWhile { it != '?' }.dropLastWhile { it != '.' }.trim('.').let {
        it.isEmpty() || it.split("\\.+".toRegex()).map { it.length }.zip(pattern)
            .all { (a, b) -> a == b }
    }

private fun generateCombinations(
    input: String,
    pattern: List<Int>,
    index: Int = 0,
    current: String = ""
): List<String> =
    when {
        !current.isPartlyValid(pattern) -> emptyList()
        index == input.length           -> listOf(current)
        input[index] == '?'             -> generateCombinations(input, pattern, index + 1, "$current.") +
                generateCombinations(input, pattern, index + 1, "$current#")

        else                            -> generateCombinations(input, pattern, index + 1, current + input[index])
    }

private val findVariationsMemo = Memo<Pair<String, List<Int>>, Long>()
private fun findVariations(line: String, controlNumbers: List<Int>): Long =
    findVariationsMemo.memoize(line to controlNumbers) {
        if (controlNumbers.isEmpty()) {
            val brokenSprings = line.count { it == '#' }
            return@memoize if (brokenSprings > 0) 0L else 1L
        }

        if (line.isEmpty()) return@memoize 0L

        if (line.length < controlNumbers.sum() + controlNumbers.size - 1) return@memoize 0L

        val head = controlNumbers.first()
        val tail = controlNumbers.drop(1)

        var segment = ""
        var i = 0
        while (i < line.length) {
            when (line[i++]) {
                '#' -> segment += '#'

                '?' -> {
                    val restOfLine = line.substring(segment.length + 1)
                    return@memoize listOf(
                        findVariations("$segment.$restOfLine", controlNumbers),
                        findVariations("$segment#$restOfLine", controlNumbers),
                    ).sum()
                }

                '.' -> {
                    segment += '.'
                    val possibleMatch = segment.dropWhile { it == '.' }.takeWhile { it == '#' }
                    if (possibleMatch.length == head) {
                        return@memoize findVariations(line.substringAfter(segment), tail)
                    } else if (possibleMatch.isNotEmpty()) {
                        return@memoize 0L
                    }
                }
            }

            val restOfLine = line.substring(i)
            if (restOfLine.length < tail.sum() + tail.size - 1) {
                return@memoize 0L
            }


            if (segment.count { it == '#' } > head) {
                return@memoize 0L
            }

            if (segment.contains("#.#")) {
                return@memoize 0L
            }
        }

        if (segment.count { it == '#' } == head && segment.contains("#".repeat(head))) {
            return@memoize findVariations(line.substringAfter(segment), tail)
        }

        0L
    }

class Memo<T, R : Any> {

    private val values = mutableMapOf<T, R>()

    fun memoize(cacheKey: T, fn: () -> R): R {
        return values.getOrPut(cacheKey) { fn() }
    }
}