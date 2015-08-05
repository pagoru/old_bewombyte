package es.bewom.user;

import org.spongepowered.api.Game;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.entity.player.PlayerPlaceBlockEvent;

public class DeniedBlocks {
	
	public static final BlockType[] DENIED = {
			BlockTypes.PISTON,
			BlockTypes.PISTON_EXTENSION,
			BlockTypes.PISTON_HEAD,
			BlockTypes.STICKY_PISTON,
			BlockTypes.DISPENSER,
			BlockTypes.DROPPER,
			BlockTypes.TNT
			
	};
	
	public static void on(Game game, PlayerPlaceBlockEvent event){
		
		BlockType b = (BlockType) event.getBlock().getBlockType();
		
		if(b != null){
			
			for (int i = 0; i < DENIED.length; i++) {
				
				if(b == DENIED[i]){
					
					event.setCancelled(true);
					break;
					
				}
				
			}
			
		}
		
	}

}
