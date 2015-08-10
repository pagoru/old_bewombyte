package es.bewom.centrospokemon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.world.Location;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;

public class CentroManager {
	
	public static ArrayList<CentroPokemon> centros = new ArrayList<>();
	
	public static Optional<String> add(Location location, String world) {
		if(centros.size() > 0) {
			for(CentroPokemon centro : centros) {
				if(centro.isEqualTo(location, world)) {
					return Optional.of("Location already used.");
				}
			}
		}
		centros.add(new CentroPokemon(location, world));
		return Optional.absent();
	}
	
	public static Optional<String> remove(Location location, String world) {
		if(centros.size() == 0) return Optional.of("No hay Centros Pokemon establecidos.");
		for(CentroPokemon centro : centros) {
			if(centro.isNear(location, world)) {
				centros.remove(centro);
				return Optional.absent();
			}
		}
		return Optional.of("No hay un centro poquemon en su posicion actual.");
	}
	
	public static CentroPokemon getClosest(Location location, String world) {
		if(centros.size() == 0) return null;
		CentroPokemon closest = centros.get(0);
		for(CentroPokemon centro : centros) {
			if(!centro.world.equals(world)){
				continue;
			}
			int dist1 = closest.distance(location);
			int dist2 = centro.distance(location);
			if(dist2 < dist1) {
				closest = centro;
			}
		}
		return closest;
	}
	
	/**
	 * Saves all {@link CentroPokemon} into a Json file.
	 */
	public static void save() {
		
		try {
		
			CentroPokemon[] centroArray = new CentroPokemon[centros.size()];
			for(int i = 0; i < centros.size(); i++) {
				centroArray[i] = centros.get(i);
			}
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/centros_pokemon.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(centroArray);
			
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			
			writer.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Loads all {@link CentroPokemon} from a Json file.
	 */
	public static void load() {
		
		try {
		
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/centros_pokemon.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			CentroPokemon[] centroArray = gson.fromJson(reader, CentroPokemon[].class);
			
			centros.clear();
			
			for(CentroPokemon centro : centroArray) {
				centros.add(centro);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void init(BewomByte plugin) {
		load();
	}
	
}
