package adventofcode.jul2023

import adventofcode.lcm
import java.io.File

val regex = """([&%]?)(\w+) -> (.*)""".toRegex()
var highPulsesSent = 0L
var lowPulsesSent = 0L
var timesPushed = 0L

val cycles = mutableMapOf<String, Long>()

fun main() {
    var modules = File("src/Input.txt").readLines().parseModules()

    repeat(1000) {
        modules.pushButton()
    }

    println("Part one: ${highPulsesSent * lowPulsesSent}")

    modules = File("src/Input.txt").readLines().parseModules()

    do {
        timesPushed++
    } while (modules.pushButton())

    println("Part two: ${cycles.values.lcm()}")
}

fun parseModule(module: String): Pair<String, Module> {
    val (type, name, destinations) = regex.matchEntire(module)!!.destructured
    val sendsHighPulse = type == "&"
    return name to Module(name, type.firstOrNull(), sendsHighPulse, destinations.split(", "))
}

fun List<String>.parseModules(): Map<String, Module> {
    val modules = associate(::parseModule).plus("button" to Module("button", null, false, listOf("broadcaster")))
    modules.forEach { (_, module) ->
        module.destinations.forEach { destination ->
            modules[destination]?.incoming?.add(module)
        }
    }
    return modules
}

fun Map<String, Module>.pushButton(): Boolean {
    val queue = mutableListOf(this["button"]!!)
    while (queue.isNotEmpty()) {
        val module = queue.removeFirst()
        when (module.sendsHighPulse) {
            true -> highPulsesSent += module.destinations.size
            false -> lowPulsesSent += module.destinations.size
        }

        if (module.sendsHighPulse) {
            when (module.name) {
                "vm" -> cycles.putIfAbsent("vm", timesPushed)
                "vb" -> cycles.putIfAbsent("vb", timesPushed)
                "kv" -> cycles.putIfAbsent("kv", timesPushed)
                "kl" -> cycles.putIfAbsent("kl", timesPushed)
            }
        }

        queue.addAll(module.sendPulse(this))
    }

    return cycles.size < 4
}

fun Module.sendPulse(modules: Map<String, Module>): List<Module> {
    val addToQueue = mutableListOf<Module>()
    destinations.forEach { destination ->
        val module = modules[destination]
        if (module != null) {
            val newPulseIsHigh =
                when (module.type) {
                    '&' -> module.incoming.any { !it.sendsHighPulse }
                    '%' -> if (sendsHighPulse) module.sendsHighPulse else !module.sendsHighPulse
                    else -> sendsHighPulse
                }
            if (module.type != '%' || module.sendsHighPulse != newPulseIsHigh) {
                addToQueue.add(module)
            }
            module.sendsHighPulse = newPulseIsHigh
        }
    }
    return addToQueue
}

data class Module(
    val name: String,
    val type: Char?,
    var sendsHighPulse: Boolean,
    val destinations: List<String>,
    val incoming: MutableList<Module> = mutableListOf(),
)
