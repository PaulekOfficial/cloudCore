package common;

import com.paulek.core.Core;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.logging.Level;

public class ConsoleLog {
    private Plugin plugin;
    private Core core;

    public ConsoleLog(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
        plugin = core.getPlugin();
    }

    public void warning(String message) {
        plugin.getLogger().warning(message);
    }

    public void info(String message) {
        plugin.getLogger().info(message);
    }

    public void log(String message, Level level) {
        plugin.getLogger().log(level, message);
    }
}
