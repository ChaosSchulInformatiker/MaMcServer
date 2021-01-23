import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress

fun main() = runBlocking {
    val server = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().bind(InetSocketAddress("127.0.0.1", 25565))
    println("Started echo telnet server at ${server.localAddress}")

    while (true) {
        val socket = server.accept()

        launch {
            println("Socket accepted: ${socket.remoteAddress}")
            val connectionSocket = Socket()

            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = false)

            try {
                while (true) {
                    val size = input.readByte().toInt()

                    input.readPacket(size).run {
                        val id = readByte().toInt()
                        println("ID: $id")
                        connectionSocket.applyPacket(id, this, output)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                mode = ModeO.WAIT
                socket.close()
            }
        }
    }
}