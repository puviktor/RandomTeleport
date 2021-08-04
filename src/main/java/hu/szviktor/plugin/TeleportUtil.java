package hu.szviktor.plugin;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import hu.szviktor.plugin.teleport.TeleportTier;
import net.md_5.bungee.api.ChatColor;

public class TeleportUtil {
	
	private static Main main = Main.getInstance();
	
	public static int generateRandom(int range) {
		return new SecureRandom().nextInt(range * 2) - range;
	}
	
	private static Location getLocation(World world, int x, int z) {
		x = generateRandom(x);
		z = generateRandom(z);
		
		Location loc = world.getHighestBlockAt(x, z).getLocation();
		
		main.getLogger().info("calculating...");
		
		return loc.add(0.5, 1.3, 0.5); //Centering the player to the block.
	}
	
	public static void teleportPlayer(Player p, TeleportTier tier) {
		if(main.economy.getBalance(p) < tier.getCost()) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.not_enough_money")));
			
			return;
		}
		
		ArrayList<Material> disallowed = new ArrayList<Material>();
		
		disallowed.addAll(Arrays.asList(Material.LAVA, Material.WATER, Material.FIRE, Material.GRAVEL, Material.AIR, Material.LEGACY_STATIONARY_LAVA, Material.LEGACY_STATIONARY_WATER));
		
		Location loc = getLocation(tier.getWorld(), tier.getMaxX(), tier.getMaxZ());
		
		while((loc == null || disallowed.contains(loc.getBlock().getType()) || disallowed.contains(loc.getBlock().getRelative(BlockFace.UP).getType())) && (loc.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR && loc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR))
			loc = getLocation(tier.getWorld(), tier.getMaxX(), tier.getMaxZ());
		
		p.teleport(loc);
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.teleport")));
		
		main.economy.withdrawPlayer(p, tier.getCost()); //For safety reasons, we need to withdraw the money after the teleport.
	}

}
