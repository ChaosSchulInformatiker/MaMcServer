import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import packets.Handshake
import packets.LoginStart

class Socket {
    var mode = Mode.Handshaking

    fun applyPacket(id: Int, reader: ByteReadPacket, writer: ByteWriteChannel) {
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
        packet.respond(writer)
    }
}

enum class Mode {
    Handshaking, Status, Login
}