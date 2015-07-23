package es.bewom.economy;

import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;

public class EconomyManager {
	
	@Subscribe(order = Order.FIRST)
	public void onPlayerInteractDoor(PlayerInteractBlockEvent event) {
		event.setCancelled(true);
	}
	
}
