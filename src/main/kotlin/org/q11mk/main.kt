package org.q11mk

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress

fun main() = runBlocking {
    val server = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().bind(InetSocketAddress("127.0.0.1"/*"192.168.178.60"*/, 25565))
    println("Started echo telnet server at ${server.localAddress}")

    while (true) {
        val socket = server.accept()

        launch {
            println("org.q11mk.Socket accepted: ${socket.remoteAddress}")

            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = false)

            val connectionSocket = Socket(input, output)

            try {
                while (true) {
                    val size = input.readByte().toInt()

                    input.readPacket(size).run {
                        val id = readByte().toInt()
                        println("ID: $id")
                        connectionSocket.applyPacket(id, this)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                //mode = ModeO.WAIT
                socket.close()
            }
        }
    }
}