package studio.thamessia;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShadowLux extends JavaPlugin {
    private FileConfiguration configuration = this.getConfig();

    @Override
    public void onEnable() {
        configuration = this.getConfig();

        configuration.addDefault("ShadowLux", true);
        configuration.options().copyDefaults(true);

        configuration.addDefault("AntiCheat", true);
        configuration.options().copyDefaults(true);

        saveConfig();
        System.out.println("WARNING: " + "THIS IS ONLY FOR DEVELOPERS' USE.");
        System.out.println("[ShadowLux]" + " Plugin enabled!");

        if (configuration.getBoolean("ShadowLux")) Bukkit.getPluginManager().registerEvents(new PacketSniffer(), this);
        if (configuration.getBoolean("AntiCheat")) Bukkit.getPluginManager().registerEvents(new AntiCheat(), this);

        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        System.out.println("[ShadowLux] Plugin disabled!");
    }
}
