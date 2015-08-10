package es.bewom.commands;

import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandHeal implements CommandExecutor {

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
		
		Optional<Player> toHealOp = args.<Player>getOne("jugador");
		
		if(toHealOp.isPresent()) {
			Player toHeal = toHealOp.get();
			HealthData data = toHeal.getOrCreate(HealthData.class).get();
			data.health().set(20.0);
			toHeal.offer(data);
		} else {
			Player toHeal = player;
			HealthData data = toHeal.getOrCreate(HealthData.class).get();
			data.health().set(20.0);
			toHeal.offer(data);
		}
		
		return CommandResult.success();
	}

}
