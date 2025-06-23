package org.andrexserver.playerUtil.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import org.andrexserver.playerUtil.Main;
import org.andrexserver.playerUtil.pterodactyl.PowerAction;
import org.andrexserver.playerUtil.pterodactyl.PteroAPI;
import org.andrexserver.playerUtil.pterodactyl.StatsResponse;

import java.util.List;

public class PowerCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();

        if(!source.hasPermission("pteroutil.serverctl")) {
            source.sendMessage(Component.text("§a[§6PteroUtil§a] §cSorry, You do not have permission to use this command!"));
            return;
        }

        String[] args = invocation.arguments();
        if(args.length != 2) {
            source.sendMessage(Component.text("§a[§6PteroUtil§a] §6Unknown action! §c/serverctl <start|stop|restart|status> <server>"));
            return;
        }
        String action = args[0];
        String server = args[1];
        PteroAPI api = new PteroAPI();
        switch (action) {
            case "start":
                source.sendMessage(Component.text(api.power(Main.panelUrl,Main.apiKey,api.resolveServerName(server), PowerAction.START)));
                break;
            case "stop":
                source.sendMessage(Component.text(api.power(Main.panelUrl,Main.apiKey,api.resolveServerName(server), PowerAction.STOP)));
                break;
            case "restart":
                source.sendMessage(Component.text(api.power(Main.panelUrl,Main.apiKey,api.resolveServerName(server), PowerAction.RESTART)));
                break;
            case "status":
                source.sendMessage(Component.text("§a[§6PteroUtil§a] §aGetting Server stats from API..."));
                StatsResponse stats = api.parseResources(Main.panelUrl,Main.apiKey,api.resolveServerName(server));
                if(stats == null) {
                    source.sendMessage(Component.text("§a[§6PteroUtil§a] §cError: §6" + server + "§c does not exist!"));
                    return;
                }
                source.sendMessage(Component.text("§a[§6PteroUtil§a]"));
                source.sendMessage(Component.text("§a[§6PteroUtil§a] §aState: " + stats.attributes.current_state));
                source.sendMessage(Component.text("§a[§6PteroUtil§a] §aMemory: " + stats.attributes.resources.memory_bytes / 1024 / 1024 + "MB"));
                source.sendMessage(Component.text("§a[§6PteroUtil§a] §aCPU: " + stats.attributes.resources.cpu_absolute + "%"));
                source.sendMessage(Component.text("§a[§6PteroUtil§a] §aDisk: " + stats.attributes.resources.disk_bytes / 1024 / 1024 + "MB"));

                long uptimeSecs = stats.attributes.resources.uptime;
                long hours = uptimeSecs / 3600;
                long minutes = (uptimeSecs % 3600) / 60;
                long seconds = uptimeSecs % 60;

                String formattedUptime = String.format("%d hrs, %02d mins, %02d secs", hours, minutes, seconds);
                source.sendMessage(Component.text("Uptime: " + formattedUptime));
                break;
            default:
                source.sendMessage(Component.text("§a[§6PteroUtil§a] §6Unknown action! §c/serverctl <start|stop|restart|status> <server>"));
                break;
        }

    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource source = invocation.source();
        List<String> SUBCOMMANDS = List.of("start", "stop", "restart", "status");
        if(args.length < 2) {
            return SUBCOMMANDS;
        }
        return Main.serverList.keySet().stream().toList();
    }
}
