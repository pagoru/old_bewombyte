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
	
	public static Door d = new Door();
	
	public static void on(Game game, PlayerInteractBlockEvent event){
		
		Player p = (Player) event.getEntity();
		BlockType b = (BlockType) event.getBlock().getType();
		double x = event.getLocation().getX();
		double y = event.getLocation().getY();
		double z = event.getLocation().getZ();
		World world = p.getWorld();
		
		if(b != null){
			
			if(equalsAnyWoodenDoorTypes(b)){
				
				p.sendMessage(Texts.of("Door!"));
				
				if(d != null){
					if(d.setDoorPos(0).isSelected(x, y, z, world)){
						event.setCancelled(true);
						p.setLocation(d.setDoorPos(1).getLocation());
					}
					if(d.setDoorPos(1).isSelected(x, y, z, world)){
						event.setCancelled(true);
						p.setLocation(d.setDoorPos(0).getLocation());
					}
				}
				
				if(second){
					event.setCancelled(true);
					BlockType doorW = game.getServer().getWorld(world.getUniqueId()).get().getBlock((int) x, (int) y + 1, (int) z).getType();
					if(equalsAnyWoodenDoorTypes(doorW)){
						y -= 1;
					}
					d.setDoorPos(0).setLocation(x, y, z).setWorld(world);
					second = false;
					p.sendMessage(Texts.of("Selected doors."));
				}
				if(first){
					event.setCancelled(true);
					BlockType doorW = game.getServer().getWorld(world.getUniqueId()).get().getBlock((int) x, (int) y + 1, (int) z).getType();
					if(equalsAnyWoodenDoorTypes(doorW)){
						y -= 1;
					}
					d.setDoorPos(1).setLocation(x, y, z).setWorld(world);
					first = false;
					second = true;
					p.sendMessage(Texts.of("Select second door."));
				}
				
			}
			
		}
		
	}
	
	public static boolean equalsAnyWoodenDoorTypes(BlockType b){
		if(b == BlockTypes.WOODEN_DOOR
				|| b == BlockTypes.ACACIA_DOOR
				|| b == BlockTypes.BIRCH_DOOR
				|| b == BlockTypes.DARK_OAK_DOOR
				|| b == BlockTypes.JUNGLE_DOOR
				|| b == BlockTypes.SPRUCE_DOOR){
			return true;
		}
		return false;
	}
	
	public static void on(Game game, PlayerBreakBlockEvent event){
		
		Player p = (Player) event.getEntity();
		double x = event.getLocation().getX();
		double y = event.getLocation().getY();
		double z = event.getLocation().getZ();
		
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
