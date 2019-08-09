package common;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterUtils {

    public static List<String> getHomes(CommandSender sender, Core core) {
        if (sender instanceof Player) {
            List<String> homes = new ArrayList<>();
            User user = core.getUsersStorage().getUser(((Player) sender).getUniqueId());
            for (String name : user.getHomes().keySet()) {
                homes.add(name);
            }
            return homes;
        }

        return new ArrayList<>();
    }
}
