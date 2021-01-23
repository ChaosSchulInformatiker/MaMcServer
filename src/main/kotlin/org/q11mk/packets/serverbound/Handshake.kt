package org.q11mk.packets.serverbound

import io.ktor.utils.io.core.*
import org.q11mk.*
import org.q11mk.packets.ServerBoundPacket

class Handshake(socket: Socket) : ServerBoundPacket(socket) {
    override val id = 0x00

    /*var protocolVersion
    var serverIp
    var serverPort
    var nextState*/

    override fun accept(input: ByteReadPacket) {
        println(input.readVarInt())
        println(input.readString())
        println(input.readUShort())
        println(input.readVarIntEnum<NextState>())

        switchMode(Mode.Login)
    }
}

enum class NextState {
    HANDSHAKING, LOGIN
}