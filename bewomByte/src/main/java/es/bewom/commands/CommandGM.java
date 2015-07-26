package es.bewom.commands;

import org.spongepowered.api.Game;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandGM implements CommandExecutor {
	
	Game game;

	public CommandGM(Game game) {
		this.game = game;
	}
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			BewomUser user = BewomUser.getUser((Player) src);
			if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
				src.sendMessage(TextMessages.NO_PERMISSIONS);
				return CommandResult.empty();
			}
		}
		
		Optional<Integer> modeOp = args.<Integer>getOne("mode");
		
		if(modeOp.isPresent()) {
			game.getCommandDispatcher().process(src, "gamemode" + modeOp.get());
		}
		
		return CommandResult.empty();
		
	}

}
