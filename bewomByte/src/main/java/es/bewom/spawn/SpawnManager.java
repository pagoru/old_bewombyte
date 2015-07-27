package es.bewom.spawn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SpawnManager {
	
	public static Spawn spawn;
	
	public static Spawn getSpawn() {
		return spawn;
	}
	
	public static void setSpawn(Spawn spawn) {
		SpawnManager.spawn = spawn;
	}
	
	public static void setSpawn(Location location, String world) {
		Spawn spawn = new Spawn(location, world);
		setSpawn(spawn);
	}
	
	public static void setSpawn(int x, int y, int z, String world) {
		Spawn spawn = new Spawn(x, y, z, world);
		setSpawn(spawn);
	}
	
	public static void save() {
		
		try {
		
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/spawn.json");
			if(!file.exists()) file.createNewFile();
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(spawn);
			
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void load() {
		
		try {
			
			File folder = new File("bewom");
			if(!folder.exists()) folder.mkdirs();
			
			File file = new File("bewom/spawn.json");
			if(!file.exists()) file.createNewFile();
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			spawn = gson.fromJson(reader, Spawn.class);
			
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static class Spawn {
		
		public int x, y, z;
		public String world;
		
		public Spawn(int x, int y, int z, String world) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public Spawn(Location location, String world) {
			this.x = location.getBlockX();
			this.y = location.getBlockY();
			this.z = location.getBlockZ();
			this.world = world;
		}
		
		public void setWorld(String world) {
			this.world = world;
		}
		
		public Vector3d getVector() {
			return new Vector3d(x, y, z);
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public int getZ() {
			return z;
		}
		
		public String getWorld() {
			return world;
		}
		
	}
	
}
