package AdventOfCode.Jul2021.Day16

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day16/Input").readLines()[0]
    val packet = Packet(input.map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString(""))
    packet.print()
    println()
    println("Part one: ${packet.versionSum()}")
    println("Part two: ${packet.findValue()}")
}

class Packet(packet: String) {
    private val subPackets: List<Packet>
    private val value: Long
    private val version: Int
    private val typeID: Int
    private val packetLength: Int

    init {
        version = packet.substring(0, 3).toInt(2)
        typeID = packet.substring(3, 6).toInt(2)
        if (typeID == 4) {
            var num = ""
            var prefix = '1'
            var index = 6
            while (prefix != '0') {
                prefix = packet[index]
                num += packet.substring(index + 1, index + 5)
                index += 5
            }
            packetLength = index
            value = num.toLong(2)
            subPackets = listOf()
        } else {
            if (packet[6] == '0') {
                subPackets = parsePacketLength(packet.substring(22), packet.substring(7, 22).toInt(2))
                packetLength = subPackets.sumOf { it.packetLength } + 22
            } else {
                subPackets = parsePacketNum(packet.substring(18), packet.substring(7, 18).toInt(2))
                packetLength = subPackets.sumOf { it.packetLength } + 18
            }
            value = findValue()
        }
    }

    fun findValue(): Long {
        return when (typeID) {
            0 -> subPackets.sumOf { it.findValue() }
            1 -> subPackets.fold(1) { acc, subPacket -> subPacket.findValue() * acc }
            2 -> subPackets.minOf { it.findValue() }
            3 -> subPackets.maxOf { it.findValue() }
            4 -> value
            5 -> if (subPackets[0].findValue() > subPackets[1].findValue()) 1 else 0
            6 -> if (subPackets[0].findValue() < subPackets[1].findValue()) 1 else 0
            7 -> if (subPackets[0].findValue() == subPackets[1].findValue()) 1 else 0
            else -> error("Fuck this shit")
        }
    }

    private fun parsePacketLength(packet: String, length: Int): List<Packet> {
        val subPac = mutableListOf<Packet>()
        var index = 0
        while (index < length) {
            val subPacket = Packet(packet.substring(index))
            subPac.add(subPacket)
            index += subPacket.packetLength
        }
        return subPac
    }

    private fun parsePacketNum(packets: String, num: Int): List<Packet> {
        val subPac = mutableListOf<Packet>()
        var index = 0
        for (n in 0 until num) {
            val subPacket = Packet(packets.substring(index))
            subPac.add(subPacket)
            index += subPacket.packetLength
        }
        return subPac
    }

    fun versionSum(): Int {
        return version + subPackets.sumOf { it.versionSum() }
    }

    fun print() {
        print(" ", true)
    }

    private fun print(pad: String, last: Boolean) {
        val ve = "Version: $version"
        val ty = "Type id: $typeID"
        val va = "Value: $value"
        println("${pad.dropLast(1)}┃")
        println("${pad.dropLast(1)}┃  $ve")
        println(
            "${pad.dropLast(1)}${if (last) "┗" else "┣"}━ $ty${
                if (subPackets.isNotEmpty()) " ".repeat(
                    maxOf(
                        0,
                        va.length - ty.length
                    ) + 1
                ) + "━┓" else ""
            }"
        )
        println(
            "${pad.dropLast(1)}${if (last) " " else "┃"}  $va${
                if (subPackets.isNotEmpty()) " ".repeat(
                    maxOf(
                        0,
                        ty.length - va.length
                    ) + 2
                ) + "┃" else ""
            }"
        )
        if (subPackets.isNotEmpty()) {
            subPackets.dropLast(1).forEach { it.print("$pad${" ".repeat(maxOf(ty.length, va.length) + 4)}┃", false) }
            subPackets.last().print("$pad${" ".repeat(maxOf(ty.length, va.length) + 5)}", true)
        }
    }
}
