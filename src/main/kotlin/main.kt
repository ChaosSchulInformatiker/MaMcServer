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

            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = true)

            /*var await = true
            var size = 0.toByte()
            var message = Flexible.make<ByteBuffer>()*/

            try {
                while (true) {
                    /*val currentByte = input.readByte()
                    //println("${socket.remoteAddress}: $currentByte")

                    if (await) {
                        size = currentByte
                        await = false
                        message = ByteBuffer.allocate(size.toInt())
                    } else {
                        input.read(size.toInt()) {

                        }
                    }
                    if (size <= 0.toByte()) {
                        await = true
                        analyze(message.array())(output)
                    }*/

                    val size = input.readByte().toInt()

                    /*input.readAvailable(size) {
                        println("START ${it.remaining()}")
                        repeat(it.remaining()) { _ ->
                            print(String.format("0x%02X", it.get().toUByte().toInt()))
                            print(" ")
                        }
                        println()
                        println("END")
                    }*/
                    input.readPacket(size).run {
                        println("ID: ${readByte()}")
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                mode = Mode.WAIT
                socket.close()
            }
        }
    }
}