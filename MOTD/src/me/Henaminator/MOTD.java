package me.Henaminator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MOTD extends JavaPlugin implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent e) {
            String motd = getConfig().getString("motd.system");
            motd = motd.replaceAll("&", "\u00A7");
            motd = motd.replaceAll("%newline", "\n");
            e.setMotd(motd);
    }
   
    public void onEnable() {
            getConfig().options().copyDefaults(true);
            saveConfig();
            Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }
   
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
            if (cmd.getName().equalsIgnoreCase("motd")) {
                    if (!sender.hasPermission("motd.check")) {
                            sender.sendMessage(ChatColor.RED + "Du har ikke rettigheter til å utføre kommandoen!");
                            return true;
                    }
                    String system = getConfig().getString("motd.system");
                    system = system.replaceAll("&", "§");
                    sender.sendMessage(ChatColor.GREEN + "System MOTD: " + system);
                    return true;
            }
            if (cmd.getName().equalsIgnoreCase("setmotd")) {
                    if (!sender.hasPermission("motd.set")) {
                            sender.sendMessage(ChatColor.RED + "You are not permitted to do this!");
                            return true;
                    }
                    if (args.length == 0) {
                            sender.sendMessage(ChatColor.RED + "Please specify a message!");
                            return true;
                    }
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                            str.append(args[i] + " ");
                    }
                    String motd = str.toString();
                    getConfig().set("motd.ingame", motd);
                    saveConfig();
                    String newmotd = getConfig().getString("motd.ingame");
                    motd = motd.replaceAll("&", "§");
                    sender.sendMessage(ChatColor.GREEN + "MOTD set to: " + newmotd);
                    return true;
            }
            if (cmd.getName().equalsIgnoreCase("setsystemmotd")) {
                    if (!sender.hasPermission("motd.set")) {
                            sender.sendMessage(ChatColor.RED + "You are not permitted to do this!");
                            return true;
                    }
                    if (args.length == 0) {
                            sender.sendMessage(ChatColor.RED + "Please specify a message!");
                            return true;
                    }
                    StringBuilder str = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                            str.append(args[i] + " ");
                    }
                    String motd = str.toString();
                    getConfig().set("motd.system", motd);
                    saveConfig();
                    String system = getConfig().getString("motd.system");
                    system = system.replaceAll("&", "§");
                    sender.sendMessage(ChatColor.GREEN + "MOTD set to: " + system);
                    return true;
            }
            return true;
    }
}
