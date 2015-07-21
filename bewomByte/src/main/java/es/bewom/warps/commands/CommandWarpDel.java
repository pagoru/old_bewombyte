package es.bewom.warps.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

import es.bewom.warps.WarpManager;

public class CommandWarpDel implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(!(src instanceof Player)) {
			src.sendMessage(Texts.of("This command is for players only."));
			return CommandResult.empty();
		}
		
		Player player = (Player) src;
		String warpName = args.<String>getOne("name").get();
		
		Optional<String> error = WarpManager.deleteWarp(warpName);
		
		if(error.isPresent()) {
			player.sendMessage(Texts.of(TextColors.RED, TextStyles.BOLD, "Error: ", TextStyles.RESET, error.get()));
			return CommandResult.empty();
		}
		
		player.sendMessage(Texts.of(TextColors.RED, "Warp deleted successfully."));
		WarpManager.save();
		return CommandResult.success();
	}

}
