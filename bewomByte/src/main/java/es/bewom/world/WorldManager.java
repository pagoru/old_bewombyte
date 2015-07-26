package es.bewom.world;

import org.spongepowered.api.Game;

public class WorldManager {

	private static Game game;
	
	public static void init(Game game) {
		WorldManager.game = game;
	}
	
}
