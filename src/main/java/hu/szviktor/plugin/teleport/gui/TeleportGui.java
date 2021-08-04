package hu.szviktor.plugin.teleport.gui;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import hu.szviktor.plugin.Main;
import hu.szviktor.plugin.teleport.TeleportTier;
import net.md_5.bungee.api.ChatColor;

public class TeleportGui {
	
	private Main main = Main.getInstance();
	
	public void openTeleportGui(Player p) {
		String title = ChatColor.translateAlternateColorCodes('&', this.main.getConfig().getString("teleportMenu.title"));
		int size = this.main.getConfig().getInt("teleportMenu.size");
		boolean fillEmpty = this.main.getConfig().getBoolean("teleportMenu.fillEmptySlots");
		Material fillMaterial = Material.getMaterial(this.main.getConfig().getString("teleportMenu.fillItem"));
		
		if(!((size % 9) == 0))
			throw new IllegalArgumentException("The size of the inventory is only could be divisible by 9 and not larger than 45");
		
		Inventory teleportGui = Bukkit.createInventory(p, size, title);
		
		Iterator<TeleportTier> teleportTiers = this.main.loadedTiers.iterator();
		
		while(teleportTiers.hasNext()) {
			TeleportTier tier = teleportTiers.next();
			
			teleportGui.addItem(tier.getIcon());
		}
		
		for(int i = 0; i < size; i++)
			if((teleportGui.getItem(i) == null || teleportGui.getItem(i).getType() == Material.AIR) && fillEmpty)
				teleportGui.setItem(i, new ItemStack(fillMaterial, 1));
		
		p.openInventory(teleportGui);
	}

}
