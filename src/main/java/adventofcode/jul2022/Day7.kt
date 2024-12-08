package adventofcode.jul2022

import java.io.File

private var path = mutableListOf("/")
private val directorySize = mutableMapOf("/" to 0)
private val currentDirectoryContent = mutableListOf<String>()

fun main() {
    val input = File("src/Input.txt").readLines()
    var listingDirectoryContent = false
    input.forEach { line ->
        if (listingDirectoryContent) {
            if (line.startsWith("$")) {
                addFiles(currentDirectoryContent)
                listingDirectoryContent = false
                currentDirectoryContent.clear()
            } else {
                currentDirectoryContent.add(line)
            }
        }
        if (line.startsWith("$ cd")) {
            cd(line)
        } else if (line.startsWith("$ ls")) {
            listingDirectoryContent = true
        }
    }
    if (listingDirectoryContent) {
        addFiles(currentDirectoryContent)
    }
    println("Part one: ${directorySize.filter { it.value <= 100_000 }.values.sum()}")
    println("Part two: ${directorySize.filter { it.value >= directorySize["/"]!! - 40_000_000 }.values.min()}")
}

fun addFiles(list: List<String>) {
    for (fileOrDir in list) {
        if (fileOrDir.startsWith("dir")) {
            directorySize[path.joinToString("") + fileOrDir.removePrefix("dir ")] = 0
        } else {
            for (index in path.indices) {
                val pathToDir = path.take(index + 1).joinToString("")
                directorySize[pathToDir] = directorySize[pathToDir]!! + fileOrDir.split(" ")[0].toInt()
            }
        }
    }
}

fun cd(cmd: String) {
    when (cmd) {
        "$ cd /" -> path.removeIf { it != "/" }
        "$ cd .." -> path.removeLast()
        else -> path.add(cmd.removePrefix("$ cd "))
    }
}
