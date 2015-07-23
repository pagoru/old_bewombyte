package es.bewom.user;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.tileentity.SignData;
import org.spongepowered.api.entity.EntityInteractionTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerMoveEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Titles;

import com.google.common.base.Optional;

import es.bewom.BewomByte;

public class UserEventsHandler {
	
	private BewomByte plugin;
	
	public UserEventsHandler(BewomByte plugin) {
		this.plugin = plugin;
	}

	/**
	 * Event triggered when a player joins the server.
	 * 
	 * @param event
	 */
	@Subscribe
	public void onUserJoin(PlayerJoinEvent event) {
		
		BewomUser user = new BewomUser(event.getUser());
		BewomUser.addUser(user);
		
		//TODO: set messages for each type of join event
		
		if (user.getRegistration() == WebRegistration.VALID) {
			//Player is allowed into the server.
			//Welcome message.
		} else if (user.getRegistration() == WebRegistration.NOT_REGISTERED) {
			//Player is not registered.
			//Warning message.
		} else if (user.getRegistration() == WebRegistration.BANNED) {
			//Player has been banned from the server.
			//Go to forums message.
		}

	}

	/**
	 * Event triggered when a player leaves the server.
	 * 
	 * @param event
	 */
	@Subscribe
	public void onUserQuit(PlayerQuitEvent event) {
		BewomUser.remove(BewomUser.getUser(event.getEntity()));
	}

	/**
	 * Event triggered when a player moves. If the player is not registered in
	 * the website he cannot move.
	 * 
	 * @param event
	 */
	@Subscribe
	public void onUserMoved(PlayerMoveEvent event) {
		Player player = event.getUser();
		if (BewomUser.getUser(player).getRegistration() == WebRegistration.NOT_REGISTERED) {
			if (!event.getOldLocation().equals(event.getNewLocation())) {
				event.setNewLocation(event.getOldLocation());
				event.getUser().sendTitle(
						Titles.of(Texts.of(TextColors.RED, "PLEASE REGISTER"),
								Texts.of(TextColors.BLUE, "http://bewom.es")));
			}
		}
	}
	
	/**
	 * Event triggered when a player right clicks a sign. 
	 * @param event
	 */
	@Subscribe
	public void onUserClickSign(PlayerInteractBlockEvent event)	 {
		
		if(!event.getInteractionType().equals(EntityInteractionTypes.USE)) return;
		
		Optional<TileEntity> entityOp = event.getBlock().getTileEntity();
		
		if(!entityOp.isPresent()) return;
		
		TileEntity entity = entityOp.get();
		
		Optional<SignData> dataOp = entity.getOrCreate(SignData.class);
		
		if(dataOp.isPresent()) {
			SignData data = dataOp.get();
			data.reset();
			data.setLine(1, Texts.of(TextColors.BLUE, "Hello World!"));
			entity.offer(data);
		}
		
	}
	
}
