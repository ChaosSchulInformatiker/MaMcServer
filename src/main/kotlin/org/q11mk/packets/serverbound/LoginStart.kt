package org.q11mk.packets.serverbound

import org.q11mk.Socket
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import org.q11mk.packets.ServerBoundPacket
import org.q11mk.packets.clientbound.LoginSuccess
import org.q11mk.readString
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