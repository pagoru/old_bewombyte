package es.bewom.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandKick implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			Player player = (Player) src;
			BewomUser user = BewomUser.getUser(player);
			if(user.getPermissionLevel() < BewomUser.PERM_LEVEL_ADMIN) {
				player.sendMessage(TextMessages.NO_PERMISSIONS);
				return CommandResult.empty();
			}
		}
		
		Player toKick = args.<Player>getOne("jugador").get();
		
		String reason = null;
		
		if(args.getOne("razon").isPresent()) {
			reason = args.<String>getOne("razon").get();
		}
		
		TextBuilder builder = Texts.builder();
		builder.append(Texts.of(TextColors.RED, "Has sido advertido por "));
		builder.append(Texts.of(TextColors.GOLD, src.getName()));
		builder.append(Texts.of(TextColors.RED, ". Razon: ", reason));
		
		toKick.kick(builder.build());
		
		src.sendMessage(Texts.of(TextColors.RED, "Has advertido a ", TextColors.BLACK, toKick.getName(), TextColors.RED, ". Razon: ", TextColors.DARK_GRAY + reason));
		
		return CommandResult.success();
	}

	
	
}
