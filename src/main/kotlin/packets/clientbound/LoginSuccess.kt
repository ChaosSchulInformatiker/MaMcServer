package packets.clientbound

import Socket
import io.ktor.utils.io.*
import packets.ClientBoundPacket
import writeString
import writeUUID
import java.util.*

class LoginSuccess(socket: Socket) : ClientBoundPacket(socket) {
    override val id = 0x02
    lateinit var name: String

    override suspend fun send(output: ByteWriteChannel) {
        output.writeByte(1 + 16 + 1 + name.length)
        output.writeByte(id)

        output.writeUUID(UUID.randomUUID())
        output.writeString(name)
    }
}