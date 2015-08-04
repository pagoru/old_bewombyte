package es.bewom.commands;

import java.util.Collection;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.user.BewomUser;

public class CommandSay implements CommandExecutor {
	
	private Game game;
	
	public CommandSay(Game game) {
		this.game = game;
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			BewomUser user = BewomUser.getUser((Player) src);
			if(src.hasPermission("any") || user.getPermissionLevel() >= BewomUser.PERM_LEVEL_ADMIN) {
				String message = args.<String>getOne("mensaje").get();
				Collection<Player> players = game.getServer().getOnlinePlayers();
				for(Player player : players) {
					player.sendMessage(ChatTypes.SYSTEM, Texts.of(
							TextColors.WHITE, "/", 
							TextColors.DARK_RED,
							TextStyles.OBFUSCATED, "K",
							TextColors.WHITE,
							TextStyles.RESET, "/", 
							TextStyles.BOLD, TextColors.DARK_RED, "WOM", 
							TextStyles.RESET, TextColors.WHITE, " < ",
							TextStyles.BOLD, TextColors.DARK_RED, message));
				}
				return CommandResult.success();
			}
		}

		return CommandResult.empty();
		
	}

}
