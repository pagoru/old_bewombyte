package es.bewom.warps;

import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

public class Warp {
	
	String name;
	int x, y, z;
	double rx, ry, rz;
	String world;
	
	public Warp(String name, Vector3i position, Vector3d rotation, String world) {
		this.name = name;
		this.world = world;
		this.x = position.getX();
		this.y = position.getY();
		this.z = position.getZ();
		this.rx = rotation.getX();
		this.ry = rotation.getY();
		this.rz = rotation.getZ();
	}
	
	public Warp(String name, Location location, Vector3d rotation, String world) {
		this.name = name;
		this.world = world;
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
		this.rx = rotation.getX();
		this.ry = rotation.getY();
		this.rz = rotation.getZ();
	}
	
	public boolean isPositionSame(double x, double y, double z) {
		if(this.x == x && this.y == y && this.z == z) {
			return true;
		}
		return false;
	}
	
	public Vector3d getVectorPos() {
		return new Vector3d(x, y, z);
	}
	
	public Vector3d getRotation() {
		return new Vector3d(rx, ry, rz);
	}
	
	public String getName() {
		return name;
	}
	
	public String getWorld() {
		return world;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
}
