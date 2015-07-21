package es.bewom.teleport;

import org.spongepowered.api.entity.player.Player;

public class TPRequest {
	
	private Player player1;
	private Player player2;
	
	private long time;
	
	private static final long expiration = 10000;
	
	public TPRequest() {
		time = System.currentTimeMillis();
	}
	
	public void setPlayer1(Player player) {
		player1 = player;
	}
	
	public void setPlayer2(Player player) {
		player2 = player;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public boolean isStillVaild() {
		
		long currentTime = System.currentTimeMillis();
		
		if(currentTime - time < expiration)
			return true;
		
		return false;
		
	}
	
}
