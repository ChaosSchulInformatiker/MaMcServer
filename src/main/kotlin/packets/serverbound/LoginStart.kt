package packets.serverbound

import Socket
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import packets.ServerBoundPacket
import packets.clientbound.Disconnect_Login
import packets.clientbound.LoginSuccess
import readString
import writeString
import writeUUID
import java.util.*

class LoginStart(socket: Socket) : ServerBoundPacket(socket) {
    override val id = 0x00

    lateinit var name: String

    override suspend fun accept(input: ByteReadPacket) {
        println(input.readString().also { name = it })

        getClientPacket<LoginSuccess>(0x02).apply {
            this.name = this@LoginStart.name
        }.respond()
        //getClientPacket<Disconnect_Login>(0x00).respond()
    }
}