package Knowit.Jul2021.Day4

import java.math.BigInteger

fun main() {
    var x = BigInteger.ZERO
    var y = BigInteger.ZERO
    val three = BigInteger("3")
    val five = BigInteger("5")
    val steps = BigInteger("100000000000000000079")
    var currStep = BigInteger.ZERO
    var north = true
    while (currStep < steps) {
        if (north) {
            y++
            if (y.mod(three).toInt() == 0 && y.mod(five).toInt() != 0) {
                north = false
            }
        } else {
            x++
            if (x.mod(five).toInt() == 0 && x.mod(three).toInt() != 0) {
                north = true
            }
        }
        currStep++
        if (currStep.mod(BigInteger("10000000")).toLong() == 0L) {
            println(currStep)
        }
    }
    println("$x,$y")
}
