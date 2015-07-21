package es.bewom.spawn.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.spawn.SpawnManager;

public class CommandSetSpawn implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		Player player;
		
		if(src instanceof Player) {
			player = (Player) src;
		} else {
			src.sendMessage(Texts.of("This command is for players only."));
			return CommandResult.empty();
		}
		
		SpawnManager.setSpawn(player.getLocation(), player.getWorld().getName());
		SpawnManager.save();
		player.sendMessage(Texts.of(TextColors.RED, "Spawn set."));
		
		return CommandResult.success();
	}
	
	
	
}
