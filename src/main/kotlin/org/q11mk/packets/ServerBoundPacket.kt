package org.q11mk.packets

import org.q11mk.Socket
import io.ktor.utils.io.core.*

abstract class ServerBoundPacket(socket: Socket) : Packet(socket) {
    abstract suspend fun accept(input: ByteReadPacket)
}

/*class Layout {
    val reads = mutableListOf<LayoutDelegate<*>>()

    fun accept(reader: ByteReadPacket) {
        reads.forEach { it.value = it.reader(reader) }
    }
}

class ElementData<T : Any>(val reader: ByteReadPacket.() -> T) : PropertyDelegateProvider<Packet, LayoutDelegate<*>> {
    override fun provideDelegate(thisRef: Packet, property: KProperty<*>): LayoutDelegate<*> {
        return LayoutDelegate(reader).also { thisRef.layout.reads.add(it) }
    }
}

class LayoutDelegate(val reader: ByteReadPacket.() -> T) : ReadOnlyProperty<Packet, T> {
    var value: T? = null

    override fun getValue(thisRef: Packet, property: KProperty<*>): T {
        return value ?: throw UninitializedPropertyAccessException()
    }
}*/