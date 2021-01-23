package org.q11mk.packets.clientbound

import org.q11mk.Socket
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import org.q11mk.packets.ClientBoundPacket
import org.q11mk.writeString

class Disconnect_Login(socket: Socket) : ClientBoundPacket(socket) {
    override val id = 0x00

    override fun send(output: BytePacketBuilder) {
        output.writeString("{}")
    }
}