package es.bewom.teleport.commands;

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
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandTPAHere implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			
			Player player1 = (Player) src;
			
			BewomUser user = BewomUser.getUser(player1);
			if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_VIP) {
				player1.sendMessage(TextMessages.NO_PERMISSIONS);
				return CommandResult.empty();
			}
			
			Player player2 = args.<Player>getOne("player").get();
			
			TPManager.newRequest(player1, player2, 1);
			
			src.sendMessage(Texts.of(TextColors.RED, "Teleport request sent."));
			player2.sendMessage(Texts.of(TextColors.GREEN, TextStyles.BOLD, player1.getName(), TextColors.RED, TextStyles.RESET, " quiere que te teletransportes a él:"));
			player2.sendMessage(Texts.of(TextColors.RED, "    Usa /tpaccept para aceptar la solicitud."));
			player2.sendMessage(Texts.of(TextColors.RED, "    Usa /tpdeny para denegar la solicitud."));
			
			return CommandResult.success();
			
		}
		
		src.sendMessage(Texts.of("This command is for players only."));
		return CommandResult.empty();
	}
	
	
	
}
