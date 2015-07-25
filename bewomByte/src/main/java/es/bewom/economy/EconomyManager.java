package es.bewom.economy;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.block.HingeData;
import org.spongepowered.api.data.type.Hinges;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;

import com.google.common.base.Optional;

public class EconomyManager {
	
	@Subscribe(order = Order.POST)
	public void onPlayerInteractDoor(PlayerInteractBlockEvent event) {
		Optional<TileEntity> entityOp = event.getBlock().getTileEntity();
		if(entityOp.isPresent()) {
			TileEntity entity = entityOp.get();
			Optional<HingeData> dataOp = entity.getOrCreate(HingeData.class);
			if(dataOp.isPresent()) {
				HingeData data = dataOp.get();
				if(data.getValue() == Hinges.RIGHT) {
					data.setValue(Hinges.LEFT);
				} else {
					data.setValue(Hinges.RIGHT);
				}
			}
		}
	}
	
}
