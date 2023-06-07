package studio.genbu.mcplugins.craftcomm.events

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import org.apache.commons.lang3.mutable.Mutable
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import studio.genbu.mcplugins.craftcomm.CraftComm

class Events (private val main: CraftComm): Listener {

    @EventHandler
    fun onAsyncChat(event: AsyncChatEvent) {

        var viewers = event.viewers()

        viewers.removeAll(event.viewers())

        fun getNearbyPlayers(limit: Int, player: Player): Set<Player> {
            fun getNearbyPlayers(limit: Int, player: Player, searched: MutableSet<Player>): MutableSet<Player> {
                if (limit > 0) {
                    var limit = limit
                    limit--;
                    val players = event.player.world.players
                    val search: MutableSet<Player> = mutableSetOf()

                    for (player in players) {
                        if (
                            player.location.distance(event.player.location) <= 128
                            && !(searched.contains(player))
                        ) {
                            event.player.sendMessage(Component.text("Limit: " + limit + " Players: " + searched.toString()))
                            searched.add(player)
                            searched.addAll(getNearbyPlayers(limit, player, searched))
                        }
                    }
                }

                return searched
            }

            val searched: MutableSet<Player> = mutableSetOf()

            if (limit > 0) {
                val players = event.player.world.players

                for (player in players) {
                    searched.addAll(getNearbyPlayers(limit, player, searched))
                }
            }

            return searched
        }

        event.viewers().addAll(getNearbyPlayers(8, event.player))

        event.viewers().add(main.server.consoleSender)
        event.player.sendMessage(Component.text(event.viewers().toString()))

        event.renderer { source, sourceDisplayName, message, viewer ->
            val replacedMessage = message.replaceText(TextReplacementConfig.builder()
                .matchLiteral("test")
                .replacement("テスト")
                .build())

            Component.text()
                .append(Component.text("まにさば | "))
                .append(sourceDisplayName)
                .append(Component.text(": "))
                .append(replacedMessage)
                .build()
        }

    }

}
