package es.bewom.commands;

import org.spongepowered.api.Game;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

import es.bewom.centrospokemon.commands.CommandCentro;
import es.bewom.centrospokemon.commands.CommandPonerCentro;
import es.bewom.centrospokemon.commands.CommandQuitarCentro;
import es.bewom.spawn.commands.CommandSetSpawn;
import es.bewom.spawn.commands.CommandSpawn;
import es.bewom.teleport.commands.CommandTPA;
import es.bewom.teleport.commands.CommandTPADeny;
import es.bewom.teleport.commands.CommandTPAccept;
import es.bewom.warps.commands.CommandWarp;
import es.bewom.warps.commands.CommandWarpDel;
import es.bewom.warps.commands.CommandWarpList;
import es.bewom.warps.commands.CommandWarpSet;

public class Commands {
	
	private Object plugin;
	private Game game;
	
	public Commands(Object plugin, Game game) {
		this.plugin = plugin;
		this.game = game;
	}
	
	public void registerAll() {
		
		//kick <player> [reason...]
		CommandSpec cmdSpec_kick = CommandSpec
				.builder()
				.permission("bewom.commands.kick")
				.description(Texts.of("Kick Player."))
				.executor(new CommandKick())
				.arguments(
						GenericArguments.player(Texts.of("player"), game),
						GenericArguments.optional(GenericArguments.remainingJoinedStrings(Texts.of("reason"))))
				.build();
		
		//kill <player>
		CommandSpec cmdSpec_kill = CommandSpec
				.builder()
				.permission("bewom.commands.kill")
				.description(Texts.of("Kill Player."))
				.executor(new CommandKill())
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)))
				.build();
		
		//killAll <entity_type>
		CommandSpec cmdSpec_killAll = CommandSpec
				.builder()
				.permission("bewom.commands.killAll")
				.description(Texts.of("Kill all of <entity_type>"))
				.executor(new CommandKillAll(game))
				.arguments(GenericArguments.onlyOne(GenericArguments.choices(Texts.of("entity_type"), CommandKillAll.choices)))
				.build();
				
		
		//burn <player> [time]		NO FUNCIONA, SIN ERROR (Sponge)
		CommandSpec cmdSpec_burn = CommandSpec
				.builder()
				.permission("bewom.commands.burn")
				.description(Texts.of("Set a player on fire (during time in seconds)."))
				.executor(new CommandBurn())
				.arguments(
						GenericArguments.player(Texts.of("player"), game),
						GenericArguments.optional(GenericArguments.integer(Texts.of("time"))))
				.build();
		
		//feed [player]				ERROR (Sponge)
		CommandSpec cmdSpec_feed = CommandSpec
				.builder()
				.permission("bewom.commands.feed")
				.description(Texts.of("Set players food level to max."))
				.executor(new CommandFeed())
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
				.build();
		
		//heal [player]
		CommandSpec cmdSpec_heal = CommandSpec
				.builder()
				.permission("bewom.commands.heal")
				.description(Texts.of("Heals player."))
				.executor(new CommandHeal())
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game))))
				.build();
		
		//tpa <player>
		CommandSpec cmdSpec_tpa = CommandSpec
				.builder()
				.permission("bewom.commands.tpa")
				.description(Texts.of("Send teleport request to player."))
				.executor(new CommandTPA())
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)))
				.build();
		
		//tpaccept
		CommandSpec cmdSpec_tpaccept = CommandSpec
				.builder()
				.permission("bewom.commands.tpaccept")
				.description(Texts.of("Accept teleport request."))
				.executor(new CommandTPAccept())
				.build();
		
		//tpdeny
		CommandSpec cmdSpec_tpadeny = CommandSpec
				.builder()
				.permission("bewom.commands.tpadeny")
				.description(Texts.of("Deny teleport request."))
				.executor(new CommandTPADeny())
				.build();
		
		//repair
		CommandSpec cmdSpec_repair = CommandSpec
				.builder()
				.permission("bewom.commands.repair")
				.description(Texts.of("Repair item in hand."))
				.executor(new CommandRepair())
				.build();
		
		//warp <name>
		CommandSpec cmdSpec_warp = CommandSpec
				.builder()
				.permission("bewom.commands.warp")
				.description(Texts.of("Teleport to specified warp."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("name"))))
				.executor(new CommandWarp())
				.build();
		
		//warpset/setwarp <name>
		CommandSpec cmdSpec_warpSet = CommandSpec
				.builder()
				.permission("bewom.commands.warp")
				.description(Texts.of("Create new warp."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("name"))))
				.executor(new CommandWarpSet())
				.build();
		
		//warpdel/delwarp <name>
		CommandSpec cmdSpec_warpDel = CommandSpec
				.builder()
				.permission("bewom.commands.warp")
				.description(Texts.of("Delete specified warp."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("name"))))
				.executor(new CommandWarpDel())
				.build();
		
		//warplist/warps <name>
		CommandSpec cmdSpec_warpList = CommandSpec
				.builder()
				.permission("bewom.commands.warp")
				.description(Texts.of("Get the list of warps."))
				.executor(new CommandWarpList())
				.build();
		
		//cp
		CommandSpec cmdSpec_centro = CommandSpec
				.builder()
				.permission("bewom.commands.ponercentro")
				.description(Texts.of("Go to a near CP."))
				.executor(new CommandCentro())
				.build();
		
		//pcp
		CommandSpec cmdSpec_ponerCentro = CommandSpec
				.builder()
				.permission("bewom.commands.ponercentro")
				.description(Texts.of("Set a Centro Pokemon in current position."))
				.executor(new CommandPonerCentro())
				.build();
		
		//qcp
		CommandSpec cmdSpec_quitarCentro = CommandSpec
				.builder()
				.permission("bewom.commands.quitarcentro")
				.description(Texts.of("Delete the Centro Pokemon at current position."))
				.executor(new CommandQuitarCentro())
				.build();
		
		//spawn
		CommandSpec cmdSpec_spawn = CommandSpec
				.builder()
				.permission("bewom.commands.spawn")
				.description(Texts.of("Teleport to spawn."))
				.executor(new CommandSpawn())
				.build();
		
		//setspawn
		CommandSpec cmdSpec_setSpawn = CommandSpec
				.builder()
				.permission("bewom.commands.setspawn")
				.description(Texts.of("Set the spawn."))
				.executor(new CommandSetSpawn())
				.build();
		
		game.getCommandDispatcher().register(plugin, cmdSpec_kick, "kick");
		game.getCommandDispatcher().register(plugin, cmdSpec_kill, "kill");
		game.getCommandDispatcher().register(plugin, cmdSpec_killAll, "killall");
		game.getCommandDispatcher().register(plugin, cmdSpec_burn, "burn", "ignite");
		game.getCommandDispatcher().register(plugin, cmdSpec_feed, "feed");
		game.getCommandDispatcher().register(plugin, cmdSpec_heal, "heal");
		game.getCommandDispatcher().register(plugin, cmdSpec_tpa, "tpa");
		game.getCommandDispatcher().register(plugin, cmdSpec_tpaccept, "tpaccept");
		game.getCommandDispatcher().register(plugin, cmdSpec_tpadeny, "tpdeny");
		game.getCommandDispatcher().register(plugin, cmdSpec_repair, "repair");
		game.getCommandDispatcher().register(plugin, cmdSpec_warp, "warp");
		game.getCommandDispatcher().register(plugin, cmdSpec_warpSet, "warpset", "setwarp");
		game.getCommandDispatcher().register(plugin, cmdSpec_warpDel, "warpdel", "delwarp");
		game.getCommandDispatcher().register(plugin, cmdSpec_warpList, "warplist", "warps");
		game.getCommandDispatcher().register(plugin, cmdSpec_centro, "cp", "centro", "centropokemon");
		game.getCommandDispatcher().register(plugin, cmdSpec_ponerCentro, "ponercentro", "ponercp", "pcp");
		game.getCommandDispatcher().register(plugin, cmdSpec_quitarCentro, "quitarcentro", "quitarcp", "qcp");
		game.getCommandDispatcher().register(plugin, cmdSpec_spawn, "spawn");
		game.getCommandDispatcher().register(plugin, cmdSpec_setSpawn, "setspawn");
		
	}
	
}
