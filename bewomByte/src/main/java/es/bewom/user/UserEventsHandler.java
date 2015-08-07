package es.bewom.user;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.spongepowered.api.Game;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.tileentity.SignData;
import org.spongepowered.api.entity.EntityInteractionTypes;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.player.gamemode.GameModes;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerBreakBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerChangeBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerMoveEvent;
import org.spongepowered.api.event.entity.player.PlayerPlaceBlockEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.event.entity.player.PlayerRespawnEvent;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Literal;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Titles;
import org.spongepowered.api.world.Location;

import com.google.common.base.Optional;

import es.bewom.BewomByte;
import es.bewom.centrospokemon.CentroManager;
import es.bewom.centrospokemon.CentroPokemon;
import es.bewom.chat.Chat;
import es.bewom.p.P;
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
		CentroPokemon cp = CentroManager.getClosest(player.getLocation(), player.getWorld().getName());
		if(cp == null) {
			return;
		}
		Location location = new Location(BewomByte.game.getServer().getWorld(cp.world).get().getLocation(cp.getVector()).getExtent(), cp.getVector().add(0.5, 0, 0.5));
		event.setLocation(location);
		
		BewomUser user = new BewomUser(player);
		BewomUser.addUser(user);
		
		Chat.sendMessage(player, "//login", null);
		
		if (user.getRegistration() == WebRegistration.VALID) {
			player.sendTitle(
				Titles.builder()
					.title(Texts.of(TextColors.DARK_AQUA, "Bienvenid@!"))
					.subtitle(Texts.of(TextColors.WHITE, "Hazte con todos..."))
					.stay(120)
					.build());
			user.updatePermissions();
		} else if (user.getRegistration() == WebRegistration.NOT_VALID) {
			player.sendTitle(
				Titles.builder()
					.title(Texts.of(TextColors.DARK_RED, "Verifica tu correo!"))
					.subtitle(Texts.of(TextColors.WHITE, "Si no encuentras el correo, busca en spam..."))
					.stay(72000)
					.build());
			
			player.offer(event.getUser().getGameModeData().setGameMode(GameModes.SPECTATOR));
			
		} else if (user.getRegistration() == WebRegistration.NOT_REGISTERED) {
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
				e.printStackTrace();
			}
			event.getUser().offer(event.getUser().getGameModeData().setGameMode(GameModes.SPECTATOR));
			
		} else if (user.getRegistration() == WebRegistration.BANNED) {
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
			Text newMessage = sink.transformMessage(event.getEntity(), event.getMessage());
			Chat.sendMessage(event.getEntity(), Texts.toPlain(event.getUnformattedMessage()), newMessage);			
		}
		
		event.setCancelled(true);
		
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
		BewomUser u = BewomUser.getUser(uuid);
		if(u != null){
			
			if(!u.isLogout()){
				
				Chat.sendMessage(player, "//logout", null);
				u.setLogout(true);
				u.remove();
				
			}
			
		}
		
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
		CentroPokemon cp = CentroManager.getClosest(player.getLocation(), player.getWorld().getName());
		if(cp == null) {
			return;
		}
		Location location = new Location(BewomByte.game.getServer().getWorld(cp.world).get().getLocation(cp.getVector()).getExtent(), cp.getVector().add(0.5, 0, 0.5));
		event.setNewRespawnLocation(location);
	}
	
	@Subscribe
	public void on(PlayerInteractBlockEvent event){
		
		P.on(game, event);
		
	}
	
	@Subscribe
	public void on(PlayerPlaceBlockEvent event){
		
		DeniedBlocks.on(game, event);
		
	}
	
	@Subscribe
	public void on(PlayerBreakBlockEvent event){
		
		P.on(game, event);
		
	}
	
}
