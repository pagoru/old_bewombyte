package es.bewom.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.p.P;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandP implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Player player = (Player) src;
		BewomUser user = BewomUser.getUser(player);
		
		if(user.isAdmin()){
			
			player.sendMessage(Texts.of("Select first door."));
			P.first = true;
			
			return CommandResult.success();
			
		}
		
		player.sendMessage(TextMessages.NO_PERMISSIONS);
		return CommandResult.empty();
	}

}
