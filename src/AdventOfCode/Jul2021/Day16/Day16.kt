package AdventOfCode.Jul2021.Day16

import java.io.File

fun main() {
    val input = File("src/AdventOfCode/Jul2021/Day16/Input").readLines()[0]
    val packet = Packet(
        input
            .map {
                it
                    .digitToInt(16)
                    .toString(2)
                    .padStart(4, '0')
            }
            .joinToString("")
    )
    packet.print()
    println()
    println("Part one: ${packet.versionSum()}")
    println("Part two: ${packet.findValue()}")
}

class Packet(packet: String) {
    private var subPackets: List<Packet> = listOf()
    private var value: Long
    private var version: Int
    private var typeID: Int
    private var packetLength: Int

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
        print("")
    }

    private fun print(pad: String) {
        println("${pad.dropLast(1)}|")
        if (subPackets.isEmpty()) {
            println("${pad.dropLast(1)}|---Version: $version, type id: $typeID, value: $value")
        } else {
            println("${pad.dropLast(1)}|---Version: $version, type id: $typeID")
            subPackets.dropLast(1).forEach { it.print("$pad   |") }
            subPackets.last().print("$pad    ")
        }
    }
}
