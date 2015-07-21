package es.bewom.warps.commands;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.warps.WarpManager;

public class CommandWarpList implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		src.sendMessage(Texts.of(TextColors.RED, WarpManager.getAllWarpNames()));
		
		return CommandResult.success();
	}
	
	
	
}
