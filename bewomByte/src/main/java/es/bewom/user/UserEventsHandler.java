package es.bewom.user;

import java.util.UUID;

import org.spongepowered.api.Game;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.DisplayNameData;
import org.spongepowered.api.data.manipulator.tileentity.SignData;
import org.spongepowered.api.entity.EntityInteractionTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerMoveEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.event.entity.player.PlayerRespawnEvent;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Titles;

import com.google.common.base.Optional;

import es.bewom.user.messages.BewomMessageSink;

public class UserEventsHandler {
	
	private Game game;
	
	public UserEventsHandler(Game game) {
		this.game = game;
	}

	/**
	 * Event triggered when a player joins the server.
	 * 
	 * @param event
	 */
	@Subscribe
	public void onUserJoin(PlayerJoinEvent event) {
		
		Player player = event.getUser();
		
		BewomUser user = new BewomUser(player);
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
	
	@Subscribe
	public void onUserChat(PlayerChatEvent event) {
		BewomMessageSink sink = new BewomMessageSink();
		Text newMessage = sink.transformMessage(event.getSource(), event.getMessage());
		event.setNewMessage(newMessage);
	}

	/**
	 * Event triggered when a player leaves the server.
	 * 
	 * @param event
	 */
	@Subscribe
	public void onUserQuit(PlayerQuitEvent event) {
		Player player = event.getUser();
		UUID uuid = player.getUniqueId();
		BewomUser.remove(uuid);
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
		if (BewomUser.getUser(player.getUniqueId()).getRegistration() == WebRegistration.NOT_REGISTERED) {
			if (!event.getOldLocation().equals(event.getNewLocation())) {
				event.setNewLocation(event.getOldLocation());
				event.getUser().sendTitle(
						Titles.of(Texts.of(TextColors.RED, "PLEASE REGISTER"),
								Texts.of(TextColors.BLUE, "http://bewom.es")));
			}
		}
	}
	
	@Subscribe
	public void onUserRespawn(PlayerRespawnEvent event) {
		Player player = event.getUser();
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
