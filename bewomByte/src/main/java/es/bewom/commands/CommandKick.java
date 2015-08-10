package es.bewom.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.chat.Chat;
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
		
		String reason = "incumplir las normas.";
		
		if(args.getOne("razon").isPresent()) {
			reason = args.<String>getOne("razon").get();
		}
		
		TextBuilder builder = Texts.builder();
		builder.append(Texts.of(TextColors.RED, "Has sido advertido por "));
		builder.append(Texts.of(TextStyles.RESET, src.getName()));
		builder.append(Texts.of(TextColors.RED, " por ", reason));
		
		toKick.kick(builder.build());
		
		Text t = Texts.of(
				TextMessages.BROADCAST,
				TextStyles.RESET, toKick.getName(),
				TextStyles.BOLD, TextColors.DARK_RED, " ha sido advertido por ", 
				TextStyles.RESET, src.getName(),
				TextStyles.BOLD, TextColors.DARK_RED, " por ", reason, ".");
		Chat.sendMessage((Player) src, "/kick " + toKick.getName() + " (" + toKick.getUniqueId() + ") " + reason, t);
		
		return CommandResult.success();
	}

	
	
}
