package org.q11mk.packets.clientbound

import org.q11mk.Socket
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import org.q11mk.packets.ClientBoundPacket
import org.q11mk.writeString
import org.q11mk.writeUUID
import java.util.*

class LoginSuccess(socket: Socket) : ClientBoundPacket(socket) {
    override val id = 0x02
    lateinit var name: String

    override fun send(output: BytePacketBuilder) {
        output.writeUUID(UUID.randomUUID())
        output.writeString(name)
    }
}