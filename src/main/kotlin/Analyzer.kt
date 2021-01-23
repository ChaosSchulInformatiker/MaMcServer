/*import io.ktor.utils.io.*
import java.nio.ByteBuffer

var mode = ModeO.WAIT

fun analyze(data: ByteArray): Future {
    print(" >>> ")
    data.printData()
    return when (mode) {
        ModeO.WAIT ->
            when (data[0]) {
                0.toByte() -> analyzeHandshake(data)
                else -> TODO()
            }
        ModeO.CONNECT ->
            when (data[0]) {
                0.toByte() -> analyzeLoginStart(data)
                else -> TODO()
            }
    }
}

fun analyzeHandshake(data: ByteArray): Future {
    val (protocolVersion, i0) = data.getVarInt(1)
    val (serverAddress, i1) = data.getString(i0, data.size - i0 - 3)
    val (port, i2) = data.getUShort(i1)
    val (status, _) = data.getVarInt(i2)

    println("Protocol Version: $protocolVersion Server Address: $serverAddress Port: $port Status: $status")

    mode = ModeO.CONNECT
    println("Enter mode CONNECT")

    return {}
}

fun analyzeLoginStart(data: ByteArray): Future {
    val (username, _) = data.getString(1, data.size - 1)

    println("Username: $username")

    return {
        val out = ByteBuffer.allocate(2 + 16 + username.length)
        out.put((1 + 16 + username.length).toByte())
        out.put(2)
        //val uuid = UUID.randomUUID()
        for (i in 2..17) {
            out.put(67)
        }
        for (c in username) {
            out.put(c.toByte())
        }
        it.write { byteBuffer -> byteBuffer.put(out) }
    }
}

enum class ModeO {
    WAIT, CONNECT
}

typealias Future = suspend (ByteWriteChannel) -> Unit*/