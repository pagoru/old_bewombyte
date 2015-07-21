package es.bewom.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class CommandKick implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		Player player = args.<Player>getOne("player").get();
		
		String reason = null;
		
		if(args.getOne("reason").isPresent()) {
			reason = args.<String>getOne("reason").get();
		}
		
		player.kick(Texts.of("You were kicked by " + src.getName() + ((reason != null) ? " because " + reason : "")));
		
		src.sendMessage(Texts.of("You kicked " + player.getName() + " because " + reason));
		
		return CommandResult.success();
	}

	
	
}
