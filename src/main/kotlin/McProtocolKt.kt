import io.ktor.utils.io.core.*
import packets.VarInt

fun ByteArray.getVarInt(index: Int): IndexedResult<Int> {
    var numRead = 0
    var result = 0
    var read: Byte
    var i = index
    do {
        read = get(i++)
        val value: Int = (read.toInt() and 0b01111111)
        result = (result or (value shl (7 * numRead)))
        numRead++
        if (numRead > 5) {
            throw RuntimeException("VarInt is too big")
        }
    } while ((read.toInt() and 0b10000000) != 0)

    return result to ++i
}

fun ByteArray.getString(index: Int, length: Int): IndexedResult<String> {
    val buf = StringBuffer()
    for (i in index until (index + length)) {
        buf.append(get(i).toChar())
    }
    return (buf.toString()) to (index + length)
}

fun ByteArray.getUShort(index: Int): IndexedResult<UShort> {
    return (((get(index).toUByte().toInt() shl 8) or (get(index + 1).toUByte().toInt())).toUShort()) to (index + 2)
}

typealias IndexedResult<T> = Pair<T, Int>

fun ByteArray.printData() {
    val buf = StringBuffer()
    buf.append('[')
    var comma = ""
    for (b in this) {
        buf.append(comma)
        buf.append(String.format("0x%02X", b.toUByte().toInt()))
        comma = ", "
    }
    buf.append(']')
    println(buf.toString())
}

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