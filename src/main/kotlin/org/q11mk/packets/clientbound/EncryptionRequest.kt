package org.q11mk.packets.clientbound

import org.q11mk.Socket
import io.ktor.utils.io.*
import org.q11mk.packets.ClientBoundPacket

class EncryptionRequest(socket: Socket) : ClientBoundPacket(socket) {
    override val id = 0x01

    override suspend fun send(output: ByteWriteChannel) {
        TODO("Not yet implemented")
    }
}