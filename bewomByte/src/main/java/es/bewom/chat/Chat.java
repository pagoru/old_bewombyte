package es.bewom.chat;

import java.util.Collection;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;

import es.bewom.BewomByte;
import es.bewom.user.BewomUser;
import es.bewom.user.WebRegistration;

public class Chat {
	
	private static Game game = BewomByte.game;
	private static Log l = new Log();
	
	public static void sendMessage(Player p, String m, Text t){
		
		if(t != null){
			
			Collection<Player> src = game.getServer().getOnlinePlayers();
			
			for(Player player : src) {
				
				if(BewomUser.getUser(player).getRegistration() == WebRegistration.VALID){
					player.sendMessage(t);					
				}
				
			}
			
		}
		
		l.add(p.getUniqueId(), m);
		
	}

}
