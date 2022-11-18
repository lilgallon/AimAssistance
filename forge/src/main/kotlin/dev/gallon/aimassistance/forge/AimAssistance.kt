package dev.gallon.aimassistance.forge

import dev.gallon.aimassistance.core.domain.AimAssistanceConfig
import dev.gallon.aimassistance.core.services.AimAssistanceService
import dev.gallon.aimassistance.forge.events.RenderEvent
import dev.gallon.aimassistance.forge.events.SingleEventBus
import dev.gallon.aimassistance.forge.events.TickEvent
import dev.gallon.aimassistance.forge.adapters.ForgeMinecraftAdapter
import dev.gallon.aimassistance.forge.adapters.ForgeMouseAdapter
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod

@Mod("aimassistance")
class AimAssistance {
    private var aimAssistance: AimAssistanceService? = null

    init {
//        AutoConfig.register(ModConfig::class.java, ::JanksonConfigSerializer)
//        val config = AutoConfig.getConfigHolder(ModConfig::class.java).config

        SingleEventBus.register<TickEvent> {
//            initOrResetAimAssistance(config.config)
            aimAssistance?.analyseEnvironment()
            aimAssistance?.analyseBehavior()
        }

        SingleEventBus.register<RenderEvent> {
            aimAssistance?.assistIfPossible()
        }
    }

    private fun initOrResetAimAssistance(config: AimAssistanceConfig) {
        if (aimAssistance == null && Minecraft.getInstance().player != null) {
            aimAssistance = AimAssistanceService(
                minecraft = ForgeMinecraftAdapter(Minecraft.getInstance()),
                mouse = ForgeMouseAdapter(),
                config = config
            )
        } else if (aimAssistance != null && Minecraft.getInstance().player == null) {
            aimAssistance = null
        }
    }
}
