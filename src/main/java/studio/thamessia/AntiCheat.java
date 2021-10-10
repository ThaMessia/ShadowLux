package studio.thamessia;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.PacketPlayInFlying;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AntiCheat implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        checkPlayer(e.getPlayer());
    }

    private void checkPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                super.channelRead(channelHandlerContext, packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                if (!(packet instanceof PacketPlayInFlying)) return;
                if (player.hasPermission("fly.use")) return;

                player.sendMessage("Don't cheat!");
                player.setAllowFlight(false);

                super.write(channelHandlerContext, packet, channelPromise);
            }
        };
    }
}
