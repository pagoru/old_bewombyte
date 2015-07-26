package es.bewom.centrospokemon.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

import es.bewom.centrospokemon.CentroManager;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandQuitarCentro implements CommandExecutor {

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
		}
		
		Optional<String> error = CentroManager.remove(player.getLocation(), player.getWorld().getName());
		
		if(error.isPresent()) {
			player.sendMessage(Texts.of(TextColors.RED, error.get()));
			return CommandResult.empty();
		}
		
		player.sendMessage(Texts.of(TextColors.RED, "Centro quitado correctamente."));
		CentroManager.save();
		return CommandResult.success();
	}

}
