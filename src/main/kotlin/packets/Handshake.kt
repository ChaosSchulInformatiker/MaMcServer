package packets

import Socket
import io.ktor.utils.io.core.*
import readString
import readVarInt

class Handshake(socket: Socket) : Packet(socket) {
    override val id = 0x00

    /*var protocolVersion
    var serverIp
    var serverPort
    var nextState*/

    override fun accept(input: ByteReadPacket) {
        println(input.readVarInt())
        println(input.readString())
        println(input.readUShort())
        println(input.readVarInt())

        switchMode(Mode.Login)
    }
}

enum class NextState {
    HANDSHAKING, LOGIN
}