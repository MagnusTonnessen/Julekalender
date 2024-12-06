package adventofcode

import java.io.File
import java.time.LocalDate

fun createFiles() {
    val template =
        """
        package adventofcode.jul2024

        import java.io.File

        fun main() {
            val input = File("src/Input.txt").readLines()
            println(input)
        }
        """.trimIndent()

    for (day in 1..24) {
        val fileName = "src/main/java/adventofcode/jul2024/Day$day.kt"

        File(fileName).apply {
            parentFile.mkdirs()
            writeText(template)
        }
    }
}

fun main() {
    val year = LocalDate.now().year
    val today = LocalDate.now().dayOfMonth
    if (today in 1..24) {
        val clazz = Class.forName("adventofcode.jul$year.Day${today}Kt")
        val method = clazz.getDeclaredMethod("main")
        method.invoke(null)
    } else {
        println("Not a valid day for Advent of Code.")
    }
}
