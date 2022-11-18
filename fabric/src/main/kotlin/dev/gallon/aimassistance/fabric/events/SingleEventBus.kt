package dev.gallon.aimassistance.fabric.events

import kotlin.reflect.KClass

object SingleEventBus {

    val callbacks = mutableMapOf<KClass<out Event>, (event: Event) -> Unit>()

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Event> register(noinline callback: (event: T) -> Unit) {
        callbacks[T::class] = callback as (event: Event) -> Unit
    }

    fun <T : Event> send(event: T) {
        callbacks[event::class]?.invoke(event)
    }
}
