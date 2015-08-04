package es.bewom.user;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.spongepowered.api.Game;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.tileentity.SignData;
import org.spongepowered.api.entity.EntityInteractionTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.player.gamemode.GameModes;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerMoveEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.event.entity.player.PlayerRespawnEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
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
			player.sendTitle(
				Titles.builder()
					.title(Texts.of(TextColors.DARK_AQUA, "¡Bienvenido " + player.getName() + "!"))
					.subtitle(Texts.of(TextColors.WHITE, "¡Hazte con todos!"))
					.stay(120)
					.build());
			user.updatePermissions();
		} else if (user.getRegistration() == WebRegistration.NOT_VALID) {
			//Player is not registered.
			//Warning message.
			
			player.sendTitle(
				Titles.builder()
					.title(Texts.of(TextColors.DARK_RED, "¡Verifica tu correo!"))
					.subtitle(Texts.of(TextColors.WHITE, "Si no encuentras el correo, busca en spam..."))
					.stay(72000)
					.build());
			
			player.offer(event.getUser().getGameModeData().setGameMode(GameModes.SPECTATOR));
			
		} else if (user.getRegistration() == WebRegistration.NOT_REGISTERED) {
			//Player is not registered.
			//Warning message.
			user.createHashFirstTime();
			
			player.sendTitle(
				Titles.builder()
					.title(Texts.of(TextColors.DARK_RED, "Porfavor, registrate!"))
					.subtitle(Texts.of(TextColors.WHITE, "Haz click en el link del chat..."))
					.stay(72000)
					.build());
			try {
				player.sendMessage(Texts.builder().append(Texts.of(TextColors.DARK_AQUA, "http://bewom.es/crear")).onClick(TextActions.openUrl(new URL(user.getRegisterLink()))).build());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			event.getUser().offer(event.getUser().getGameModeData().setGameMode(GameModes.SPECTATOR));
			
		} else if (user.getRegistration() == WebRegistration.BANNED) {
			//Player has been banned from the server.
			//Go to forums message.
			user.updatePermissions();
			player.offer(player.getGameModeData().setGameMode(GameModes.SPECTATOR));
		}

	}
	
	@Subscribe
	public void onUserChat(PlayerChatEvent event) {
		BewomUser b = BewomUser.getUser(event.getUser());
		
		if (b.getRegistration() == WebRegistration.VALID) {
			//Player is allowed into the server.
			//Welcome message.
			BewomMessageSink sink = new BewomMessageSink();
			Text newMessage = sink.transformMessage(event.getSource(), event.getMessage());
			event.setNewMessage(newMessage);
		} else {
			event.setCancelled(true);
		}
		
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
		BewomUser b = BewomUser.getUser(event.getUser());
		
		if (b.getRegistration() != WebRegistration.VALID) {
			event.setCancelled(true);
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
