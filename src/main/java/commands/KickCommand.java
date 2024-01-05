package commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class KickCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public KickCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verificar si el remitente es un jugador o tiene permisos de operador (OP)
        boolean isOp = sender instanceof Player && ((Player) sender).isOp();

        // Verificar permisos (permisos para jugadores y consola, o permisos de operador)
        if (!(sender.hasPermission("epicstaff.kick") || isOp)) {
            sender.sendMessage(getMessage("no-permission-message").replace("%player_name%", sender.getName()));
            return true;
        }

        // Verificar si se proporcionó el nombre del jugador a expulsar
        if (args.length == 0) {
            sender.sendMessage(getMessage("incorrect-usage-kick"));
            return true;
        }

        // Obtener el nombre del jugador a expulsar
        String targetPlayerName = args[0];

        // Obtener la instancia del jugador
        Player targetPlayer = sender.getServer().getPlayer(targetPlayerName);

        // Verificar si el jugador está en línea
        if (targetPlayer == null) {
            sender.sendMessage(getMessage("player-not-online").replace("%player_name%", targetPlayerName));
            return true;
        }

        // Expulsar al jugador
        targetPlayer.kickPlayer(getMessage("player-kicked").replace("%player_name%", targetPlayer.getName()));

        // Notificar al remitente que el jugador ha sido expulsado
        sender.sendMessage(getMessage("player-kicked").replace("%player_name%", targetPlayer.getName()));

        return true;
    }

    private String getMessage(String key) {
        FileConfiguration config = plugin.getConfig();
        return config.getString("messages." + key, "&cMensaje no configurado en config.yml");
    }
}

