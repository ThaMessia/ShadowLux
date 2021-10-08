package studio.thamessia;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShadowLux extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("WARNING: THIS IS ONLY FOR DEVELOPERS' USE.");
        System.out.println("[ShadowLux] Plugin enabled!");
        Bukkit.getPluginManager().registerEvents(new PacketSniffer(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[ShadowLux] Plugin disabled!");
    }
}
