package hu.szviktor.plugin.teleport;

import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class TeleportTier {
	
	private String id;
	private String name;
	private ItemStack icon;
	private World world;
	private int maxX;
	private int maxZ;
	private double cost;
	
	public TeleportTier(String id, String name, ItemStack icon, World world, int maxX, int maxZ, double cost) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.world = world;
		this.maxX = maxX;
		this.maxZ = maxZ;
		this.cost = cost;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ItemStack getIcon() {
		return this.icon;
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public int getMaxX() {
		return this.maxX;
	}
	
	public int getMaxZ() {
		return this.maxZ;
	}
	
	public double getCost() {
		return this.cost;
	}

}
