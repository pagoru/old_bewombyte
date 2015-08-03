package es.bewom.user;

import java.util.HashMap;
import java.util.UUID;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.scoreboard.Team;

import com.google.common.base.Optional;

import es.bewom.BewomByte;

/**
 * 
 * @author McMacker4
 *
 */
public class BewomUser {
	
	public static final String PERM_ADMIN = "admin";
	public static final String PERM_VIP = "vip";
	public static final String PERM_USER = "miembro";
	
	public static final int PERM_LEVEL_ADMIN = 3;
	public static final int PERM_LEVEL_VIP = 2;
	public static final int PERM_LEVEL_USER = 1;
	
	private static BewomByte plugin;
	
	static HashMap<UUID, BewomUser> onlineUsers = new HashMap<>();
	
	private Player player;
	private UUID uuid;
	
	private boolean isAfk;
	private int lastMove;
	
	private int permissionLevel;
	private int warnings;
	
	private int registration = -1;
	
	/**
	 * Constructor. Creates a {@link BewomUser} from a player.
	 * @param player to create the {@link BewomUser} from.
	 */
	public BewomUser(Player player) {
		
		this.player = player;
		this.uuid = player.getUniqueId();
		lastMove = plugin.getGame().getServer().getRunningTimeTicks();
		registration = checkWebsiteRegistration();
		permissionLevel = checkPermissionLevel();
		warnings = checkNumberOfWarnings();
		
		updatePermissions();
		
	}
	
	public void updatePermissions(){
		
		switch(permissionLevel) {
		case PERM_LEVEL_ADMIN:
			Optional<Team> teamAdminOp = player.getScoreboard().getTeam(PERM_ADMIN);
			if(!teamAdminOp.isPresent()) {
				System.err.println("El jugador " + player.getName() + " no ha sido a�adido a ningun equipo.");
				break;
			}
			Team teamAdmin = teamAdminOp.get();
			if(!teamAdmin.getUsers().contains(player)){
				for(Team team : player.getScoreboard().getTeams()) {
					team.removeUser(player);
				}
				teamAdmin.addUser(player);
			}
			break;
		case PERM_LEVEL_VIP:
			Optional<Team> teamVipOp = player.getScoreboard().getTeam(PERM_VIP);
			if(!teamVipOp.isPresent()) {
				System.err.println("El jugador " + player.getName() + " no ha sido a�adido a ningun equipo.");
				break;
			}
			Team teamVip = teamVipOp.get();
			if(!teamVip.getUsers().contains(player)){
				for(Team team : player.getScoreboard().getTeams()) {
					team.removeUser(player);
				}
				teamVip.addUser(player);
			}
			break;
		case PERM_LEVEL_USER:
			Optional<Team> teamUserOp = player.getScoreboard().getTeam(PERM_USER);
			if(!teamUserOp.isPresent()) {
				System.err.println("El jugador " + player.getName() + " no ha sido a�adido a ningun equipo.");
				break;
			}
			Team teamUser = teamUserOp.get();
			if(!teamUser.getUsers().contains(player)){
				for(Team team : player.getScoreboard().getTeams()) {
					team.removeUser(player);
				}
				teamUser.addUser(player);
			}
			break;
		}
		
	}

	/**
	 * Returns the {@link Player} attached to this {@link BewomUser}.
	 * @return {@link Player}
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the name of the {@link Player} attached to this {@link BewomUser}.
	 * @return {@link String}
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Returns whether this {@link BewomUser} is Afk or not.
	 * @return boolean
	 */
	public boolean isAfk() {
		return isAfk;
	}
	
	/**
	 * Set the last time the player moved.
	 * @param time in ticks.
	 */
	public void setLastMove(int time) {
		this.lastMove = time;
	}
	
	/**
	 * Get the last time the player moved.
	 * @return Last time player moved.
	 */
	public int getLastMove() {
		return lastMove;
	}
	
	/**
	 * Returns the {@link WebRegistration} value of the player.
	 * @return Registration value.
	 */
	public int getRegistration() {
		return registration;
	}
	
	/**
	 * Runs when player joins.
	 * Grabs {@link WebRegistration} value from web server.
	 * @return 
	 */
	private int checkWebsiteRegistration() {
		//TODO: Check Registration in the database.
		return WebRegistration.VALID;
	}
	
	private int checkPermissionLevel() {
		//TODO: Check Permission level in the database.
		return 1;
	}
	
	private int checkNumberOfWarnings() {
		//TODO: Check the number of warnings in the database.
		return 0;
	}
	
	/**
	 * Returns the level of permissions this user has.
	 * This is provided by the database connected to the forums.
	 * @return int - The permission level.
	 */
	public int getPermissionLevel() {
		return permissionLevel;
	}
	
	public Optional<String> setPermissionLevel(int level) {
		if(level < PERM_LEVEL_USER || level > PERM_LEVEL_ADMIN) {
			return Optional.of("Este grupo no existe.");
		}
		this.permissionLevel = level;
		//TODO: Save to database.
		return Optional.absent();
	}
	
	/**
	 * Gets a {@link BewomUser} that is online.
	 * @param name of the player.
	 * @return The {@link BewomUser} specified.
	 */
	public static BewomUser getUser(UUID uuid) {
		return onlineUsers.get(uuid);
	}
	
	/**
	 * Gets a {@link BewomUser} that is online.
	 * @param player
	 * @return The {@link BewomUser} or null if not found.
	 */
	public static BewomUser getUser(Player player) {
		return getUser(player.getUniqueId());
	}
	
	/**
	 * Adds the {@link BewomUser} to the onlineUsers {@link HashMap}
	 * @param user
	 */
	public static void addUser(BewomUser user) {
		onlineUsers.put(user.getUUID(), user);
		System.out.println(onlineUsers.keySet());
	}
	
	/**
	 * Removes user from the online users list.
	 */
	public static void remove(BewomUser user) {
		onlineUsers.remove(user.getUUID());
	}
	
	public static void remove(UUID uuid) {
		onlineUsers.remove(uuid);
		System.out.println(onlineUsers.keySet());
	}
	
	/**
	 * DO NOT USE.
	 * This function sets the current plugin.
	 * This should only be used from BewomByte.class and just once when server starts.
	 * @param game
	 */
	public static void setGame(BewomByte plugin) {
		BewomUser.plugin = plugin;
	}
	
}
