package org.q11mk.packets

import org.q11mk.Socket
import io.ktor.utils.io.*

abstract class ClientBoundPacket(socket: Socket) : Packet(socket) {
    abstract suspend fun send(output: ByteWriteChannel)
}