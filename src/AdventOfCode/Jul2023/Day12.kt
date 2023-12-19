package AdventOfCode.Jul2023

import java.io.File
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

val progressCount = AtomicInteger(0)

fun main() {
    File("src/Input")
        .readLines()
        .map { it.split(" ") }
        .map { Pair(it.first(), it.last().split(",").map { it.toInt() }) }
        .run {
            println(
                "Part one: ${sumOf { (line, pattern) -> generateCombinations(line, pattern).count { it.isValid(pattern) } }}"
            )
            // Holy fudge, amazingly bad solution, but cool progress bar though
            runBlocking {
                coroutineScope {
                    map { (line, pattern) ->
                        async(Dispatchers.Default) {
                            generateCombinations(
                                List(5) { line }.joinToString("?"),
                                List(5) { pattern }.flatten()
                            ).count { it.isValid(List(5) { pattern }.flatten()) }.also {
                                progressCount.incrementAndGet().also {
                                    System.out.flush()
                                    print("\r▕${"█".repeat(it * 100 / size)}${" ".repeat(100 - it * 100 / size)}▏ ${it.toFloat().div(size).times(100)} %")
                                }
                            }
                        }
                    }
                    .sumOf { it.await() }
                    .let { println("Part two: $it") }
                }
            }
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
