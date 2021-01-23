package packets

import Socket
import io.ktor.utils.io.core.*
import readString

class LoginStart(socket: Socket) : Packet(socket) {
    override val id = 0x00

    override fun accept(input: ByteReadPacket) {
        println(input.readString())
    }
}