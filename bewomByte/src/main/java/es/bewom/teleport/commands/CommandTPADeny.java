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

public class CommandTPADeny implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			
			Player player2 = (Player) src;
			
			TPRequest request = TPManager.getRequest(player2);
			
			if(request == null) {
				src.sendMessage(Texts.of(TextColors.RED, "No teleport request found."));
				return CommandResult.empty();
			}
			
			Player player1 = request.getPlayer1();
			
			TPManager.deleteRequest(request);
			src.sendMessage(Texts.of(TextColors.RED, "Teleport request denied."));
			player1.sendMessage(Texts.of(TextColors.RED, "Teleport request denied."));
			
			return CommandResult.success();
			
		}
		
		src.sendMessage(Texts.of("This command is player only."));
		return CommandResult.empty();
	}
	
}
