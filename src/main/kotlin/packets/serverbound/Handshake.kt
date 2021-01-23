package packets.serverbound

import Socket
import io.ktor.utils.io.core.*
import packets.ServerBoundPacket
import readString
import readVarInt
import readVarIntEnum

class Handshake(socket: Socket) : ServerBoundPacket(socket) {
    override val id = 0x00

    /*var protocolVersion
    var serverIp
    var serverPort
    var nextState*/

    override suspend fun accept(input: ByteReadPacket) {
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