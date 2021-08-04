package hu.szviktor.plugin.teleport.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import hu.szviktor.plugin.Main;
import hu.szviktor.plugin.TeleportUtil;
import hu.szviktor.plugin.teleport.TeleportTier;
import net.md_5.bungee.api.ChatColor;

public class TeleportGuiListener implements Listener {
	
	private Main main = Main.getInstance();
	
	@EventHandler
	public void onIteract(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(e.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', this.main.getConfig().getString("teleportMenu.title")))) {
			e.setCancelled(true);
			
			for(TeleportTier tier : this.main.loadedTiers) {
				if(tier.getIcon().equals(e.getCurrentItem())) {
					
					TeleportUtil.teleportPlayer(p, tier);
				}
			}
		}
	}

}
