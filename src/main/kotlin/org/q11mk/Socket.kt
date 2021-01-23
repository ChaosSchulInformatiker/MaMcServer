package org.q11mk

import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import org.q11mk.packets.ClientBoundPacket
import org.q11mk.packets.clientbound.Disconnect_Login
import org.q11mk.packets.clientbound.EncryptionRequest
import org.q11mk.packets.clientbound.LoginSuccess
import org.q11mk.packets.serverbound.Handshake
import org.q11mk.packets.serverbound.LoginStart

class Socket(private val input: ByteReadChannel, private val output: ByteWriteChannel) {
    var mode = Mode.Handshaking

    suspend fun applyPacket(id: Int, reader: ByteReadPacket) {
        val packet = when (mode) {
            Mode.Handshaking -> when (id) {
                0x00 -> Handshake(this)
                else -> TODO()
            }
            Mode.Status -> TODO()
            Mode.Login -> when (id) {
                0x00 -> LoginStart(this)
                else -> TODO()
            }
            Mode.Play -> TODO()
        }
        packet.accept(reader)
        packet.future()
    }

    fun packet(id: Int): ClientBoundPacket = when (mode) {
        Mode.Handshaking -> TODO()
        Mode.Status -> TODO()
        Mode.Login -> when (id) {
            0x00 -> Disconnect_Login(this)
            0x01 -> EncryptionRequest(this)
            0x02 -> LoginSuccess(this)
            else -> TODO()
        }
        Mode.Play -> TODO()
    }

    suspend fun sendPacket(packet: ClientBoundPacket) {
        val builder = BytePacketBuilder().also { packet.send(it) }
        output.writePacket(BytePacketBuilder().apply {
            writeByte((builder.size + 1).toByte())
            writeByte(packet.id.toByte())
            writePacket(builder.build())
        }.build())
        output.flush()
        packet.future()
    }
}

enum class Mode {
    Handshaking, Status, Login, Play
}