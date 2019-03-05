import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.Sponge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.Optional;
import java.util.Set;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import com.intellectualcrafters.plot.flag.Flags;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;
import com.intellectualcrafters.plot.object.PlotPlayer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.inject.Inject;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;

import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

	@Plugin(id = "plotidbossbar", name = "Darwin PlotID boss bar", version = "1.0", description = "Plot ID boss bar")
	public class PlotID {
	    @Listener
	    public void onServerFinishLoad(GameStartedServerEvent event) {

		// Hey! The server has started!
	        // Try instantiating your logger in here.
	        // (There's a guide for that)
	    	Sponge.getEventManager().registerListeners(this, new move());
	    	Sponge.getCommandManager().register(this, toggle, "toggle");
	    	plugin = this;
	    }
	    Object plugin;
	    public static ArrayList <UUID> toggledID = new ArrayList<>();
	    public static ArrayList <UUID> toggledMembers = new ArrayList<>();
		public static  HashMap<UUID, BarPlayer> allPlayers = new HashMap<>();
	    @Listener
	    public void onServerStart(GameStartedServerEvent event) {
	        File file = new File(root.toFile(), "toggled.conf");
	        if (!file.exists()) {
	            Toggled toggled = new Toggled();
	            toggled.setName("Toggled people");
	            RootConfig config = new RootConfig();
	            config.getCategories().add(toggled);
	            saveConfig(config, file.toPath());
	        }
	        rootConfig = loadConfig(file.toPath());
	        for (Toggled toggled : rootConfig.getCategories()) {
                toggledID = (ArrayList<UUID>) toggled.getToggledID();
                toggledMembers = (ArrayList<UUID>) toggled.getToggledMem();
            }
	    }
        private void saveConfig(RootConfig config, Path path) {
            try {
                if (!path.toFile().getParentFile().exists()) {
                    Files.createDirectories(path.toFile().getParentFile().toPath());
                }
                ObjectMapper.BoundInstance configMapper = ObjectMapper.forObject(config);
                HoconConfigurationLoader hcl = HoconConfigurationLoader.builder().setPath(path).build();
                SimpleConfigurationNode scn = SimpleConfigurationNode.root();
                configMapper.serialize(scn);
                hcl.save(scn);
            } catch (Exception e) {
                throw new RuntimeException("Could not write file. ", e);
            }
        }
	    private RootConfig loadConfig(Path path) {
            try {
                logger.info("Loading config...");
                ObjectMapper<RootConfig> mapper = ObjectMapper.forClass(RootConfig.class);
                HoconConfigurationLoader hcl = HoconConfigurationLoader.builder().setPath(path).build();
                return mapper.bind(new RootConfig()).populate(hcl.load());
            } catch (Exception e) {
                throw new RuntimeException("Could not load file " + path, e);
            }
        }
        
        private RootConfig rootConfig;
        
        @Inject
        private Logger logger;

        @Inject
        @ConfigDir(sharedRoot = false)
        private Path root;


        @Inject
        @DefaultConfig(sharedRoot = false)
        private ConfigurationLoader<CommentedConfigurationNode> configManager;

        
	    @Inject
	    @DefaultConfig(sharedRoot =false)
	    private Path defaultConfig;

	    @Inject
	    @ConfigDir(sharedRoot = false)
	    
	    private Path privateConfigDir;
	    public Optional<User> getUser(UUID owner) {
	       Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
	        return userStorage.get().get(owner);
	    }
	    CommandSpec plotmemtoggle = CommandSpec.builder()
	    	    .description(Text.of("Toggle for Plot members"))
	    	    .permission("DarwinPlotID.Toggle")
	    	    .executor(new TogglePlotMembers())
	    	    .build();
	    CommandSpec plotidtoggle = CommandSpec.builder()
	    	    .description(Text.of("Toggle for Plot ID"))
	    	    .permission("DarwinPlotID.Toggle")
	    	    .executor(new TogglePlotID())
	    	    .build();
	    
		CommandSpec toggle = CommandSpec.builder()
				.description(Text.of("Toggle main command"))
				.permission("DarwinPlotID.Toggle")
				.child(plotidtoggle, "id")   	  
				.child(plotmemtoggle, "member")
				.build();
		public class TogglePlotID implements CommandExecutor {
		    @Override
		    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		    	Player player = (Player) src;
		    	 File file = new File(root.toFile(), "toggled.conf");
		    	 BarPlayer barP = new BarPlayer(player);
		    	if (toggledID.contains(player.getUniqueId())){
		    	toggledID.remove(player.getUniqueId());
		    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Bar Preference to ", TextColors.RED, "on"));
		    	barP.setBarBool(false);
		        }
		    	else {
		    		toggledID.add(player.getUniqueId());
			    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Bar Preference to ", TextColors.RED, "off"));
			    	barP.setBarBool(true);
		    	}
		    	  allPlayers.put(player.getUniqueId(), barP);
	            Toggled toggled = new Toggled();
	            RootConfig config = new RootConfig();
	            toggled.setToggledID(toggledID);
	            toggled.setToggledMem(toggledMembers);
	            config.getCategories().add(toggled);
	            saveConfig(config, file.toPath());
		     return CommandResult.success();
		    }
		}
		

		public class TogglePlotMembers implements CommandExecutor {
		    @Override
		    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		    	File file = new File(root.toFile(), "toggled.conf");
		    	Player player = (Player) src;
		    	BarPlayer barP = new BarPlayer(player);
		    	if (toggledMembers.contains(player.getUniqueId())){
		    	toggledMembers.remove(player.getUniqueId());
		    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Members Preference to ", TextColors.RED, "on"));
		    	barP.setMembersBool(false);
		        }
		    	else {
		    		toggledMembers.add(player.getUniqueId());
			    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Members Preference to ", TextColors.RED, "off"));
			    	barP.setMembersBool(true);
		    	}
		    	  allPlayers.put(player.getUniqueId(), barP);
	            Toggled toggled = new Toggled();
	            RootConfig config = new RootConfig();
	            toggled.setToggledID(toggledID);
	            toggled.setToggledMem(toggledMembers);
	            config.getCategories().add(toggled);
	            saveConfig(config, file.toPath());
		     return CommandResult.success();
		    }
		}


	    
	    public class move {
	    	private void check(MoveEntityEvent event, Player player) {
	    		com.intellectualcrafters.plot.object.Location loc = new com.intellectualcrafters.plot.object.Location();
	    		loc.setX(event.getToTransform().getLocation().getBlockX());
	    		loc.setY(event.getToTransform().getLocation().getBlockY());
	    		loc.setZ(event.getToTransform().getLocation().getBlockZ());
	    		loc.setWorld(event.getToTransform().getLocation().getExtent().getName().toString());
	    		if (Plot.getPlot(loc) != null) {
	    			Plot plot = Plot.getPlot(loc);
	    			doBar(plot, player);	
	    		}
	    		else {
	    			BarPlayer barP = new BarPlayer(player);
	    			if (barP.hasPlotTime) {
	    				Sponge.getCommandManager().process(player, "ptime reset");
	    				 barP.setPlotTime(false);
	    			}
	    	    	   if (barP.getIDBar() != null) {
		     	    	   ServerBossBar oldBar1 = barP.getIDBar();
		     	    	  oldBar1.removePlayer(player);
		    	    	   }
		    	    	   if (barP.getMemBar() != null) {
		    	    	   ServerBossBar oldBar2 = barP.getMemBar();
				    	   oldBar2.removePlayer(player);
		    	    	   }
		    	    	   allPlayers.put(player.getUniqueId(), barP);
	    		}
	    	}
	    	@Listener
	    	public void onMove(MoveEntityEvent event, @First Player player2) { 
	    		check(event, player2);
	    	}
	    	
	    	@Listener
	    	public void onTeleport(MoveEntityEvent.Teleport event, @First Player player2) { 
	    		check(event, player2);
				
	    	}
	    	private void doBar(Plot plot, Player player) {
	      		ServerBossBar bossBarA, bossBarB;
	    		BarPlayer barP = new BarPlayer(player);
				PlotId ID = plot.getId();
	    	    Set<UUID> owner = plot.getOwners();
	    		String actualowner;
	 
	    		if (barP.getBarBool()) {
	    			

	    			if (barP.getIDBar() != null) {
	    				ServerBossBar idBar = barP.getIDBar();
	    				idBar.removePlayer(player);
	    			}
	    			if (barP.getMemBar() != null) {
	    				ServerBossBar membersBar = barP.getIDBar();
	    				membersBar.removePlayer(player);
	    			}
	    			return;
	    		}
	    		
	    		if (barP.getMembersBool()) {
	    	
	    			if (barP.getMemBar() != null) {
	    				ServerBossBar membersBar = barP.getIDBar();
	    				membersBar.removePlayer(player);
	    			}
	    		}
				try {
					actualowner = getUser(owner.iterator().next()).get().getName();
				} catch (Exception e) {
					// TODO: handle exception
					actualowner = "Unowned";
				}
				if (actualowner.equals("Unowned")) {
					
	    	    	   if (barP.getIDBar() != null) {
		     	    	   ServerBossBar oldBar1 = barP.getIDBar();
		     	    	  oldBar1.removePlayer(player);
		    	    	   }
		    	    	   if (barP.getMemBar() != null) {
		    	    	   ServerBossBar oldBar2 = barP.getMemBar();
				    	   oldBar2.removePlayer(player);
		    	    	   }
		    	    	   allPlayers.put(player.getUniqueId(), barP);
					
					return;
				}
				String worldname = plot.getWorldName();
	    		HashSet<UUID> trusted = PlotPlayer.wrap(player).getCurrentPlot().getTrusted();
	    		com.google.common.base.Optional<String> description = PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION);
	    		Text IDM = Text.of(TextColors.DARK_AQUA, "Plot ID : ", TextColors.AQUA, worldname,";", TextColors.AQUA, ID, TextColors.WHITE ," |-=-| ");
	    		Text OwnerName = Text.of(TextColors.DARK_AQUA,"Owner : ",TextColors.AQUA, actualowner);
	    		
		    		if (!plot.getAlias().isEmpty()) {
		    			IDM = Text.of(PlotPlayer.wrap(player).getCurrentPlot().getAlias().replaceAll("&", "§"), TextColors.WHITE," |-=-| ");
		    			Set<Plot> plots = PlotPlayer.wrap(player).getPlots();
		    		}
		  
	    	    bossBarA = ServerBossBar.builder()
	    	             .name(Text.of(
	    	                   TextColors.DARK_AQUA, IDM, OwnerName
	    	              ))
	    	               .percent(1f)
	    	               .color(BossBarColors.WHITE)
	    	               .overlay(BossBarOverlays.PROGRESS)
	    	                .build();
	    	       bossBarB = ServerBossBar.builder()
	    	               .name(Text.of(
	    	                  TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Members : ", TextColors.AQUA, trusted.size())
	    	              ))
	    	               .percent(1f)
	    	               .color(BossBarColors.BLUE)
	    	              .overlay(BossBarOverlays.PROGRESS)
	    	               .build();
	    	       if (barP.getIDBar() != null) {
	    	    	    barP.setLastPlot(worldname + plot.getId().toString());
	    	    	   if (plot.getFlag(Flags.PRICE).isPresent()) {
	    	    		   if (!barP.hasPlotTime) {
	    	    		   Sponge.getCommandManager().process(player, "ptime set " + plot.getFlag(Flags.PRICE).get().intValue());
	    	    		   barP.setPlotTime(true);
	    	    	   }
	    	    	   }
	    	    	   else {
	    	    		   if (barP.hasPlotTime) {
	    	    			   Sponge.getCommandManager().process(player, "ptime reset");
	    	    			   barP.setPlotTime(false);
	    	    		   }
	    	    	   }
	    	    	   if (barP.getIDBar() != null) {
	     	    	   ServerBossBar oldBar1 = barP.getIDBar();
	     	    	  oldBar1.removePlayer(player);
	    	    	   }
	    	    	   if (barP.getMemBar() != null) {
	    	    	   ServerBossBar oldBar2 = barP.getMemBar();
			    	   oldBar2.removePlayer(player);
	    	    	   }
	    	    	   barP.setIDBar(bossBarA);
	    	    	   bossBarA.addPlayer(player);
	    	    	   if (!barP.getMembersBool()) {
		    	       barP.setMemBar(bossBarB);
		    	       bossBarB.addPlayer(player);
	    	    	   }
		    	       allPlayers.put(player.getUniqueId(), barP);
	    	       }
	    	       else {
	    	    	   if (barP.getIDBar() != null) {
		     	    	   ServerBossBar oldBar1 = barP.getIDBar();
		     	    	  oldBar1.removePlayer(player);
		    	    	   }
		    	    	   if (barP.getMemBar() != null) {
		    	    	   ServerBossBar oldBar2 = barP.getMemBar();
				    	   oldBar2.removePlayer(player);
		    	    	   }
		    	    	   barP.setIDBar(bossBarA);
		    	    	   bossBarA.addPlayer(player);
		    	    	   if (!barP.getMembersBool()) {
			    	       barP.setMemBar(bossBarB);
			    	       bossBarB.addPlayer(player);
		    	    	   }
		    	    	   allPlayers.put(player.getUniqueId(), barP);
	    	       }
	    	}
	}
	}