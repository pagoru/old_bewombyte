package es.bewom.commands;

import org.spongepowered.api.data.manipulator.entity.HealthData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

public class CommandHeal implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		Player player;
		
		if(src instanceof Player) {
			player = (Player) src;
		} else {
			src.sendMessage(Texts.of("This command is for players only."));
			return CommandResult.empty();
		}
		
		Optional<Player> toHealOp = args.<Player>getOne("player");
		
		if(toHealOp.isPresent()) {
			Player toHeal = toHealOp.get();
			HealthData data = toHeal.getOrCreate(HealthData.class).get();
			data.setHealth(20);
			toHeal.offer(data);
		} else {
			Player toHeal = player;
			HealthData data = toHeal.getOrCreate(HealthData.class).get();
			data.setHealth(20);
			toHeal.offer(data);
		}
		
		return CommandResult.success();
	}

}
