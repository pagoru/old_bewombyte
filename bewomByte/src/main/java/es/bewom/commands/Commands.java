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
import es.bewom.teleport.commands.CommandTPAHere;
import es.bewom.teleport.commands.CommandTPAccept;
import es.bewom.user.commands.CommandSetLevel;
import es.bewom.warps.commands.CommandWarp;
import es.bewom.warps.commands.CommandWarpDel;
import es.bewom.warps.commands.CommandWarpList;
import es.bewom.warps.commands.CommandWarpSet;
import es.bewom.world.commands.CommandWorld;

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
//				.permission("bewom.commands.kick")
				.description(Texts.of("Advertencia."))
				.executor(new CommandKick())
				.arguments(
						GenericArguments.player(Texts.of("jugador"), game),
						GenericArguments.optional(GenericArguments.remainingJoinedStrings(Texts.of("razon"))))
				.build();
		
		//kill <player>
		CommandSpec cmdSpec_kill = CommandSpec
				.builder()
//				.permission("bewom.commands.kill")
				.description(Texts.of("Matar a un jugador."))
				.executor(new CommandKill())
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("jugador"), game)))
				.build();
		
		//killAll <entity_type>
		CommandSpec cmdSpec_killAll = CommandSpec
				.builder()
//				.permission("bewom.commands.killAll")
				.description(Texts.of("Matar todos los <entidad>"))
				.executor(new CommandKillAll(game))
				.arguments(GenericArguments.onlyOne(GenericArguments.choices(Texts.of("entidad"), CommandKillAll.choices)))
				.build();
				
		
		//burn <player> [time]		NO FUNCIONA, SIN ERROR (Sponge)
		CommandSpec cmdSpec_burn = CommandSpec
				.builder()
//				.permission("bewom.commands.burn")
				.description(Texts.of("Quemar un jugador."))
				.executor(new CommandBurn())
				.arguments(
						GenericArguments.player(Texts.of("jugador"), game),
						GenericArguments.optional(GenericArguments.integer(Texts.of("tiempo"))))
				.build();
		
		//feed [player]				ERROR (Sponge)
		CommandSpec cmdSpec_feed = CommandSpec
				.builder()
//				.permission("bewom.commands.feed")
				.description(Texts.of("Dar de comer a un jugador (rellena su barra de hambre)."))
				.executor(new CommandFeed())
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("jugador"), game))))
				.build();
		
		//heal [player]
		CommandSpec cmdSpec_heal = CommandSpec
				.builder()
//				.permission("bewom.commands.heal")
				.description(Texts.of("Curar a un jugador."))
				.executor(new CommandHeal())
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Texts.of("jugador"), game))))
				.build();
		
		//tpa <player>
		CommandSpec cmdSpec_tpa = CommandSpec
				.builder()
//				.permission("bewom.commands.tpa")
				.description(Texts.of("Enviar una solicitud a un jugador para teletransportarte a su posición"))
				.executor(new CommandTPA())
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("jugador"), game)))
				.build();
		
		//tpahere <player>
		CommandSpec cmdSpec_tpaHere = CommandSpec
				.builder()
//				.permission("bewom.commands.tpahere")
				.description(Texts.of("Enviar una solicitud a un jugador para teletransportarlo a tu posición."))
				.executor(new CommandTPAHere())
				.arguments(GenericArguments.onlyOne(GenericArguments.player(Texts.of("player"), game)))
				.build();
		
		//tpaccept
		CommandSpec cmdSpec_tpaccept = CommandSpec
				.builder()
//				.permission("bewom.commands.tpaccept")
				.description(Texts.of("Aceptar la última solicitud de teletransporte."))
				.executor(new CommandTPAccept())
				.build();
		
		//tpdeny
		CommandSpec cmdSpec_tpadeny = CommandSpec
				.builder()
//				.permission("bewom.commands.tpadeny")
				.description(Texts.of("Denegar la última solicitud de teletransporte."))
				.executor(new CommandTPADeny())
				.build();
		
		//repair
		CommandSpec cmdSpec_repair = CommandSpec
				.builder()
//				.permission("bewom.commands.repair")
				.description(Texts.of("Reparar el ítem de la mano."))
				.executor(new CommandRepair())
				.build();
		
		//warp <name>
		CommandSpec cmdSpec_warp = CommandSpec
				.builder()
//				.permission("bewom.commands.warp")
				.description(Texts.of("Teletransportarse al warp especificado."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("nombre"))))
				.executor(new CommandWarp())
				.build();
		
		//warpset/setwarp <name>
		CommandSpec cmdSpec_warpSet = CommandSpec
				.builder()
//				.permission("bewom.commands.warp")
				.description(Texts.of("Crear un nuevo warp."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("nombre"))))
				.executor(new CommandWarpSet())
				.build();
		
		//warpdel/delwarp <name>
		CommandSpec cmdSpec_warpDel = CommandSpec
				.builder()
//				.permission("bewom.commands.warp")
				.description(Texts.of("Eliminar el warp especificado."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("nombre"))))
				.executor(new CommandWarpDel())
				.build();
		
		//warplist/warps <name>
		CommandSpec cmdSpec_warpList = CommandSpec
				.builder()
//				.permission("bewom.commands.warp")
				.description(Texts.of("Te devuelve la lista de warps existentes."))
				.executor(new CommandWarpList())
				.build();
		
		//cp
		CommandSpec cmdSpec_centro = CommandSpec
				.builder()
//				.permission("bewom.commands.ponercentro")
				.description(Texts.of("Ir al centro pokemon más cercano."))
				.executor(new CommandCentro())
				.build();
		
		//pcp
		CommandSpec cmdSpec_ponerCentro = CommandSpec
				.builder()
//				.permission("bewom.commands.ponercentro")
				.description(Texts.of("Establecer un centro pokemon en la posición actual."))
				.executor(new CommandPonerCentro())
				.build();
		
		//qcp
		CommandSpec cmdSpec_quitarCentro = CommandSpec
				.builder()
//				.permission("bewom.commands.quitarcentro")
				.description(Texts.of("Eliminar el centro pokemon de la posición actual."))
				.executor(new CommandQuitarCentro())
				.build();
		
		//spawn
		CommandSpec cmdSpec_spawn = CommandSpec
				.builder()
//				.permission("bewom.commands.spawn")
				.description(Texts.of("Ir al spawn."))
				.executor(new CommandSpawn())
				.build();
		
		//setspawn
		CommandSpec cmdSpec_setSpawn = CommandSpec
				.builder()
//				.permission("bewom.commands.setspawn")
				.description(Texts.of("Establecer el spawn."))
				.executor(new CommandSetSpawn())
				.build();
		
		//world <world>
		CommandSpec cmdSpec_world = CommandSpec
				.builder()
//				.permission("bewom.commands.world")
				.description(Texts.of("Ir al mundo especificado."))
				.arguments(GenericArguments.onlyOne(GenericArguments.world(Texts.of("mundo"), game)))
				.executor(new CommandWorld())
				.build();
		
		//level <level>
		CommandSpec cmdSpec_level = CommandSpec
				.builder()
//				.permission("bewom.commands.level")
				.description(Texts.of("Establecer el nivel de permisos a un jugador."))
				.arguments(GenericArguments.seq(GenericArguments.player(Texts.of("jugador"), game), GenericArguments.integer(Texts.of("nivel"))))
				.executor(new CommandSetLevel())
				.build();
		
		//say <mensaje>
		CommandSpec cmdSpec_say = CommandSpec
				.builder()
//				.permission("bewom.commands.say")
				.description(Texts.of("Decir a todo el servidor como Bewom"))
				.arguments(GenericArguments.remainingJoinedStrings(Texts.of("mensaje")))
				.executor(new CommandSay(game))
				.build();
		
		game.getCommandDispatcher().register(plugin, cmdSpec_kick, "kick");
		game.getCommandDispatcher().register(plugin, cmdSpec_kill, "kill");
		game.getCommandDispatcher().register(plugin, cmdSpec_killAll, "killall");
		game.getCommandDispatcher().register(plugin, cmdSpec_burn, "burn", "ignite");
		game.getCommandDispatcher().register(plugin, cmdSpec_feed, "feed");
		game.getCommandDispatcher().register(plugin, cmdSpec_heal, "heal");
		game.getCommandDispatcher().register(plugin, cmdSpec_tpa, "tpa");
		game.getCommandDispatcher().register(plugin, cmdSpec_tpaHere, "tpahere");
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
		game.getCommandDispatcher().register(plugin, cmdSpec_world, "world");
		game.getCommandDispatcher().register(plugin, cmdSpec_level, "nivel", "permisos");
		game.getCommandDispatcher().register(plugin, cmdSpec_say, "say", "broadcast");
		
		game.getCommandDispatcher().register(plugin, new CommandGM(), "gm");
		game.getCommandDispatcher().register(plugin, new CommandInv(game), "inv", "invsee");
		
	}
	
}
