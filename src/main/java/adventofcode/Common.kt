package adventofcode

class Grid(
    private val grid: List<MutableList<Char>>,
) {
    val height: Int = grid.size
    val width: Int = grid[0].size
    val yIndices: IntRange = grid.indices
    val xIndices: IntRange = grid[0].indices

    fun sumRows(selector: (Int) -> Int) = yIndices.sumOf(selector)

    fun countCol(predicate: (Int) -> Boolean) = xIndices.count(predicate)

    fun get(
        y: Int,
        x: Int,
    ): Char = grid[y][x]

    fun isOnGrid(position: Position): Boolean = position.y in grid.indices && position.x in grid[0].indices

    fun getChar(position: Position): Char = grid[position.y][position.x]

    fun getPositionOfChar(char: Char): Position =
        Position(
            grid.indexOfFirst { it.contains(char) },
            grid[grid.indexOfFirst { it.contains(char) }].indexOf(char),
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

    companion object {
        fun List<String>.toGrid() = Grid(map { it.toMutableList() })
    }
}

class Position(
    var y: Int,
    var x: Int,
) {
    fun afterMove(direction: Direction): Position = Position(y + direction.y, x + direction.x)

    fun move(direction: Direction): Position {
        y += direction.y
        x += direction.x
        return this.copy()
    }

    fun copy(): Position = Position(y, x)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Position
        return y != other.y && x != other.x
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

    fun turnRight90(): Direction =
        when (this) {
            N -> E
            NE -> SE
            E -> S
            SE -> SW
            S -> W
            SW -> NW
            W -> N
            NW -> NE
        }

    companion object {
        val eightDirections
            get() = listOf(N, NE, E, SE, S, SW, W, NW)

        val fourDirections
            get() = listOf(E, S, W, N)

        fun String.toDir(): Direction =
            when (this) {
                "R" -> E
                "D" -> S
                "L" -> W
                "U" -> N
                else -> throw IllegalArgumentException("Invalid direction: $this")
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

fun List<List<Char>>.isOnGrid(
    y: Int,
    x: Int,
): Boolean = y in indices && x in this[0].indices

fun List<List<Char>>.isOnGrid(position: Position): Boolean = isOnGrid(position.y, position.x)

fun List<List<Char>>.getChar(position: Position): Char = this[position.y][position.x]

fun List<List<Char>>.getPositionOfChar(char: Char): Position =
    Position(
        indexOfFirst { it.contains(char) },
        this[indexOfFirst { it.contains(char) }].indexOf(char),
    )

fun List<List<Char>>.copyAndReplace(
    y: Int,
    x: Int,
    newSymbol: Char,
): List<List<Char>> {
    val grid = this.map { it.toMutableList() }
    grid[y][x] = newSymbol
    return grid
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

fun <T> T.printPart1() = println("Part 1: $this")

fun <T> T.printPart2() = println("Part 2: $this")

enum class AnsiColor(
    private val ansi: String,
) {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    ;

    override fun toString(): String = ansi
}
