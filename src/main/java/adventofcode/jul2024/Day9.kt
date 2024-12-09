package adventofcode.jul2024

import adventofcode.printPart1
import adventofcode.printPart2
import java.io.File
import java.nio.file.Files.move

fun main() {
    var index = 0L
    File("src/Input.txt")
        .readText()
        .mapIndexed { id, size ->
            val result =
                if (id % 2 == 0) {
                    Triple(id / 2L, index, index + size.digitToInt() - 1)
                } else {
                    Triple(null, index, index + size.digitToInt() - 1)
                }
            index += size.digitToInt()
            result
        }.run {
            toMutableList()
                .moveFileBlocks()
                .checksum()
                .printPart1()

            toMutableList()
                .moveWholeFiles()
                .checksum()
                .printPart2()
        }
}

private fun List<Triple<Long?, Long, Long>>.checksum(): Long =
    sumOf { (id, start, end) ->
        (start..end).sumOf { it * (id ?: 0) }
    }

private fun List<Triple<Long?, Long, Long>>.canMoveFile(): Boolean = indexOfFirst { it.first == null } < indexOfLast { it.first != null }

private fun Triple<Long?, Long, Long>.size(): Long = third - second + 1

private fun MutableList<Triple<Long?, Long, Long>>.moveFileBlocks(): List<Triple<Long?, Long, Long>> {
    do {
        val diskRangeIndex = indexOfLast { it.first != null }
        val emptyRangeIndex = indexOfFirst { it.first == null }
    } while (move(diskRangeIndex, emptyRangeIndex))

    return this
}

private fun MutableList<Triple<Long?, Long, Long>>.moveWholeFiles(): MutableList<Triple<Long?, Long, Long>> {
    val maxID = maxOf { it.first ?: 0 }
    (maxID downTo 0).forEach { id ->
        val diskRange = first { it.first == id }
        val diskRangeIndex = indexOf(diskRange)
        val emptyRangeIndex = indexOfFirst { it.first == null && it.size() >= diskRange.size() }

        move(diskRangeIndex, emptyRangeIndex)
    }

    return this
}

private fun MutableList<Triple<Long?, Long, Long>>.move(
    diskRangeIndex: Int,
    emptyRangeIndex: Int,
): Boolean {
    if (emptyRangeIndex == -1 || emptyRangeIndex > diskRangeIndex) return false

    val diskRange = this[diskRangeIndex]
    val diskRangeSize = diskRange.size()

    val emptyRange = this[emptyRangeIndex]
    val emptyRangeSize = emptyRange.size()

    removeAt(diskRangeIndex)
    removeAt(emptyRangeIndex)

    when {
        emptyRangeSize > diskRangeSize -> {
            add(emptyRangeIndex, Triple(diskRange.first, emptyRange.second, emptyRange.second + diskRangeSize - 1))
            add(emptyRangeIndex + 1, emptyRange.copy(second = emptyRange.second + diskRangeSize))
        }
        emptyRangeSize < diskRangeSize -> {
            add(emptyRangeIndex, emptyRange.copy(first = diskRange.first))
            add(diskRangeIndex, diskRange.copy(third = diskRange.third - emptyRangeSize))
        }
        else -> add(emptyRangeIndex, emptyRange.copy(first = diskRange.first))
    }
    return true
}
