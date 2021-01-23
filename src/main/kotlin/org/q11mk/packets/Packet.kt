package org.q11mk.packets

import org.q11mk.Mode
import org.q11mk.Socket

abstract class Packet (private val socket: Socket) {
    abstract val id: Int

    fun switchMode(mode: Mode) { socket.mode = mode }

    @Suppress("Unchecked_Cast")
    fun <T> getClientPacket(id: Int) = socket.packet(id) as T

    suspend fun ClientBoundPacket.respond() {
        socket.sendPacket(this)
    }
}