package packets.clientbound

import Socket
import io.ktor.utils.io.*
import packets.ClientBoundPacket

class Disconnect_Login(socket: Socket) : ClientBoundPacket(socket) {
    override val id = 0x00

    override suspend fun send(output: ByteWriteChannel) {
        output.writeByte(1)
        output.writeByte(id)
    }
}