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
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandSpawn implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		Player player;
		
		if(src instanceof Player) {
			player = (Player) src;
		} else {
			src.sendMessage(TextMessages.NOT_CONSOLE_COMPATIBLE);
			return CommandResult.empty();
		}
		
		BewomUser user = BewomUser.getUser(player);
		if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
			player.sendMessage(TextMessages.NO_PERMISSIONS);
			return CommandResult.empty();
		}
		
		if(SpawnManager.getSpawn() == null) {
			player.sendMessage(Texts.of(TextColors.RED, "The spawn doesn't exist."));
			return CommandResult.empty();
		}
		
		player.transferToWorld(SpawnManager.getSpawn().getWorld(), SpawnManager.getSpawn().getVector());
		player.sendMessage(Texts.of(TextColors.RED, "Teleport successful."));
		
		return CommandResult.success();
	}
	
}
