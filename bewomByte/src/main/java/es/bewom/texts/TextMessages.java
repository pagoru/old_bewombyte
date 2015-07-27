package es.bewom.texts;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

public class TextMessages {
	
	public static final Text NO_PERMISSIONS = Texts.of(TextColors.RED, "No tienes permisos.");
	public static final Text NOT_CONSOLE_COMPATIBLE = Texts.of("Este comando no funciona en consola.");

	public static final Text TP_SUCCESS = Texts.of(TextColors.RED, "Teletransporte exitoso.");
	public static final Text TP_EXPIRED = Texts.of(TextColors.RED, "La solicitud ha expirado.");
	public static final Text TP_NOT_FOUND = Texts.of(TextColors.RED, "No hay ninguna solicitud.");
	public static final Text TP_DENIED = Texts.of(TextColors.RED, "Solicitud de teletransporte denegada.");
	public static final Text TP_REQUEST_SENT = Texts.of(TextColors.RED, "Se ha enviado una solicitud.");
	
	public static final Text LOGIN_SUCCESS = Texts.of(TextColors.RED, "Bienvenido a ", TextColors.GOLD, "BEWOM", TextColors.RED, ".");
	
	public static final Text WORLD_NOT_FOUND = Texts.of(TextColors.RED, "No se ha encontrado el mundo especificado.");
	
}
