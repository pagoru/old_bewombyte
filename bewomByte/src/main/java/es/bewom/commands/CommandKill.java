package es.bewom.commands;

import org.spongepowered.api.data.manipulator.entity.HealthData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandKill implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			Player player = (Player) src;
			BewomUser user = BewomUser.getUser(player);
			if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
				src.sendMessage(TextMessages.NO_PERMISSIONS);
				return CommandResult.empty();
			}
		}
		
		Player toKill = args.<Player>getOne("player").get();
		
		if(!toKill.isOnline()) {
			src.sendMessage(Texts.of("Player not found."));
			return CommandResult.empty();
		}
		
		toKill.sendMessage(Texts.of("You were murdered"));
		
		src.sendMessage(Texts.of(TextColors.RESET, "You killed ", TextColors.GREEN, toKill.getName()));
		
		HealthData health = toKill.getHealthData();
		health.setHealth(0);
		toKill.offer(health);
		
		return CommandResult.success();
	}

	
	
}
