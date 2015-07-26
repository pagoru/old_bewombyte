package es.bewom.economy;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.tileentity.SignData;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.base.Optional;

public class EconomyManager {
	
	@Subscribe
	public void onPlayerInteractDoor(PlayerInteractBlockEvent event) {
		
	}
	
	@Subscribe
	public void onInteracrSign(PlayerInteractBlockEvent event) {
		Optional<TileEntity> entityOp = event.getBlock().getTileEntity();
		if(entityOp.isPresent()) {
			TileEntity entity = entityOp.get();
			Optional<SignData> dataOp = entity.getOrCreate(SignData.class);
			if(dataOp.isPresent()) {
				SignData data = dataOp.get();
				data.reset();
				data.setLine(1, Texts.of(TextColors.RED, "Hello World!"));
				entity.offer(data);
			}
		}
	}
	
}
