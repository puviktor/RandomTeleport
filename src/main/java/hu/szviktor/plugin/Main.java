package hu.szviktor.plugin;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import hu.szviktor.plugin.commands.BaseCommand;
import hu.szviktor.plugin.teleport.TeleportTier;
import hu.szviktor.plugin.teleport.gui.TeleportGuiListener;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private static Main instance;
	
	public ArrayList<TeleportTier> loadedTiers = new ArrayList<TeleportTier>();
	
	public Economy economy;
	
	public static Main getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		this.getLogger().info("Starting up the plugin...");
		
		this.getCommand("randomteleport").setExecutor(new BaseCommand());
		this.getCommand("randomteleport").setTabCompleter(new BaseCommand());
		
		Bukkit.getPluginManager().registerEvents(new TeleportGuiListener(), this);
		
		this.registerVaultHook();
		this.saveDefaultConfig();
		this.loadTeleportTiers();
		
		this.getLogger().info("The plugin successfully enabled!");
		this.getLogger().info("This plugin was made by " + this.getDescription().getAuthors());
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("The plugin successfully disabled!");
	}
	
	public void registerVaultHook() {
		if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
			this.getLogger().severe("Unable to hook with Vault! The plugin disables itself!");
			
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		this.economy = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();
		
		this.getLogger().info("Successfully hooked with Vault!");
	}
	
	public void loadTeleportTiers() {
		ConfigurationSection tiers = this.getConfig().getConfigurationSection("teleportTiers");
		Iterator<String> tierIter = tiers.getKeys(false).iterator();
		
		this.loadedTiers.clear();
		
		while(tierIter.hasNext()) {
			String id = tierIter.next();
			String name = ChatColor.translateAlternateColorCodes('&', tiers.getString(id + ".name"));
			Material iconItem = Material.getMaterial(tiers.getString(id + ".icon"));
			World world = Bukkit.getWorld(tiers.getString(id + ".world"));
			int maxX = tiers.getInt(id + ".radiusX");
			int maxZ = tiers.getInt(id + ".radiusZ");
			double cost = tiers.getDouble(id + ".cost");
			
			ItemStack icon = new ItemStack(iconItem, 1);
			ItemMeta meta = icon.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			
			tiers.getStringList(id + ".lore").forEach(s -> lore.add(ChatColor.translateAlternateColorCodes('&', s).replaceAll("%cost%", "" + cost)));
			
			meta.setDisplayName(name);
			meta.setLore(lore);
			
			icon.setItemMeta(meta);
			
			TeleportTier tier = new TeleportTier(id, name, icon, world, maxX, maxZ, cost);
			
			this.loadedTiers.add(tier);
		}
		
		this.getLogger().info("Teleport tiers successfully loaded!");
	}
	
}
