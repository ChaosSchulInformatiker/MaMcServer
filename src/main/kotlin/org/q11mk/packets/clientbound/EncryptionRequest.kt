package org.q11mk.packets.clientbound

import org.q11mk.Socket
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import org.q11mk.packets.ClientBoundPacket

class EncryptionRequest(socket: Socket) : ClientBoundPacket(socket) {
    override val id = 0x01

    override suspend fun send(output: BytePacketBuilder) {
        val bpb = BytePacketBuilder().apply {
            this.writeByte(id.toByte())
        }
        output.writePacket(bpb.build())

        TODO("Not yet implemented")
    }
}