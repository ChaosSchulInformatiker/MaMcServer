package packets

import Mode
import Socket
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class Packet(private val socket: Socket) {
    abstract val id: Int
    //val layout = Layout()

    abstract fun accept(input: ByteReadPacket)

    open fun respond(output: ByteWriteChannel) {}

    fun switchMode(mode: Mode) { socket.mode = mode }
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

inline class VarInt(val int: Int)