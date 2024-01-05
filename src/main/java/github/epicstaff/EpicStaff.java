package github.epicstaff;

import commands.ChatClearCommand;
import commands.KickCommand;
import commands.reload;
import events.commandblocker;
import events.messageblocker;
import manager.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import utils.ChatUtils;

public final class EpicStaff extends JavaPlugin {

    ConsoleCommandSender mycmd = Bukkit.getConsoleSender();
    public static String prefix;

    public static EpicStaff plugin;

    @Override
    public void onEnable() {

        //prefix
        prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix", "&8[&aEpicStaff&8]"));

        //config
        ConfigManager.setupConfig(this);
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        //Register
        registerCommands();
        plugin = this;
        registerEvents();
        reloadConfig();

        mycmd.sendMessage(ChatUtils.getColoredMessage(
                "\u001B[32m" + // Código de color verde claro
                        "    ______      _      _____ __        ________\n" +
                        "   / ____/___  (_)____/ ___// /_____ _/ __/ __/\n" +
                        "  / __/ / __ \\/ / ___/\\__ \\/ __/ __ `/ /_/ /_  \n" +
                        " / /___/ /_/ / / /__ ___/ / /_/ /_/ / __/ __/  \n" +
                        "/_____/ .___/_/\\___//____/\\__/\\__,_/_/ /_/     \n" +
                        "     /_/                                       \n" +
                        "\u001B[0m" // Restablecer el color a predeterminado
        ));

    }

    @Override
    public void onDisable() {

        // Guardar configuración al desactivar el plugin
        saveConfig();

        mycmd.sendMessage(ChatUtils.getColoredMessage("&aEpicStaff is being disabled this does not affect anything."));
        mycmd.sendMessage(ChatUtils.getColoredMessage("&7Commands Saved Successfully"));
        mycmd.sendMessage(ChatUtils.getColoredMessage("&7Modules saved Successfully"));
        mycmd.sendMessage(ChatUtils.getColoredMessage("&7Goodbye!"));

    }

    public void registerCommands() {
        this.getCommand("reload").setExecutor(new reload(this));
        this.getCommand("chatclear").setExecutor(new ChatClearCommand(this));
        this.getCommand("kick").setExecutor(new KickCommand(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new messageblocker(this), this);
        getServer().getPluginManager().registerEvents(new commandblocker(this),this);
    }

    public static EpicStaff getPlugin() {
        return plugin;
    }
}
