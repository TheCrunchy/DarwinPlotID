import java.io.File;
import java.nio.file.Path;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ToggleExecutors {
	
	public static class TogglePlotID implements CommandExecutor {
		private Path root;
		public TogglePlotID(Path root) {
		this.root = root;
		}
	    @Override
	    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
	    	Player player = (Player) src;
	    	 File file = new File(root.toFile(), "toggled.conf");
	    	 BarPlayer barP = new BarPlayer(player);
	    	if (PlotID.toggledID.contains(player.getUniqueId())){
	    	PlotID.toggledID.remove(player.getUniqueId());
	    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Bar Preference to ", TextColors.RED, "on"));
	    	barP.setBarBool(false);
	        }
	    	else {
	    		PlotID.toggledID.add(player.getUniqueId());
		    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Bar Preference to ", TextColors.RED, "off"));
		    	barP.setBarBool(true);
	    	}
	    	  PlotID.allPlayers.put(player.getUniqueId(), barP);
            Toggled toggled = new Toggled();
            RootConfig config = new RootConfig();
            toggled.setToggledID(PlotID.toggledID);
            toggled.setToggledMem(PlotID.toggledMembers);
            config.getCategories().add(toggled);
            PlotID.saveConfig(config, file.toPath());
	     return CommandResult.success();
	    }
	}
	

	public static class TogglePlotMembers implements CommandExecutor {
		private Path root;
		public TogglePlotMembers(Path root) {
		this.root = root;
		}
	    @Override
	    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
	    	File file = new File(root.toFile(), "toggled.conf");
	    	Player player = (Player) src;
	    	BarPlayer barP = new BarPlayer(player);
	    	if (PlotID.toggledMembers.contains(player.getUniqueId())){
	    	PlotID.toggledMembers.remove(player.getUniqueId());
	    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Members Preference to ", TextColors.RED, "on"));
	    	barP.setMembersBool(false);
	        }
	    	else {
	    		PlotID.toggledMembers.add(player.getUniqueId());
		    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Members Preference to ", TextColors.RED, "off"));
		    	barP.setMembersBool(true);
	    	}
	    	  PlotID.allPlayers.put(player.getUniqueId(), barP);
            Toggled toggled = new Toggled();
            RootConfig config = new RootConfig();
            toggled.setToggledID(PlotID.toggledID);
            toggled.setToggledMem(PlotID.toggledMembers);
            config.getCategories().add(toggled);
            PlotID.saveConfig(config, file.toPath());
	     return CommandResult.success();
	    }
	}
}
