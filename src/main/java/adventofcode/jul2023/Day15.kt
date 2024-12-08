package adventofcode.jul2023

import java.io.File

fun main() {
    File("src/Input.txt")
        .readText()
        .split(",")
        .run {
            println("Part one: ${sumOf { it.hash() }}")
            val map = List(256) { mutableListOf<String>() }
            forEach {
                val label = it.split("[=-]".toRegex()).first()
                if (it.contains("=")) {
                    map[label.hash()].apply {
                        indexOfFirst { it.startsWith(label) }.let { i ->
                            if (i != -1) {
                                removeAt(i)
                                add(i, it)
                            } else {
                                add(it)
                            }
                        }
                    }
                }
                if (it.contains("-")) {
                    map[label.hash()].removeIf { it.startsWith(label) }
                }
                println(map.take(4))
            }
            println(
                "Part two: ${
                    map.foldIndexed(0) { box, acc, list ->
                        acc +
                            list.foldIndexed(0) { slot, a, lens ->
                                a + (box + 1) * (slot + 1) * lens.last().digitToInt()
                            }
                    }
                }",
            )
        }
}

fun String.hash(): Int = map { it.code }.fold(0) { acc, i -> (acc + i) * 17 % 256 }
