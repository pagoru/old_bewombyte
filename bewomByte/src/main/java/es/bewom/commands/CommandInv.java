package es.bewom.commands;

import java.util.Collections;
import java.util.List;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandCallable;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.base.Optional;

import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandInv implements CommandCallable {
	
	private Game game;
	
	public CommandInv(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult process(CommandSource source, String arguments)
			throws CommandException {
		
		//TODO: Arreglarlo. No funciona.
		
		source.sendMessage(Texts.of("Comando en construccion."));
		
		if(!(source instanceof Player)) {
			source.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
			return CommandResult.empty();
		}
		
		Player player = (Player) source;
		
		if(arguments.split(" ").length > 1) {
			source.sendMessage(Texts.of(TextColors.RED, "Uso: /inv <jugador>"));
			return CommandResult.empty();
		}
		
		Optional<Player> toWatchOp = game.getServer().getPlayer(arguments);
		if(!toWatchOp.isPresent()) {
			source.sendMessage(Texts.of(TextColors.RED, "No se ha encontrado el jugador."));
			return CommandResult.empty();
		}
		
		Player toWatch = toWatchOp.get();
		player.openInventory(toWatch.getInventory());
		
		return null;
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments)
			throws CommandException {
		return Collections.emptyList();
	}

	@Override
	public boolean testPermission(CommandSource source) {
		if(source instanceof Player) {
			if(BewomUser.getUser(((Player) source).getUniqueId()).getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public Optional<? extends Text> getShortDescription(CommandSource source) {
		return Optional.of(Texts.of("Abrir el inventario de alguien."));
	}

	@Override
	public Optional<? extends Text> getHelp(CommandSource source) {
		return Optional.of(Texts.of("Abrir el inventario de alguien."));
	}

	@Override
	public Text getUsage(CommandSource source) {
		return Texts.of("<jugador>");
	}

}
