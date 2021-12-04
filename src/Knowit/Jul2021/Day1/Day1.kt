package Knowit.Jul2021.Day1

import java.io.File

fun main() {
    println(File("src/Knowit/Jul2021/Day1/Input").readLines()[0].split(" ").sumOf { it.toInt() })
}