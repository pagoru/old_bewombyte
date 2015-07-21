package es.bewom.centrospokemon.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.centrospokemon.CentroPokemon;

public class CommandCentro implements CommandExecutor {
	
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

		CentroPokemon cp = CentroManager.getClosest(player.getLocation(), player.getWorld().getName());
		if(cp == null) {
			player.sendMessage(Texts.of("There are no CPs near you."));
			return CommandResult.empty();
		}
		
		player.transferToWorld(cp.getWorld(), cp.getVector());
		player.sendMessage(Texts.of(TextColors.RED, "Teleport successful."));
		
		return CommandResult.success();
	}

}
