package es.bewom.p;

import org.spongepowered.api.Game;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.entity.player.PlayerBreakBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.world.Location;

public class P {
	
	public static void on(Game game, PlayerInteractBlockEvent event){
		
		Player p = (Player) event.getEntity();
		BlockType b = (BlockType) event.getBlock().getBlockType();
		double x = event.getBlock().getX();
		double y = event.getBlock().getY();
		double z = event.getBlock().getZ();
		
		if(b != null){
			
			if(b == BlockTypes.WOODEN_DOOR
					|| b == BlockTypes.ACACIA_DOOR
					|| b == BlockTypes.BIRCH_DOOR
					|| b == BlockTypes.DARK_OAK_DOOR
					|| b == BlockTypes.JUNGLE_DOOR
					|| b == BlockTypes.SPRUCE_DOOR){
				
				event.getEntity().sendMessage(Texts.of("Door"));
				if(x == 134 && (y == 64 || y == 65) && z == 293){
					p.setLocation(new Location(p.getWorld(), 134.5, 64, 295.5));									
				}
				event.setCancelled(true);
				
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
				
				event.getEntity().sendMessage(Texts.of("Door Up"));
				event.setCancelled(true);
				
			}
			
		}
		
	}
	
}
