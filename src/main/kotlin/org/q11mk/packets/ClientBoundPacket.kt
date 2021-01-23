package org.q11mk.packets

import org.q11mk.Socket
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*

abstract class ClientBoundPacket(socket: Socket) : Packet(socket) {
    abstract fun send(output: BytePacketBuilder)
}