import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import packets.ClientBoundPacket
import packets.clientbound.Disconnect_Login
import packets.clientbound.LoginSuccess
import packets.serverbound.Handshake
import packets.serverbound.LoginStart

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
        }
        packet.accept(reader)
    }

    fun packet(id: Int): ClientBoundPacket = when (mode) {
        Mode.Handshaking -> TODO()
        Mode.Status -> TODO()
        Mode.Login -> when (id) {
            0x00 -> Disconnect_Login(this)
            0x02 -> LoginSuccess(this)
            else -> TODO()
        }
    }

    suspend fun sendPacket(packet: ClientBoundPacket) {
        packet.send(output)
        output.flush()
    }
}

enum class Mode {
    Handshaking, Status, Login
}