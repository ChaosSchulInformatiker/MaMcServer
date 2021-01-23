import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import java.util.*

fun ByteReadPacket.readVarInt(): VarInt {
    var numRead = 0
    var result = 0
    var read: Byte
    do {
        read = readByte()
        val value: Int = (read.toInt() and 0b01111111)
        result = (result or (value shl (7 * numRead)))
        numRead++
        if (numRead > 5) {
            throw RuntimeException("VarInt is too big")
        }
    } while ((read.toInt() and 0b10000000) != 0)

    return VarInt(result)
}

fun ByteReadPacket.readString(): String {
    val buf = StringBuffer()
    repeat(readByte().toInt()) {
        buf.append(readByte().toChar())
    }
    return buf.toString()
}

inline fun <reified E : Enum<E>> ByteReadPacket.readVarIntEnum(): Enum<E> {
    return E::class.java.enumConstants[readVarInt().int - 1]
}

suspend fun ByteWriteChannel.writeUUID(uuid: UUID) {
    writeLong(uuid.leastSignificantBits)
    writeLong(uuid.mostSignificantBits)
}

suspend fun ByteWriteChannel.writeString(string: String) {
    writeByte(string.length)
    for (c in string) {
        writeByte(c.toByte())
    }
}

inline class VarInt(val int: Int) {
    override fun toString() = int.toString()
}