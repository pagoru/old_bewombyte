package es.bewom.world;

import java.util.Collection;
import java.util.HashMap;

import org.spongepowered.api.Game;
import org.spongepowered.api.world.GeneratorTypes;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBuilder;

import com.google.common.base.Optional;

public class WorldManager {
	
	public static final String PIXELMON = "world";
	public static final String INTERIORES = "world_interiors";
	public static final String RECURSOS = "world_recursos";
	
	private static Game game;
	
	public static void init(Game game) {
		WorldManager.game = game;
		loadWorld(INTERIORES);
		loadWorld(RECURSOS);
	}
	
	public static void loadWorld(String worldName) {
		Optional<World> world = game.getServer().loadWorld(worldName);
		if(!world.isPresent()) {
			createWorld(worldName);
		}
	}
	
	private static void createWorld(String worldName) {
		
//		WorldBuilder builder = game.getServer().;
//		
//		switch(worldName) {
//		case INTERIORES:
//			builder.generator(GeneratorTypes.FLAT);
//			break;
//		case RECURSOS:
//			builder.generator(GeneratorTypes.DEFAULT);
//			break;
//		default:
//			break;
//		}
//		
//		Optional<World> world = builder.build();
//		if(!world.isPresent()) {
//			System.out.println("AN ERROR OCURRED CREATING A WORLD (" + worldName + ")");
//		}
		
	}
	
}
