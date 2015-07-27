package es.bewom.user.messages;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.sink.MessageSink;
import org.spongepowered.api.util.command.CommandSource;

import com.google.common.collect.Lists;

import es.bewom.user.BewomUser;


public class BewomMessageSink extends MessageSink {
	
	@Override
	public Text transformMessage(CommandSource target, Text text) {
		
		if(target instanceof Player) {
			
			String message = Texts.toPlain(text);
			int nameStart = message.indexOf(target.getName());
			int nameEnd = nameStart + target.getName().length();
			
			String name = message.substring(nameStart, nameEnd);
			String postName = message.substring(nameEnd + 1, message.length());
			
			BewomUser user = BewomUser.getUser((Player) target);
			switch(user.getPermissionLevel()) {
			case BewomUser.PERM_LEVEL_USER:
				return Texts.of(TextColors.GRAY, "/", name, TextColors.WHITE, " <", postName);
			case BewomUser.PERM_LEVEL_VIP:
				return Texts.of(TextColors.DARK_AQUA, "/", name, TextColors.WHITE, " <", postName);
			case BewomUser.PERM_LEVEL_ADMIN:
				return Texts.of(TextColors.DARK_RED, "/", name, TextColors.WHITE, " <", TextStyles.BOLD, postName);
			}
			
		}
		
		return super.transformMessage(target, text);
		
	}

	@Override
	public Iterable<CommandSource> getRecipients() {
		return Lists.newArrayList();
	}

}
