package dev.gallon.aimassistance.forge.events

sealed interface Event

class RenderEvent : Event

class TickEvent : Event

class MouseMoveEvent(
    val x: Double,
    val y: Double
) : Event

class LeftMouseClickEvent : Event
