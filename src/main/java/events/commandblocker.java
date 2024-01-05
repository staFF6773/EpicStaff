package events;

import github.epicstaff.EpicStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import utils.ChatUtils;

public class commandblocker implements Listener {

    private final EpicStaff plugin;

    public commandblocker(EpicStaff plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        // Verificar si el bloqueo está desactivado en la configuración
        if (!plugin.getConfig().getBoolean("module-chat.enabled", true)) {
            return;
        }

        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0].substring(1);

        // Verifica si el jugador tiene el permiso de bypass
        if (player.hasPermission("epicstaff.bypass.chat")) {
            return;  // No se aplica el bloqueo
        }

        String[] blockedCommands = plugin.getConfig().getStringList("blocked-commands").toArray(new String[0]);

        for (String blockedCommand : blockedCommands) {
            if (command.equalsIgnoreCase(blockedCommand)) {
                String commandLockedMessage = plugin.getConfig().getString("command-locked");
                commandLockedMessage = commandLockedMessage.replace("%es-command%", blockedCommand);
                commandLockedMessage = commandLockedMessage.replace("%player_name%", player.getName());
                player.sendMessage(ChatUtils.getColoredMessage(EpicStaff.prefix + " " + commandLockedMessage));
                event.setCancelled(true);
                break;
            }
        }
    }
}

