package org.q11mk

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
            throw RuntimeException("org.q11mk.VarInt is too big")
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

fun BytePacketBuilder.writeUUID(uuid: UUID) {
    writeLong(uuid.leastSignificantBits)
    writeLong(uuid.mostSignificantBits)
}

fun BytePacketBuilder.writeString(string: String) {
    writeByte(string.length.toByte())
    for (c in string) {
        writeByte(c.toByte())
    }
}

fun BytePacketBuilder.writeVarInt(varInt: VarInt) {
    var value = varInt.int
    do {
        var temp = (value and 127).toByte()
        value = value ushr 7
        if (value != 0) {
            temp = (temp.toInt() or 128).toByte()
        }
        writeByte(temp)
    } while (value != 0)
}

fun BytePacketBuilder.writeByteArray(array: ByteArray) {
    for (b in array) {
        writeByte(b)
    }
}

inline class VarInt(val int: Int) {
    override fun toString() = int.toString()
}