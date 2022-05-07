package io.github.stupidgame.jdaplugin

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin

class Jdaplugin : JavaPlugin(), Listener {
    private val TOKEN = ""
    var jda : JDA? = JDABuilder
        .createDefault(TOKEN , GatewayIntent.GUILD_MESSAGES)
        .setRawEventsEnabled(true)
        .setActivity(Activity.listening("ワルキューレの曲"))
        .addEventListeners(D2MC())
        .build()

    inner class D2MC : ListenerAdapter(){
        override fun onMessageReceived(e: MessageReceivedEvent) {
            val msg:String = e.message.contentRaw
            var chid : String = e.channel.id
            if (chid == "949870723038588969" && !e.author.isBot){
                server.broadcastMessage(msg)
            }
            }
        }

        override fun onEnable() {
            val txtch: TextChannel? = jda?.getTextChannelById("949870723038588969")
            server.pluginManager.registerEvents(this , this)
            txtch?.sendMessage("サーバーを起動しました！")?.queue()
        }

        override fun onDisable() {
            val txtch: TextChannel? = jda?.getTextChannelById("949870723038588969")
            txtch?.sendMessage("サーバーをシャットダウンしました！")?.queue()
            jda?.shutdownNow()
        }
        @EventHandler fun onChat(e: AsyncPlayerChatEvent){
            var txtch: TextChannel? = jda?.getTextChannelById("949870723038588969")
            var msg = e.message
            txtch?.sendMessage(msg)?.queue()
        }
    }

