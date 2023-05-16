package dev.gallon.aimassistance.forge.adapters

import dev.gallon.aimassistance.core.domain.Position
import dev.gallon.aimassistance.core.domain.Rotation
import dev.gallon.aimassistance.core.interfaces.Block
import dev.gallon.aimassistance.core.interfaces.Entity
import dev.gallon.aimassistance.core.interfaces.Player
import net.minecraft.client.player.LocalPlayer
import net.minecraft.util.Mth
import net.minecraft.world.entity.Mob
import net.minecraft.world.level.ClipContext
import net.minecraft.world.phys.AABB

class ForgePlayerAdapter(
    private val player: LocalPlayer,
) : ForgeEntityAdapter(player), Player {

    override fun setRotation(rotations: Rotation) {
        player.xRot = rotations.pitch.toFloat()
        player.yRot = rotations.yaw.toFloat()
    }

    override fun canInteract(): Boolean = player.isPickable

    override fun findMobsAroundPlayer(range: Double): List<Entity> =
        player
            .level
            .getEntitiesOfClass(
                Mob::class.java,
                AABB(
                    player.x - range,
                    player.y - range,
                    player.z - range,
                    player.x + range,
                    player.y + range,
                    player.z + range,
                ),
            ) { true }
            .map(::ForgeEntityAdapter)

    override fun rayTrace(reach: Double, source: Position, direction: Rotation): Block {
        val f2 = Mth.cos((-direction.yaw * (Math.PI / 180.0) - Math.PI).toFloat())
        val f3 = Mth.sin((-direction.yaw * (Math.PI / 180.0) - Math.PI).toFloat())
        val f4 = -Mth.cos((-direction.pitch * (Math.PI / 180.0)).toFloat())
        val f5 = Mth.sin((-direction.pitch * (Math.PI / 180.0)).toFloat())
        val f6 = f3 * f4
        val f7 = f2 * f4
        val vector = source.toVec3d().add(f6 * reach, f5 * reach, f7 * reach)

        return player
            .level
            .clip(
                ClipContext(
                    source.toVec3d(),
                    vector,
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    player,
                ),
            )
            .let(::ForgeBlockAdapter)
    }
}
