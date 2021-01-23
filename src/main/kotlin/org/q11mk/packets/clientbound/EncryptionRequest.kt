package org.q11mk.packets.clientbound

import io.ktor.utils.io.core.*
import org.q11mk.*
import org.q11mk.packets.ClientBoundPacket

class EncryptionRequest(socket: Socket) : ClientBoundPacket(socket) {
    override val id = 0x01

    override fun send(output: BytePacketBuilder) {
        output.writeString("")
        output.writeVarInt(VarInt(4))
        output.writeByteArray(byteArrayOf(0, 1, 2, 3))
        output.writeVarInt(VarInt(4))
        output.writeByteArray(byteArrayOf(5, 7, 24, -88))
    }
}