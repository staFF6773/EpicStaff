package events;

import github.epicstaff.EpicStaff;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import utils.ChatUtils;

public class messageblocker implements Listener {

    private final EpicStaff plugin;

    public messageblocker(EpicStaff plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        // Verificar si el bloqueo está desactivado en la configuración
        if (!plugin.getConfig().getBoolean("module-chat.enabled", true)) {
            return;
        }

        Player player = event.getPlayer();
        String message = event.getMessage();
        String blockedWord = null; // Variable para almacenar la palabra bloqueada

        // Verifica si el jugador tiene el permiso de bypass
        if (player.hasPermission("epicstaff.bypass.chat")) {
            return;  // No se aplica el bloqueo
        }


        for (String word : plugin.getConfig().getStringList("blocked-words")) {
            if (message.toLowerCase().contains(word.toLowerCase())) {
                blockedWord = word;
                String blockedMessage = plugin.getConfig().getString("blocked-message");
                blockedMessage = blockedMessage.replace("%es-word%", blockedWord);
                blockedMessage = blockedMessage.replace("%player_name%", player.getName());
                player.sendMessage(ChatUtils.getColoredMessage(EpicStaff.prefix + " " + blockedMessage));
                event.setCancelled(true);
                break;
            }
        }

        if (blockedWord != null) {
            // Aquí puedes hacer lo que necesites con la palabra bloqueada y el nombre del jugador.
        }
    }
}
