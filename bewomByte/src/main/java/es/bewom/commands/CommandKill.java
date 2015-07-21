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

public class CommandKill implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		Player player = args.<Player>getOne("player").get();
		
		if(!player.isOnline()) {
			src.sendMessage(Texts.of("Player not found."));
			return CommandResult.empty();
		}
		
		player.sendMessage(Texts.of("You were murdered"));
		
		src.sendMessage(Texts.of(TextColors.RESET, "You killed ", TextColors.GREEN, player.getName()));
		
		HealthData health = player.getHealthData();
		health.setHealth(0);
		player.offer(health);
		
		return CommandResult.success();
	}

	
	
}
