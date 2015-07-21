package es.bewom.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.teleport.TPManager;

public class CommandTPA implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			
			Player player1 = (Player) src;
			Player player2 = args.<Player>getOne("player").get();
			
			TPManager.newRequest(player1, player2);
			
			src.sendMessage(Texts.of(TextColors.RED, "Teleport request sent."));
			player2.sendMessage(Texts.of(TextColors.GREEN, TextStyles.BOLD, player1.getName(), TextColors.RED, TextStyles.RESET, " wants to teleport to you:"));
			player2.sendMessage(Texts.of(TextColors.RED, "    Use /tpaccept to accept the request."));
			player2.sendMessage(Texts.of(TextColors.RED, "    Use /tpdeny"));
			
			return CommandResult.success();
			
		}
		
		src.sendMessage(Texts.of("This command is for players only."));
		return CommandResult.empty();
	}
	
	
	
}
