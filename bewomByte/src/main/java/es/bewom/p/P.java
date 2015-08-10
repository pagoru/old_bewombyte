package es.bewom.p;

import org.spongepowered.api.Game;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.entity.player.PlayerBreakBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class P {
	
	public static boolean first = false;
	public static boolean second = false;
	
	public static World firstWorld;
	public static double firstX;
	public static double firstY;
	public static double firstZ;

	public static World secondWorld;
	public static double secondX;
	public static double secondY;
	public static double secondZ;
	
	public static void on(Game game, PlayerInteractBlockEvent event){
		
		Player p = (Player) event.getEntity();
		BlockType b = (BlockType) event.getBlock().getBlockType();
		double x = event.getBlock().getX();
		double y = event.getBlock().getY();
		double z = event.getBlock().getZ();
		World world = p.getWorld();
		
		if(b != null){
			
			if(b == BlockTypes.WOODEN_DOOR
					|| b == BlockTypes.ACACIA_DOOR
					|| b == BlockTypes.BIRCH_DOOR
					|| b == BlockTypes.DARK_OAK_DOOR
					|| b == BlockTypes.JUNGLE_DOOR
					|| b == BlockTypes.SPRUCE_DOOR){
				
				p.sendMessage(Texts.of("Door!"));
				
				if(x == firstX && (y == firstY || y == firstY + 1) && z == firstZ && world.equals(firstWorld)){
					event.setCancelled(true);
					p.setLocation(new Location(secondWorld, secondX + 0.5, secondY, secondZ + 0.5));									
				}
				
				if(x == secondX && (y == secondY || y == secondY + 1) && z == secondZ && world.equals(secondWorld)){
					event.setCancelled(true);
					p.setLocation(new Location(firstWorld, firstX + 0.5, firstY, firstZ + 0.5));								
				}
				
				if(second){
					event.setCancelled(true);
					secondX = x;
					secondY = y;
					secondZ = z;
					secondWorld = world;
					second = false;
					p.sendMessage(Texts.of("Selected doors."));
				}
				if(first){
					event.setCancelled(true);
					firstX = x;
					firstY = y;
					firstZ = z;
					firstWorld = world;
					first = false;
					second = true;
					p.sendMessage(Texts.of("Select second door."));
				}
				
			}
			
		}
		
	}
	
	public static void on(Game game, PlayerBreakBlockEvent event){
		
		Player p = (Player) event.getEntity();
		double x = event.getBlock().getX();
		double y = event.getBlock().getY();
		double z = event.getBlock().getZ();
		
		BlockType b = game.getServer().getWorld(p.getWorld().getUniqueId()).get().getBlock((int) x, (int) y + 1, (int) z).getType();
		
//		BlockType b = (BlockType) event.getBlock().getBlockType();
		if(b != null){
			
			if(b == BlockTypes.WOODEN_DOOR
					|| b == BlockTypes.ACACIA_DOOR
					|| b == BlockTypes.BIRCH_DOOR
					|| b == BlockTypes.DARK_OAK_DOOR
					|| b == BlockTypes.JUNGLE_DOOR
					|| b == BlockTypes.SPRUCE_DOOR){
				
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
}
