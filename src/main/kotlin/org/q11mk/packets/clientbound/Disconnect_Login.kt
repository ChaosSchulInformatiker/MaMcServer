package org.q11mk.packets.clientbound

import org.q11mk.Socket
import io.ktor.utils.io.*
import org.q11mk.packets.ClientBoundPacket

class Disconnect_Login(socket: Socket) : ClientBoundPacket(socket) {
    override val id = 0x00

    override suspend fun send(output: ByteWriteChannel) {
        output.writeByte(1)
        output.writeByte(id)
    }
}