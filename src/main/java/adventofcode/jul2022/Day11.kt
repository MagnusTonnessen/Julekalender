package adventofcode.jul2022

import java.io.File

fun main() {
    val monkeys =
        File("src/Input.txt")
            .readLines()
            .map { it.trim() }
            .chunked(7)
            .map { input ->
                val monkey =
                    Monkey(
                        input[1]
                            .substring(16)
                            .split(", ")
                            .map { it.toLong() }
                            .toMutableList(),
                        input[2].substring(21).split(" "),
                        input[3].substring(19).toLong(),
                        input[4].substring(25).toInt(),
                        input[5].substring(26).toInt(),
                    )
                Pair(monkey, monkey.clone())
            }

    val mod = monkeys.fold(1L) { acc, pair -> acc * pair.second.test }

    for (round in 0 until 10000) {
        for (monkey in monkeys) {
            for (item in monkey.first.processItemsPart1()) {
                monkeys[item.first].first.items.add(item.second)
            }
            monkey.first.items.clear()
            for (item in monkey.second.processItemsPart2(mod)) {
                monkeys[item.first].second.items.add(item.second)
            }
            monkey.second.items.clear()
        }
        if (round == 20) {
            println("Part one: ${monkeys.map { it.first.inspected }.sortedDescending().take(2).reduce(Long::times)}")
        }
    }

    println("Part two: ${monkeys.map { it.second.inspected }.sortedDescending().take(2).reduce(Long::times)}")
}

class Monkey(
    val items: MutableList<Long>,
    private val op: List<String>,
    val test: Long,
    private val ifTrue: Int,
    private val ifFalse: Int,
    var inspected: Long = 0,
) {
    fun processItemsPart1(): List<Pair<Int, Long>> {
        inspected += items.size
        return items.map {
            val worryLevel = nextWorryLevel(it) / 3
            Pair(nextMonkey(worryLevel), worryLevel)
        }
    }

    fun processItemsPart2(mod: Long): List<Pair<Int, Long>> {
        inspected += items.size
        return items.map {
            val worryLevel = nextWorryLevel(it) % mod
            Pair(nextMonkey(worryLevel), worryLevel)
        }
    }

    private fun nextWorryLevel(item: Long): Long {
        val value = if (op[1] == "old") item else op[1].toLong()
        return if (op[0] == "+") item + value else item * value
    }

    private fun nextMonkey(item: Long): Int = if (item % test == 0L) ifTrue else ifFalse

    fun clone(): Monkey = Monkey(items.toMutableList(), op, test, ifTrue, ifFalse, inspected)
}
