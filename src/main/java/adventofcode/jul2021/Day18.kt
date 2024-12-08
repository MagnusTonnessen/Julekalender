package adventofcode.jul2021

import java.io.File
import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    val input = File("src/Input.txt").readLines()
    var number = input.first()
    for (num in input.drop(1)) {
        number = reduce("[$number,$num]")
    }
    println("Part one: ${magnitude(number)}")
    var max = 0L
    for (index1 in 0 until input.lastIndex) {
        for (index2 in index1 + 1 until input.size) {
            max =
                maxOf(
                    max,
                    magnitude(reduce("[${input[index1]},${input[index2]}]")),
                    magnitude(reduce("[${input[index2]},${input[index1]}]")),
                )
        }
    }
    println("Part two: $max")
}

fun magnitude(number: String): Long =
    if (number.first().isDigit()) {
        number.toLong()
    } else {
        val first = getFirstNumber(number.drop(1))
        val second = number.drop(2 + first.length).dropLast(1)
        magnitude(first) * 3 + magnitude(second) * 2
    }

fun getFirstNumber(number: String): String {
    if (number[0].isDigit()) {
        return number.takeWhile { it.isDigit() }
    }
    var leftBracket = 0
    var rightBracket = 0
    var subNumber = ""
    var index = 0
    do {
        subNumber += number[index]
        if (number[index] == '[') {
            leftBracket++
        }
        if (number[index] == ']') {
            rightBracket++
        }
        index++
    } while (leftBracket > rightBracket)
    return subNumber
}

fun reduce(number: String): String {
    var newNumber = number
    loop@ while (true) {
        var depth = 0
        for (index in newNumber.indices) {
            if (newNumber[index] == '[') {
                depth++
            }
            if (newNumber[index] == ']') {
                depth--
            }
            if (depth == 5) {
                newNumber = explode(newNumber, index)
                continue@loop
            }
        }
        for (index in 0 until newNumber.lastIndex) {
            if (newNumber[index].isDigit() && newNumber[index + 1].isDigit()) {
                newNumber = split(newNumber, index)
                continue@loop
            }
        }
        return newNumber
    }
}

fun split(
    number: String,
    index: Int,
): String = number.take(index) + splitNumber(number.substring(index, index + 2)) + number.drop(index + 2)

fun splitNumber(number: String): String = "[${floor(number.toInt() / 2.0).toInt()},${ceil(number.toInt() / 2.0).toInt()}]"

fun explode(
    number: String,
    index: Int,
): String {
    val explode = number.drop(index + 1).takeWhile { it != ']' }
    val leftValue = explode.split(",")[0].toInt()
    val rightValue = explode.split(",")[1].toInt()
    return increaseLeftSide(number.substring(0, index), leftValue) + 0 +
        increaseRightSide(number.substring(index + explode.length + 2), rightValue)
}

fun increaseLeftSide(
    string: String,
    value: Int,
): String {
    val suffix = string.takeLastWhile { !it.isDigit() }
    val number = string.dropLastWhile { !it.isDigit() }.takeLastWhile { it.isDigit() }
    val prefix = string.dropLastWhile { !it.isDigit() }.dropLastWhile { it.isDigit() }
    return "$prefix${if (number.isBlank()) "" else number.toInt() + value}$suffix"
}

fun increaseRightSide(
    string: String,
    value: Int,
): String {
    val prefix = string.takeWhile { !it.isDigit() }
    val number = string.dropWhile { !it.isDigit() }.takeWhile { it.isDigit() }
    val suffix = string.dropWhile { !it.isDigit() }.dropWhile { it.isDigit() }
    return "$prefix${if (number.isBlank()) "" else number.toInt() + value}$suffix"
}
