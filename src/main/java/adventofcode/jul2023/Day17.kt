package adventofcode.jul2023

import adventofcode.Direction
import adventofcode.Direction.E
import adventofcode.Direction.N
import adventofcode.Direction.S
import adventofcode.Direction.W
import java.io.File
import java.util.PriorityQueue

fun main() {
    val grid = File("src/Input.txt").readLines().map { it.toCharArray().map { it.digitToInt() } }

    println("Part one: ${grid.dijkstra()}")
    println("Part two: ${grid.dijkstra(4, 10)}")
}

data class State(
    val heatLoss: Int,
    val y: Int,
    val x: Int,
    val direction: Direction,
    val consecutiveMoves: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is State) return false

        if (y != other.y) return false
        if (x != other.x) return false
        if (direction != other.direction) return false
        if (consecutiveMoves != other.consecutiveMoves) return false

        return true
    }

    override fun hashCode(): Int {
        var result = y
        result = 31 * result + x
        result = 31 * result + direction.hashCode()
        result = 31 * result + consecutiveMoves
        return result
    }
}

private fun Direction.validDirections(): List<Direction> =
    when (this) {
        E -> listOf(N, E, S)
        S -> listOf(E, S, W)
        W -> listOf(N, S, W)
        N -> listOf(N, E, W)
        else -> throw IllegalArgumentException("Illegal direction $this")
    }

fun List<List<Int>>.dijkstra(
    minConsecutiveMoves: Int = 1,
    maxConsecutiveMoves: Int = 3,
): Int {
    val visited = mutableSetOf<State>()
    val pq = PriorityQueue<State>(compareBy { it.heatLoss })
    pq.add(State(0, 0, 0, E, 0))
    pq.add(State(0, 0, 0, S, 0))

    while (pq.isNotEmpty()) {
        val state = pq.poll()
        if (visited.contains(state)) continue
        visited.add(state)
        val (heatLoss, y, x, direction, consecutiveMoves) = state

        if (y == lastIndex && x == first().lastIndex && consecutiveMoves >= minConsecutiveMoves) {
            return heatLoss
        }

        for (dir in direction.validDirections()) {
            if (consecutiveMoves < minConsecutiveMoves && dir != direction) {
                continue
            }
            val newY = y + dir.y
            val newX = x + dir.x
            val newConsecutiveMoves = if (dir == direction) consecutiveMoves + 1 else 1
            if (newY in indices && newX in first().indices && newConsecutiveMoves <= maxConsecutiveMoves) {
                pq.add(State(heatLoss + this[newY][newX], newY, newX, dir, newConsecutiveMoves))
            }
        }
    }
    return -1
}
