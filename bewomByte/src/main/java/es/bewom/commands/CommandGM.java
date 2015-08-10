package es.bewom.commands;

import java.util.Collections;
import java.util.List;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.entity.player.gamemode.GameMode;
import org.spongepowered.api.entity.player.gamemode.GameModes;
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

public class CommandGM implements CommandCallable {
	
	private final Optional<Text> desc = Optional.of(Texts.of("Cambia el gamemode."));
	private final Optional<Text> help = Optional.of(Texts.of("Cambia el gamemode. Gamemodes: SURVIVAL (0), CREATIVE (1), ADVENTURE (2), SPECTATOR (3)"));
	
	private final Text usage = Texts.of("<gamemode>");

	@Override
	public CommandResult process(CommandSource source, String arguments)
			throws CommandException {
		
		if(!(source instanceof Player)) {
			source.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
			return CommandResult.empty();
		}
		
		Player player = (Player) source;
		BewomUser user = BewomUser.getUser(player);
		if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			return CommandResult.empty();
		}
		
		GameMode gm = GameModes.NOT_SET;
		
		switch(arguments.toLowerCase()) {
		case "survival":
			gm = GameModes.SURVIVAL;
			break;
		case "s":
			gm = GameModes.SURVIVAL;
			break;
		case "0":
			gm = GameModes.SURVIVAL;
			break;
			
		case "creative":
			gm = GameModes.CREATIVE;
			break;
		case "c":
			gm = GameModes.CREATIVE;
			break;
		case "1":
			gm = GameModes.CREATIVE;
			break;
			
		case "adventure":
			gm = GameModes.ADVENTURE;
			break;
		case "a":
			gm = GameModes.ADVENTURE;
			break;
		case "2":
			gm = GameModes.ADVENTURE;
			break;
			
		case "spectator":
			gm = GameModes.SPECTATOR;
			break;
		case "3":
			gm = GameModes.SPECTATOR;
			break;
		default:
			source.sendMessage(Texts.of(TextColors.RED, "Usage: /gm <gamemode>"));
			return CommandResult.empty();
		}
		
		player.offer(player.getGameModeData().type().set(gm));
		player.sendMessage(Texts.of(TextColors.RED, "Gamemode actualizado"));
		
		return CommandResult.success();
	}

	@Override
	public List<String> getSuggestions(CommandSource source, String arguments)
			throws CommandException {
		return Collections.emptyList();
	}

	@Override
	public boolean testPermission(CommandSource source) {
		return true;
	}

	@Override
	public Optional<? extends Text> getShortDescription(CommandSource source) {
		return desc;
	}

	@Override
	public Optional<? extends Text> getHelp(CommandSource source) {
		return help;
	}

	@Override
	public Text getUsage(CommandSource source) {
		return usage;
	}
	
	
	
//	Game game;
//
//	public CommandGM(Game game) {
//		this.game = game;
//	}
//	
//	@Override
//	public CommandResult execute(CommandSource src, CommandContext args)
//			throws CommandException {
//		
//		if(src instanceof Player) {
//			BewomUser user = BewomUser.getUser((Player) src);
//			if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
//				GameMode mode = args.<GameMode>getOne("modo").get();
//				Player player = (Player) src;
//				player.offer(player.getGameModeData().setGameMode(mode));
//				return CommandResult.empty();
//			}
//		}
//		
//		src.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
//		
//		return CommandResult.empty();
//		
//	}

}
