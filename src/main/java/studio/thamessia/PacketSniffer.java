package studio.thamessia;

import io.netty.channel.*;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.PacketPlayOutKeepAlive;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PacketSniffer implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        removePlayer(event.getPlayer());
    }
    private void removePlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }

    private void addPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "[ShadowLux] " + ChatColor.YELLOW + "Sniffed packet: " + ChatColor.RED + packet.toString());
                super.channelRead(channelHandlerContext, packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE +  "[ShadowLux] " + ChatColor.AQUA + "Server response: " + ChatColor.GREEN + packet.toString());
                super.write(channelHandlerContext, packet, channelPromise);
            }
        };

        //important to understand packets and get player's native mc version
        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }
}
