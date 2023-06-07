package studio.genbu.mcplugins.craftcomm

import org.bukkit.plugin.java.JavaPlugin
import studio.genbu.mcplugins.craftcomm.events.Events

// SUZAKUCHAT IS COMPLETE.
class CraftComm: JavaPlugin() {

    override fun onEnable() {
        server.pluginManager.registerEvents(Events(this), this)
        logger.info("CraftComm is disabled.")
    }

    override fun onDisable() {

    }

}
