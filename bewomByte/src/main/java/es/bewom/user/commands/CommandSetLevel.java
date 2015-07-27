package es.bewom.user.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandSetLevel implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {

		if (src instanceof Player) {
			BewomUser user = BewomUser.getUser((Player) src);
			if (user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
				src.sendMessage(TextMessages.NO_PERMISSIONS);
				return CommandResult.empty();
			}
		}

		Optional<Player> toChangeOp = args.<Player> getOne("jugador");
		Optional<Integer> levelOp = args.<Integer> getOne("nivel");

		if (toChangeOp.isPresent() && levelOp.isPresent()) {

			Player toChange = toChangeOp.get();
			int level = levelOp.get();

			BewomUser user = BewomUser.getUser(toChange);
			Optional<String> error = user.setPermissionLevel(level);

			if (error.isPresent()) {
				src.sendMessage(Texts.of(error));
				return CommandResult.empty();
			}

			src.sendMessage(Texts.of(TextColors.RED, toChange.getName(), " ahora es nivel de permisos ", level));
			return CommandResult.success();

		}

		return CommandResult.empty();
	}

}
