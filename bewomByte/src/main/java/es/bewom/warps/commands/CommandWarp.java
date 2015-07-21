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

import es.bewom.warps.Warp;
import es.bewom.warps.WarpManager;

public class CommandWarp implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {

		if (!(src instanceof Player)) {
			src.sendMessage(Texts.of("This command is for players only."));
			return CommandResult.empty();
		}

		Player player = (Player) src;
		String warpName = args.<String> getOne("name").get();

		Optional<Warp> warpOp = WarpManager.getWarpByName(warpName);

		if (!warpOp.isPresent()) {
			src.sendMessage(Texts.of(TextColors.RED, TextStyles.BOLD,
					"Error: ", TextStyles.RESET, "Warp not found."));
			return CommandResult.empty();
		}

		Warp warp = warpOp.get();

		player.transferToWorld(warp.getWorld(), warp.getVectorPos());
		player.setRotation(warp.getRotation());

		player.sendMessage(Texts.of(TextColors.RED, "Teleport successful."));

		return CommandResult.success();

	}
}
