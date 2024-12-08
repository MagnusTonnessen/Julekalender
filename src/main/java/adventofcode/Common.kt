package adventofcode

class Grid(
    private val grid: List<MutableList<Char>>,
) {
    val height: Int = grid.size
    val width: Int = grid[0].size
    val yIndices: IntRange = grid.indices
    val xIndices: IntRange = grid[0].indices

    fun positions(): Sequence<Position> = yIndices.flatMap { y -> xIndices.map { x -> Position(y, x) } }.asSequence()

    fun sumRows(selector: (Int) -> Int) = yIndices.sumOf(selector)

    fun countCol(predicate: (Int) -> Boolean) = xIndices.count(predicate)

    fun get(
        y: Int,
        x: Int,
    ): Char = grid[y][x]

    fun get(position: Position): Char = grid[position.y][position.x]

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

    fun print(overrideChars: List<Pair<Position, Char>> = emptyList()) {
        yIndices.forEach { y ->
            xIndices.forEach { x ->
                print(overrideChars.find { it.first == Position(y, x) }?.second ?: grid[y][x])
            }
            println()
        }
    }

    companion object {
        fun List<String>.toGrid() = Grid(map { it.toMutableList() })
    }
}

class Position(
    var y: Int,
    var x: Int,
) {
    fun move(direction: Direction): Position = Position(y + direction.y, x + direction.x)

    fun copy(): Position = Position(y, x)

    fun mod(grid: Grid) = Position(y.mod(grid.height), x.mod(grid.width))

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

    fun turnRight90(): Direction = eightDirections[(eightDirections.indexOf(this) + 2) % 8]

    companion object {
        val eightDirections
            get() = listOf(N, NE, E, SE, S, SW, W, NW)

        val fourDirections
            get() = listOf(E, S, W, N)

        fun Char.toDir(): Direction =
            when (this) {
                '>', 'R' -> E
                'v', 'D' -> S
                '<', 'L' -> W
                '^', 'U' -> N
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
