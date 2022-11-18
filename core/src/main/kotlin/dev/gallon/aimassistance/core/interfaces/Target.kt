package dev.gallon.aimassistance.core.interfaces

import dev.gallon.aimassistance.core.domain.Position
import dev.gallon.aimassistance.core.domain.Rotation

sealed interface Target {
    fun getPosition(): Position
}

interface Entity : Target {
    fun getEyesHeight(): Double
    fun getEyesPosition(): Position = getPosition().run { copy(y = y + getEyesHeight()) }
    fun getRotations(): Rotation
}

interface Player : Entity {
    fun setRotation(rotations: Rotation)

    /**
     * @return true if the player is in game and not in any GUIs
     */
    fun canInteract(): Boolean

    fun findMobsAroundPlayer(range: Double): List<Entity>

    fun rayTrace(reach: Double, source: Position, direction: Rotation): Block?
}

interface Block : Target {
    fun getFacePosition(): Position
}
