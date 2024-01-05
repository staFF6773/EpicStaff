package commands;

import github.epicstaff.EpicStaff;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import utils.ChatUtils;

public class ChatClearCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ChatClearCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Verifica si el jugador tiene permisos para ejecutar el comando
            if (player.hasPermission("epicstaff.chatclear")) {
                // Limpia el chat
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    for (int i = 0; i < 100; i++) {
                        onlinePlayer.sendMessage(""); // Envía líneas en blanco para "limpiar" el chat
                    }

                    // Obtiene el mensaje de chat-cleared de la configuración y reemplaza {player}
                    String chatClearedMessage = ChatColor.translateAlternateColorCodes('&',
                                    plugin.getConfig().getString("chat-cleared"))
                            .replace("%player_name%", player.getName());

                    onlinePlayer.sendMessage(chatClearedMessage);
                }

                return true;
            } else {
                // Obtiene el mensaje de no-permission-message de la configuración y reemplaza {player}
                String noPermissionMessage = ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("no-permission-message"))
                        .replace("%player_name%", player.getName());

                sender.sendMessage(ChatUtils.getColoredMessage(EpicStaff.prefix + " " + noPermissionMessage));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("player-only")));
        }

        return false;
    }
}
