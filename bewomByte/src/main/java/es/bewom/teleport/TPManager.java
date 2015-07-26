package es.bewom.teleport;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.entity.player.Player;

import es.bewom.BewomByte;

public class TPManager implements Runnable {
	
	private static ArrayList<TPRequest> requests;
	
	public static void init(BewomByte plugin) {
		
		requests = new ArrayList<>();
		
		plugin.getGame()
				.getScheduler()
				.getTaskBuilder()
				.delay(5, TimeUnit.MINUTES)
				.interval(5, TimeUnit.MINUTES)
				.execute(new TPManager())
				.name("tpmanager")
				.submit(plugin);
		
	}
	
	public static void newRequest(Player player1, Player player2, int objective) {
		
		if(requests.size() != 0) {
		
			for(int i = 0; i < requests.size(); i++) {
				
				TPRequest request = requests.get(i);
				
				if(request.getPlayer1().equals(player1))
					requests.remove(request);
				if(request.getPlayer2().equals(player2))
					requests.remove(request);
			}
		
		}
		
		TPRequest request = new TPRequest();
		request.setPlayer1(player1);
		request.setPlayer2(player2);
		request.setObjective(objective);
		requests.add(request);
		
	}
	
	public static boolean validateRequest(TPRequest request) {
		
		if(request.isStillVaild()) {
			return true;
		} else {
			requests.remove(request);
		}
		
		return false;
		
	}
	
	public static TPRequest getRequest(Player player2) {
		
		if(requests.size() == 0) return null;
		
		for(int i = 0; i < requests.size(); i++) {
			
			TPRequest request = requests.get(i);
			
			if(request.getPlayer2().equals(player2)){
				return request;
			}
		}
		
		return null;
		
	}
	
	public static void deleteRequest(TPRequest request) {
		requests.remove(request);
	}
	
	public static void periodicCleanup() {
		for(int i = 0; i < requests.size(); i++) {
			TPRequest request = requests.get(i);
			if(!request.isStillVaild()) {
				requests.remove(request);
			}
		}
	}

	@Override
	public void run() {
		periodicCleanup();
	}
	
}
