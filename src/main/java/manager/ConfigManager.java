package manager;

import github.epicstaff.EpicStaff;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(EpicStaff epicStaff){
        ConfigManager.config = epicStaff.getConfig();
        epicStaff.saveDefaultConfig();
    }
}