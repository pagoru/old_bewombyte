package es.bewom.commands;

import org.spongepowered.api.data.manipulator.item.DurabilityData;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

public class CommandRepair implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(src instanceof Player) {
			
			Player player = (Player) src;
			
			Optional<ItemStack> itemStackOp = player.getItemInHand();
			
			if(itemStackOp.isPresent()) {
				ItemStack itemStack = itemStackOp.get();
				if(itemStack.isCompatible(DurabilityData.class)) {
					if(itemStack.getData(DurabilityData.class).isPresent()) {
						DurabilityData durability = itemStack.getData(DurabilityData.class).get();
						durability.setDurability(durability.getMaxValue());
						itemStack.offer(durability);
					} else {
						src.sendMessage(Texts.of("DurabilityData is not present in its optional."));
					}
				} else {
					src.sendMessage(Texts.of("This ItemStack is incompatible with the DurabilityData manipulator."));
				}
			} else {
				src.sendMessage(Texts.of("ItemStack is not present in its optional."));
			}
			
		}
		
		return CommandResult.empty();
	}

}
