package es.bewom.teleport.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.teleport.TPManager;
import es.bewom.teleport.TPRequest;
import es.bewom.texts.TextMessages;

public class CommandTPADeny implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			
			Player player2 = (Player) src;
			
			TPRequest request = TPManager.getRequest(player2);
			
			if(request == null) {
				src.sendMessage(TextMessages.TP_NOT_FOUND);
				return CommandResult.empty();
			}
			
			Player player1 = request.getPlayer1();
			
			TPManager.deleteRequest(request);
			src.sendMessage(TextMessages.TP_DENIED);
			player1.sendMessage(TextMessages.TP_DENIED);
			
			return CommandResult.success();
			
		}
		
		src.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
		return CommandResult.empty();
	}
	
}
