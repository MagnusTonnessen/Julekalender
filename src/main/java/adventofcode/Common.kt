package adventofcode

import java.util.PriorityQueue

class Grid(
    private val grid: List<MutableList<Char>>,
    private val startSymbol: Char = 'S',
    private val endSymbol: Char = 'E',
    private val wallSymbol: Char = '#',
) {
    val height: Int = grid.size
    val width: Int = grid[0].size
    val yIndices: IntRange = grid.indices
    val xIndices: IntRange = grid[0].indices

    val positions: List<Position>
        get() = yIndices.flatMap { y -> xIndices.map { x -> Position(y, x) } }

    val positionsWithChar: List<Pair<Position, Char>>
        get() = yIndices.flatMap { y -> xIndices.map { x -> Position(y, x) to grid[y][x] } }

    fun swap(
        pos1: Position,
        pos2: Position,
    ) {
        val temp = grid[pos1.y][pos1.x]
        grid[pos1.y][pos1.x] = grid[pos2.y][pos2.x]
        grid[pos2.y][pos2.x] = temp
    }

    fun isWall(position: Position): Boolean = get(position) == wallSymbol

    val start: Position
        get() = getFirstPositionOfChar(startSymbol)

    val end: Position
        get() = getFirstPositionOfChar(endSymbol)

    fun fourNeighbours(position: Position) = Direction.fourDirections.map { position.move(it) }

    fun sumRows(selector: (Int) -> Int) = yIndices.sumOf(selector)

    fun countCol(predicate: (Int) -> Boolean) = xIndices.count(predicate)

    fun get(
        y: Int,
        x: Int,
    ): Char = grid[y][x]

    fun get(position: Position): Char = grid[position.y][position.x]

    fun getInt(position: Position): Int = grid[position.y][position.x].digitToInt()

    fun isOnGrid(position: Position): Boolean = position.y in yIndices && position.x in xIndices

    fun getChar(position: Position): Char = grid[position.y][position.x]

    fun getFirstPositionOfChar(char: Char): Position =
        Position(
            grid.indexOfFirst { it.contains(char) },
            grid[grid.indexOfFirst { it.contains(char) }].indexOf(char),
        )

    fun getLastPositionOfChar(char: Char): Position =
        Position(
            grid.indexOfLast { it.contains(char) },
            grid[grid.indexOfLast { it.contains(char) }].indexOf(char),
        )

    fun copyAndReplace(
        y: Int,
        x: Int,
        newSymbol: Char,
    ): Grid {
        val newGrid = grid.map { it.toMutableList() }
        newGrid[y][x] = newSymbol
        return Grid(newGrid)
    }

    fun dijkstra(
        startPosition: Position,
        startDirection: Direction,
    ): Map<Position, MutableMap<Direction, Long>> {
        val distances =
            positions.associateWith { Direction.fourDirections.associateWith { Long.MAX_VALUE }.toMutableMap() }
        val pq = PriorityQueue<Triple<Position, Direction, Long>>(compareBy { it.third })

        distances[startPosition]!![startDirection] = 0
        pq.add(Triple(startPosition, startDirection, 0))

        while (pq.isNotEmpty()) {
            val (position, direction, currentDistance) = pq.poll()

            if (currentDistance > distances[position]!![direction]!!) continue

            for (newDirection in setOf(direction.turnLeft90(), direction, direction.turnRight90())) {
                val newPosition = if (newDirection == direction) position.move(direction) else position
                if (!isWall(newPosition)) {
                    val newDistance = currentDistance + if (newDirection == direction) 1L else 1000L
                    if (newDistance < distances[newPosition]!![newDirection]!!) {
                        distances[newPosition]!![newDirection] = newDistance
                        pq.offer(Triple(newPosition, newDirection, newDistance))
                    }
                }
            }
        }

        return distances
    }

    override fun toString(): String = grid.joinToString("\n") { it.joinToString("") }

    fun print(overrideChars: Iterable<Pair<Position, String>> = emptyList()) {
        yIndices.forEach { y ->
            xIndices.forEach { x ->
                print(overrideChars.find { it.first == Position(y, x) }?.second ?: grid[y][x])
            }
            println()
        }
    }

    companion object {
        fun List<String>.toGrid(
            startSymbol: Char = 'S',
            endSymbol: Char = 'E',
            wallSymbol: Char = '#',
        ) = Grid(map { it.toMutableList() }, startSymbol, endSymbol, wallSymbol)
    }
}

class Position(
    val y: Int,
    val x: Int,
) {
    fun move(direction: Direction): Position = Position(y + direction.y, x + direction.x)

    fun copy(): Position = Position(y, x)

    fun mod(
        height: Int,
        width: Int,
    ) = Position(y.mod(height), x.mod(width))

    fun opposite(other: Position) = Position(y - (other.y - y), x - (other.x - x))

    fun plus(other: Position) = Position(y + other.y, x + other.x)

    fun minus(other: Position) = Position(y - other.y, x - other.x)

    override fun toString(): String = "($y, $x)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Position
        return y == other.y && x == other.x
    }

    override fun hashCode(): Int {
        var result = y
        result = 31 * result + x
        return result
    }
}

enum class Direction(
    val y: Int,
    val x: Int,
) {
    E(0, 1),
    S(1, 0),
    W(0, -1),
    N(-1, 0),
    NW(-1, -1),
    NE(-1, 1),
    SW(1, -1),
    SE(1, 1),
    ;

    fun turnRight90(): Direction = eightDirections[(eightDirections.indexOf(this) + 2).mod(8)]

    fun turnLeft90(): Direction = eightDirections[(eightDirections.indexOf(this) - 2).mod(8)]

    fun turn180(): Direction = eightDirections[(eightDirections.indexOf(this) + 4).mod(8)]

    companion object {
        val eightDirections
            get() = listOf(N, NE, E, SE, S, SW, W, NW)

        val fourDirections
            get() = listOf(N, E, S, W)

        fun Char.toDir(): Direction =
            when (this) {
                '^', 'U' -> N
                '>', 'R' -> E
                'v', 'D' -> S
                '<', 'L' -> W
                else -> error("Invalid direction: $this")
            }

        fun Char.idxToDir(): Direction =
            when (this) {
                '0' -> E
                '1' -> S
                '2' -> W
                '3' -> N
                else -> throw IllegalArgumentException("Invalid direction index: $this")
            }
    }
}

fun gcd(
    a: Long,
    b: Long,
): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(
    a: Long,
    b: Long,
): Long = (a * b) / gcd(a, b)

fun Collection<Long>.lcm(): Long = reduce { acc, num -> lcm(acc, num) }

fun List<String>.transpose(): List<String> = this.indices.map { i -> this.indices.map { j -> this[j][i] }.joinToString("") }

fun <T> Collection<T>.combinations(size: Int): List<List<T>> =
    if (size == 1) {
        this.map { listOf(it) }
    } else {
        this.flatMapIndexed { index, t -> this.drop(index + 1).combinations(size - 1).map { listOf(t) + it } }
    }

fun <T> T.printPart1() = println("Part 1: $this")

fun <T> T.printPart2() = println("Part 2: $this")
