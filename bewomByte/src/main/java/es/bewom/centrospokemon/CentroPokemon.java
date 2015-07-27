package es.bewom.centrospokemon;

import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

public class CentroPokemon {
	
	public static String DEFAULT_WORLD = "world";
	
	public int x, y, z;
	public String world;
	
	public CentroPokemon(int x, int y, int z, String world) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
	}
	
	public CentroPokemon(Location location, String world) {
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
		this.world = world;
	}

	public boolean isEqualTo(Location location, String world) {
		if(location.getBlockX() == this.x
				&& location.getBlockY() == this.y
				&& location.getBlockZ() == this.z
				&& world == this.world) {
			return true;
		}
		return false;
	}
	
	public boolean isNear(Location location, String world) {
		if(!this.world.equals(world)) return false;
		if(location.getPosition().distance(getVector()) < 1) {
			return true;
		}
		return false;
	}
	
	public int distance(Location location) {
		Vector3i pos = new Vector3i(x, y, z);
		Vector3i loc = new Vector3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		return Math.abs(pos.distance(loc));
	}
	
	public String getWorld() {
		return world;
	}
	
	public Vector3d getVector() {
		return new Vector3d(x, y, z);
	}

}
