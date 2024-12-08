package adventofcode.jul2023

import java.io.File
import java.math.BigInteger

fun main() {
    val (parts, workflows) =
        File("src/Input.txt")
            .readLines()
            .filter { it.isNotEmpty() }
            .partition { it.startsWith("{") }
            .let {
                it.first.map(::parsePart) to it.second.associate(::parseWorkflow)
            }

    println("Part one: ${parts.filter { workflows.isValidRange(it) }.sumOf { it.sum() }}")

    val fullRange = Range(1..4000, 1..4000, 1..4000, 1..4000)

    println("Part two: ${workflows.findValidRanges(fullRange).map { it.product() }.sumOf { it }}")
}

data class Rule(
    val splitRange: (Range) -> Pair<Range, Range>,
    val destination: String,
)

data class Range(
    val x: IntRange,
    val m: IntRange,
    val a: IntRange,
    val s: IntRange,
) {
    fun sum(): Int = x.last + m.last + a.last + s.last

    fun product(): BigInteger = x.count().toBigInteger() * m.count().toBigInteger() * a.count().toBigInteger() * s.count().toBigInteger()
}

fun parsePart(line: String): Range =
    line
        .drop(1)
        .dropLast(1)
        .split(",")
        .map { it.filter { it.isDigit() }.toInt() }
        .let { (x, m, a, s) -> Range(x..x, m..m, a..a, s..s) }

fun parseRule(condition: String): (Range) -> Pair<Range, Range> {
    val (attr, op, value) = """(\w)([<>]+)(\d+)""".toRegex().find(condition)!!.destructured
    val num = value.toInt()

    val obeyRule =
        when (op) {
            "<" -> { r: IntRange -> r.first..<num }
            ">" -> { r: IntRange -> num + 1..r.last }
            else -> throw IllegalArgumentException("Unknown operator $op")
        }

    val disobeyRule =
        when (op) {
            "<" -> { r: IntRange -> num..r.last }
            ">" -> { r: IntRange -> r.first..num }
            else -> throw IllegalArgumentException("Unknown operator $op")
        }

    return when (attr) {
        "x" -> { range -> range.copy(x = obeyRule(range.x)) to range.copy(x = disobeyRule(range.x)) }
        "m" -> { range -> range.copy(m = obeyRule(range.m)) to range.copy(m = disobeyRule(range.m)) }
        "a" -> { range -> range.copy(a = obeyRule(range.a)) to range.copy(a = disobeyRule(range.a)) }
        "s" -> { range -> range.copy(s = obeyRule(range.s)) to range.copy(s = disobeyRule(range.s)) }
        else -> throw IllegalArgumentException("Unknown attribute $attr")
    }
}

fun parseWorkflow(line: String): Pair<String, List<Rule>> {
    val (name, rawRules) = """(\w+)\{(.+)}""".toRegex().matchEntire(line)!!.destructured
    val rules =
        rawRules.split(",").map { ruleStr ->
            val (condition, _, destination) = """(\w[<>]\d+)?(:)?(\w+)""".toRegex().matchEntire(ruleStr)!!.destructured
            when {
                condition.isEmpty() -> Rule({ it to it }, destination)
                else -> Rule(parseRule(condition), destination)
            }
        }
    return name to rules
}

fun Map<String, List<Rule>>.findValidRanges(
    range: Range,
    workflowName: String = "in",
): List<Range> =
    this[workflowName]!!
        .fold(range to listOf<Range>()) { (currentRange, validRanges), rule ->
            val (validRange, invalidRange) = rule.splitRange(currentRange)

            invalidRange to validRanges +
                when (rule.destination) {
                    "A" -> listOf(validRange)
                    "R" -> emptyList()
                    else -> findValidRanges(validRange, rule.destination)
                }
        }.second

fun Map<String, List<Rule>>.isValidRange(
    part: Range,
    workflowName: String = "in",
): Boolean =
    this[workflowName]!!.first { it.splitRange(part).first.isValid() }.let {
        when (it.destination) {
            "A" -> true
            "R" -> false
            else -> isValidRange(part, it.destination)
        }
    }

fun Range.isValid(): Boolean = !(x.isEmpty() || m.isEmpty() || a.isEmpty() || s.isEmpty())
