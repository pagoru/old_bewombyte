package es.bewom.texts;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class TextMessages {
	
	public static Text NO_PERMISSIONS = Texts.of(TextColors.RED, "No tienes permisos.");
	public static Text NOT_CONSOLE_COMPATIBLE = Texts.of("Este comando no funciona en consola.");

	public static Text TP_SUCCESS = Texts.of(TextColors.RED, "Teletransporte exitoso.");
	public static Text TP_EXPIRED = Texts.of(TextColors.RED, "La solicitud ha expirado.");
	public static Text TP_NOT_FOUND = Texts.of(TextColors.RED, "No hay ninguna solicitud.");
	public static Text TP_DENIED = Texts.of(TextColors.RED, "Solicitud de teletransporte denegada.");
	
	public static Text LOGIN_SUCCESS = Texts.of(TextColors.RED, "Bienvenido a ", TextColors.GOLD, "BEWOM", TextColors.RED, ".");
	
}
