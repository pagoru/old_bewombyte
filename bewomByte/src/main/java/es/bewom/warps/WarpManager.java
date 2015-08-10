package es.bewom.warps;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.EntityInteractionTypes;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.block.tileentity.SignChangeEvent;
import org.spongepowered.api.event.entity.player.PlayerInteractBlockEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.bewom.BewomByte;

public class WarpManager {
	
	private static BewomByte plugin;

	static ArrayList<Warp> warps = new ArrayList<>();

	/**
	 * Adds a warp to the list of available warps.
	 * 
	 * @param warp
	 * @return Absent optional in case there was no error.
	 * @return {@link Optional} of type string with the error message in case
	 *         there was an error.
	 */
	public static Optional<String> addWarp(Warp warp) {

		for (Warp w : warps) {
			if (w.getName().equalsIgnoreCase(warp.getName()))
				return Optional.of("Name already in use.");
			if (w.isPositionSame(warp.getX(), warp.getY(), warp.getZ()))
				return Optional.of("Position is already a warp.");
		}

		warps.add(warp);

		return Optional.absent();

	}

	/**
	 * Deletes a warp from the current list of available warps.
	 * 
	 * @param warp
	 * @return Absent optional in case there was no error.
	 * @return {@link Optional} of type string with the error message in case
	 *         there was an error.
	 */
	public static Optional<String> deleteWarp(String name) {

		for (Warp warp : warps) {
			if (warp.getName().equalsIgnoreCase(name)) {
				warps.remove(warp);
				return Optional.absent();
			}
		}

		return Optional.of("Warp not found.");

	}

	/**
	 * Returns the warp with the given name. The {@link Optional} will contain
	 * the found {@link Warp}. In case there was no {@link Warp} found this will
	 * return an Absent {@link Optional}.
	 * 
	 * @param name
	 * @return An {@link Optional} of type warp.
	 */
	public static Optional<Warp> getWarpByName(String name) {

		for (Warp warp : warps) {
			if (warp.getName().equalsIgnoreCase(name)) {
				return Optional.of(warp);
			}
		}

		return Optional.absent();

	}

	/**
	 * Returns a String with all the warp names available.
	 * 
	 * @return String with the list of names or error message in case of error.
	 */
	public static String getAllWarpNames() {

		if (warps.isEmpty())
			return "There are no warps.";

		String names = "Warps: ";

		for (int i = 0; i < warps.size(); i++) {
			names += warps.get(i).getName();
			if (i != warps.size() - 1) {
				names += ", ";
			} else {
				names += ".";
			}
		}

		return names;
	}

	/**
	 * Save the warps to the json file.
	 */
	public static void save() {

		try {

			Warp[] warpsArray = new Warp[warps.size()];
			for (int i = 0; i < warps.size(); i++) {
				warpsArray[i] = warps.get(i);
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(warpsArray);

			FileWriter writer = new FileWriter("bewom/warps.json");
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Load warps from the json file.
	 */
	public static void load() {

		try {

			File folder = new File("bewom");
			if (!folder.exists()) {
				folder.mkdirs();
			}
			File file = new File("bewom/warps.json");
			if (!file.exists()) {
				file.createNewFile();
				return;
			}

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Warp[] warpsArray = gson.fromJson(new FileReader("bewom/warps.json"),
					Warp[].class);

			ArrayList<Warp> warpsList = new ArrayList<>();
			if(!warpsList.isEmpty()) {
				for (Warp warp : warpsArray) {
					warpsList.add(warp);
				}
			}

			WarpManager.warps = warpsList;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Event triggered when a sign is created/changed.
	 * @param event
	 */
	@Subscribe
	public void onWarpSignCreated(SignChangeEvent event) {
		
		SignData newData = event.getNewData();
		
		if(Texts.toPlain(newData.lines().get(0)).equalsIgnoreCase("[WARP]")) {
			
			List<Text> lines = newData.lines().getAll();
			String warpName = Texts.toPlain(lines.get(1));
			Optional<Warp> warpOp = WarpManager.getWarpByName(warpName);
			
			if(warpOp.isPresent()) {
				lines.set(0, Texts.of(TextColors.GREEN, "[WARP]"));
				lines.set(1, Texts.of(TextColors.BLACK, warpName.toLowerCase()));
				lines.set(2, Texts.of(""));
				lines.set(3, Texts.of(""));
			} else {
				lines.set(0, Texts.of(TextColors.DARK_RED, "[WARP]"));
				lines.set(1, Texts.of(TextColors.BLACK, "ERROR"));
				lines.set(2, Texts.of(TextColors.BLACK, "Not found."));
				lines.set(3, Texts.of(""));
			}
			
			for(int i = 0; i < 4; i++) {
				newData.lines().set(i, lines.get(i));
			}
			
			event.setNewData(newData);
			
		}
		
	}
	
	/**
	 * Event triggered when a sign is clicked.
	 * @param event
	 */
	@Subscribe
	public void onSignClicked(PlayerInteractBlockEvent event) {
		
		if(event.getInteractionType() != EntityInteractionTypes.USE)
			return;

		Location block = event.getLocation();
		
		if(!block.getTileEntity().isPresent()) {
			return;
		}
		
		if(!(block.getTileEntity().get().getType() == TileEntityTypes.SIGN)) {
			return;
		}
		
		TileEntity signEntity = block.getTileEntity().get();
		
		Optional<SignData> signData = signEntity.getOrCreate(SignData.class);
		
		if(!signData.isPresent()) {
			return;
		}
		
		List<Text> lines = signData.get().lines().getAll();
		if(!Texts.toPlain(lines.get(0)).equals("[WARP]")) {
			return;
		}
		
		String command = "warp " + Texts.toPlain(lines.get(1));
		plugin.getGame().getCommandDispatcher().process(event.getUser(), command);
		
	}
	
	/**
	 * Used by the main class {@link BewomByte} to initialize plugin in this class.
	 * @param plugin
	 */
	public static void init(BewomByte plugin) {
		WarpManager.plugin = plugin;
	}

}
