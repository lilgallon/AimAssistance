package dev.gallon.aimassistance.forge

import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod("aimassistance")
class AimAssistance {
    private val LOGGER: Logger = LogManager.getLogger()

    init {
        LOGGER.info("example mod initialization")
    }
}
