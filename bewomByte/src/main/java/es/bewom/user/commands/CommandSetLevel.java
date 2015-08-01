package es.bewom.user.commands;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.google.common.base.Optional;

import es.bewom.texts.TextMessages;
import es.bewom.user.BewomUser;

public class CommandSetLevel implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		
		boolean permissions = false;
		
		if (src instanceof Player) {
			BewomUser user = BewomUser.getUser((Player) src);
			if (user.getPermissionLevel() >= BewomUser.PERM_LEVEL_ADMIN) {
				permissions = true;
			}
		}
		
		if(src.hasPermission("ghjf") && permissions == false) {
			permissions = true;
		}
		
		if(permissions == false) {
			src.sendMessage(TextMessages.NO_PERMISSIONS);
			return CommandResult.empty();
		}
		
		Optional<Player> toChangeOp = args.<Player> getOne("jugador");
		Optional<Integer> levelOp = args.<Integer> getOne("nivel");

		if (toChangeOp.isPresent() && levelOp.isPresent()) {

			Player toChange = toChangeOp.get();
			int level = levelOp.get();

			BewomUser user = BewomUser.getUser(toChange);
			Optional<String> error = user.setPermissionLevel(level);

			if (error.isPresent()) {
				src.sendMessage(Texts.of(error));
				return CommandResult.empty();
			}
			
			for(Team team : toChange.getScoreboard().getTeams()) {
				team.removeUser(toChange);
			}
			
			switch(level) {
			case BewomUser.PERM_LEVEL_ADMIN:
				Optional<Team> teamAdminOp = toChange.getScoreboard().getTeam("Admin");
				if(!teamAdminOp.isPresent()) {
					System.err.println("El jugador " + toChange.getName() + " no ha sido añadido a ningun equipo.");
					break;
				}
				Team teamAdmin = teamAdminOp.get();
				teamAdmin.addUser(toChange);
				break;
			case BewomUser.PERM_LEVEL_VIP:
				Optional<Team> teamVipOp = toChange.getScoreboard().getTeam("VIP");
				if(!teamVipOp.isPresent()) {
					System.err.println("El jugador " + toChange.getName() + " no ha sido añadido a ningun equipo.");
					break;
				}
				Team teamVip = teamVipOp.get();
				teamVip.addUser(toChange);
				break;
			case BewomUser.PERM_LEVEL_USER:
				Optional<Team> teamUserOp = toChange.getScoreboard().getTeam("User");
				if(!teamUserOp.isPresent()) {
					System.err.println("El jugador " + toChange.getName() + " no ha sido añadido a ningun equipo.");
					break;
				}
				Team teamUser = teamUserOp.get();
				teamUser.addUser(toChange);
				break;
			}

			src.sendMessage(Texts.of(TextColors.RED, toChange.getName(), " ahora es nivel de permisos ", level));
			return CommandResult.success();

		}

		return CommandResult.empty();
	}

}
