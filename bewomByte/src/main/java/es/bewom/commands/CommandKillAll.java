package es.bewom.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.api.Game;
import org.spongepowered.api.data.manipulator.entity.HealthData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class CommandKillAll implements CommandExecutor {
	
	private Game game;
	
	public static Map<String, String> choices = new HashMap<String, String>();
	
	public CommandKillAll(Game game) {
		this.game = game;
		
		choices.put("players", "players");
		choices.put("animals", "animals");
		choices.put("monsters", "monsters");
		choices.put("living", "living");
		
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		if(!(src instanceof Player)) {
			src.sendMessage(Texts.of("This command is for players only"));
			return CommandResult.empty();
		}
		
		Player player = (Player) src;
		String arg = args.<String>getOne("entity_type").get();
		arg = arg.toLowerCase();
		
		switch(arg) {
		case "players":
			killAllPlayers(player);
			return CommandResult.success();
		case "animals":
			killAllAnimals(player);
			return CommandResult.success();
		case "monsters":
			killAllMonsters(player);
			return CommandResult.success();
		case "living":
			killAllAnimals(player);
			killAllMonsters(player);
			return CommandResult.success();
		}
		
		src.sendMessage(Texts.of(TextColors.RED, arg + " is not a valid argument."));
		return CommandResult.empty();
	}
	
	private void killAllPlayers(Player player) {
		
		Collection<Player> players = game.getServer().getOnlinePlayers();
		
		for(Player p : players) {
			if(p.equals(player)) continue;
			HealthData health = p.getHealthData();
			health.setHealth(0);
			p.offer(health);
		}
		
	}
	
	private void killAllAnimals(Player player) {
		
		Collection<Entity> entities = player.getWorld().getEntities();
		
		for(Entity entity : entities) {
			if(entity instanceof Animal) {
				entity.remove();
			}
		}
		
	}
	
	private void killAllMonsters(Player player) {
		
		Collection<Entity> entities = player.getWorld().getEntities();
		
		for(Entity entity : entities) {
			if(entity instanceof Monster) {
				entity.remove();
			}
		}
		
	}

}
