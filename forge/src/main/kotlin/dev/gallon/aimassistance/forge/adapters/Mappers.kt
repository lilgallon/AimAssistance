package dev.gallon.aimassistance.forge.adapters

import dev.gallon.aimassistance.core.domain.Position
import dev.gallon.aimassistance.core.domain.Rotation
import net.minecraft.world.phys.Vec3

fun Vec3.toPosition() = Position(x, y, z)

fun Vec3.toRotation() = Rotation(yaw = y, pitch = x)

fun Position.toVec3d() = Vec3(x, y, z)
