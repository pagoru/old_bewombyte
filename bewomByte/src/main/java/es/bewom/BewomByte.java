package es.bewom;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.commands.Commands;
import es.bewom.spawn.SpawnManager;
import es.bewom.teleport.TPManager;
import es.bewom.user.BewomUser;
import es.bewom.user.UserEventsHandler;
import es.bewom.warps.WarpManager;
import es.bewom.world.WorldManager;

/**
 * 
 * Main plugin class. Here is where the magic happens.
 * @author Pagoru & McMacker4
 *
 */

@Plugin(id="bewomByte", name="bewom byte", version="0.0b")
public class BewomByte {
	
	@Inject
	Game game;
	
	@Inject
	Logger log;
	
	/**
	 * Runs when the plugin is initializing.
	 * @param e the {@link InitializationEvent}.
	 */
	@Subscribe
	public void onInitialization(InitializationEvent e) {
		
		log.debug("BewomByte Loading.");
		
		game = e.getGame();
		
		log.debug("Loading BewomByte commands.");
		
		Commands commands = new Commands(this, game);
		commands.registerAll();
		
		log.debug("Loading BewomByte events.");
		
		BewomUser.setGame(this);
		TPManager.init(this);
		SpawnManager.load();
		WarpManager.load();
		CentroManager.init(this);
		
		game.getEventManager().register(this, new UserEventsHandler(game));
		game.getEventManager().register(this, new WarpManager());
		game.getEventManager().register(this, new CentroManager());
		
	}
	
	/**
	 * Runs when the server is stopping.
	 * @param e the event triggered.
	 */
	@Subscribe
	public void onServerClosing(ServerStoppingEvent e) {
		WarpManager.save();
		CentroManager.save();
	}
	
	/**
	 * Returns the current {@link Game}.
	 * @return The current {@link Game}.
	 */
	public Game getGame() {
		return game;
	}

}
