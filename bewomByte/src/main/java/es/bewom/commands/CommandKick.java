package es.bewom.commands;

import java.util.Collection;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import es.bewom.BewomByte;
import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;
import es.bewom.user.WebRegistration;

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
		builder.append(Texts.of(TextStyles.RESET, src.getName()));
		builder.append(Texts.of(TextColors.RED, " por ", reason));
		
		toKick.kick(builder.build());
		
		Collection<Player> players = BewomByte.game.getServer().getOnlinePlayers();
		for(Player player : players) {
			if(BewomUser.getUser(player).getRegistration() == WebRegistration.VALID){
				player.sendMessage(ChatTypes.SYSTEM, Texts.of(
						TextMessages.BROADCAST,
						TextStyles.RESET, toKick.getName(),
						TextStyles.BOLD, TextColors.DARK_RED, " ha sido advertido por ", 
						TextStyles.RESET, src.getName(),
						TextStyles.BOLD, TextColors.DARK_RED, " por ", reason, "."));
			}
		}
		
		return CommandResult.success();
	}

	
	
}
