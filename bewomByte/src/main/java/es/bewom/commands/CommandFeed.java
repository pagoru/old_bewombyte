package es.bewom.commands;

import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandFeed implements CommandExecutor {

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
		
		Optional<Player> toFeedOp = args.<Player>getOne("jugador");
		
		if(toFeedOp.isPresent()) {
			Player toFeed = toFeedOp.get();
			FoodData data = toFeed.getOrCreate(FoodData.class).get();
			data.saturation().set(20.0);
			data.foodLevel().set(20);
			toFeed.offer(data);
		} else {
			Player toFeed = player;
			FoodData data = toFeed.getOrCreate(FoodData.class).get();
			data.saturation().set(20.0);
			data.foodLevel().set(20);
			toFeed.offer(data);
		}
		
		return CommandResult.success();
	}

}
