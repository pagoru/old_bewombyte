package es.bewom;

import java.util.List;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;
import com.google.inject.Inject;
 
@Plugin(id="bewomByte", name="bewom byte", version="0.0a")
public class BewomByte {
	
	@Inject
	Logger log;
	
	@Inject
	private Game game;
	
	@Subscribe
	public void onInitialization(InitializationEvent e){
		
		game.getCommandDispatcher().register(this, new CommandCallable() {
						
			@Override
			public boolean testPermission(CommandSource source) {
				
				return false;
			}
			
			@Override
			public Optional<CommandResult> process(CommandSource source, String arguments) throws CommandException {
												
				return null;
			}
			
			@Override
			public Text getUsage(CommandSource source) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<String> getSuggestions(CommandSource source, String arguments) throws CommandException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Optional<? extends Text> getShortDescription(CommandSource source) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Optional<? extends Text> getHelp(CommandSource source) {
				// TODO Auto-generated method stub
				return null;
			}
		}, "kick"); 
		
	}
	
	@Subscribe
	public void onServerStart(ServerStartedEvent e){
		
		log.info(">>>>>>>>>>> bewom byte cargado con exito <<<<<<<<<<<");
		
	}
	
	@Subscribe
	public void onJoin(PlayerJoinEvent e){
		
		Text hola = Texts.of("Hola, que tal?");
		
		e.getUser().sendMessage(hola);
		
	}

}
