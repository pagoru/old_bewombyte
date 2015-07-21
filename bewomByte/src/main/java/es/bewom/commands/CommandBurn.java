package es.bewom.commands;

import org.spongepowered.api.data.manipulator.entity.FlamableData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

public class CommandBurn implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		Player toBurn = args.<Player>getOne("player").get();
		
		Optional<Integer> secondsOp = args.getOne("time");
		int seconds = 0;
		
		if(secondsOp.isPresent()) {
			seconds = secondsOp.get();
		}
		
		if(seconds == 0) {
			seconds = 1;
		}
		
		Optional<FlamableData> data = toBurn.getOrCreate(FlamableData.class);
		
		if(data.isPresent()) {
			
			FlamableData ignite = data.get();
			
			src.sendMessage(Texts.of(toBurn.getName() + " is burning"));
			
			toBurn.offer(ignite);
			
		}
		
		return CommandResult.success();
	}

}
