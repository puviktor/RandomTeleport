package hu.szviktor.plugin.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import hu.szviktor.plugin.Main;
import hu.szviktor.plugin.teleport.gui.TeleportGui;
import net.md_5.bungee.api.ChatColor;

public class BaseCommand implements CommandExecutor, TabExecutor {
	
	private Main main = Main.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("reload")) {
				if(sender.hasPermission("randomteleport.reload")) {
					this.main.reloadConfig();
					
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.main.getConfig().getString("messages.reload")));
					
					return true;
				}
			}
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.main.getConfig().getString("messages.player_only")));
			
			return true;
		}
		
		Player p = (Player) sender;
		
		TeleportGui gui = new TeleportGui();
		
		gui.openTeleportGui(p);
		
		if(this.main.getConfig().getBoolean("teleportMenu.openSound"))
			p.getWorld().playSound(p.getLocation(), Sound.valueOf(this.main.getConfig().getString("teleportMenu.sound")), 1.0F, 1.0F);
		
		return true;
	}
	
	@Override
	public ArrayList<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> tabComplete = new ArrayList<String>();
		
		if(args.length == 1)
			if(sender.hasPermission("randomteleport.reload"))
				tabComplete.addAll(Arrays.asList("reload"));
		
		return tabComplete;
	}

}
