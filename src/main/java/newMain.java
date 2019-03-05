//import org.spongepowered.api.event.Listener;
//import org.spongepowered.api.event.entity.MoveEntityEvent;
//import org.spongepowered.api.event.filter.cause.First;
//import org.spongepowered.api.event.game.state.GameStartedServerEvent;
//import org.spongepowered.api.event.network.ClientConnectionEvent;
//import org.spongepowered.api.plugin.Plugin;
//import org.spongepowered.api.scheduler.Task;
//import org.spongepowered.api.service.user.UserStorageService;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//import org.spongepowered.api.world.Location;
//import org.apache.commons.lang3.Validate;
//import org.spongepowered.api.Sponge;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.UUID;
//import java.util.Map.Entry;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Logger;
//import java.util.Optional;
//import java.util.Set;
//import org.spongepowered.api.boss.BossBarColors;
//import org.spongepowered.api.boss.BossBarOverlays;
//import org.spongepowered.api.boss.ServerBossBar;
//import org.spongepowered.api.command.CommandException;
//import org.spongepowered.api.command.CommandResult;
//import org.spongepowered.api.command.CommandSource;
//import org.spongepowered.api.command.args.CommandContext;
//import org.spongepowered.api.command.spec.CommandExecutor;
//import org.spongepowered.api.command.spec.CommandSpec;
//import org.spongepowered.api.effect.sound.SoundTypes;
//
//import com.flowpowered.math.vector.Vector3d;
//import com.intellectualcrafters.plot.flag.Flags;
//import com.intellectualcrafters.plot.object.PlotId;
//import com.intellectualcrafters.plot.object.PlotPlayer;
//
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import com.google.inject.Inject;
//import org.spongepowered.api.config.ConfigDir;
//import org.spongepowered.api.config.DefaultConfig;
//
//import ninja.leaping.configurate.SimpleConfigurationNode;
//import ninja.leaping.configurate.commented.CommentedConfigurationNode;
//import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
//import ninja.leaping.configurate.loader.ConfigurationLoader;
//import ninja.leaping.configurate.objectmapping.ObjectMapper;
//
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.entity.living.player.User;
//
//	@Plugin(id = "plotidbossbar", name = "Darwin PlotID boss bar", version = "1.0", description = "Plot ID boss bar")
//	public class newMain {
//	    @Listener
//	    public void onServerFinishLoad(GameStartedServerEvent event) {
//
//		// Hey! The server has started!
//	        // Try instantiating your logger in here.
//	        // (There's a guide for that)
//	    	Sponge.getEventManager().registerListeners(this, new plotCheck());
//	    	Sponge.getEventManager().registerListeners(this, new teleport());
//	    	Sponge.getCommandManager().register(this, plotidtoggle, "toggleBar");
//	    	Sponge.getCommandManager().register(this, plotmemtoggle, "toggleMembers");
//	    	plugin = this;
//	    }
//	    Object plugin;
//	    public ArrayList <UUID> toggledID = new ArrayList<>();
//	    public ArrayList <UUID> toggledMembers = new ArrayList<>();
//	    @Listener
//	    public void onServerStart(GameStartedServerEvent event) {
//	        File file = new File(root.toFile(), "toggled.conf");
//	        if (!file.exists()) {
//	            Toggled toggled = new Toggled();
//	            toggled.setName("Toggled people");
//	            RootConfig config = new RootConfig();
//	            config.getCategories().add(toggled);
//	            saveConfig(config, file.toPath());
//	        }
//	        rootConfig = loadConfig(file.toPath());
//	        for (Toggled toggled : rootConfig.getCategories()) {
//                toggledID = (ArrayList<UUID>) toggled.getToggledID();
//                toggledMembers = (ArrayList<UUID>) toggled.getToggledMem();
//            }
//	    }
//        private void saveConfig(RootConfig config, Path path) {
//            try {
//                if (!path.toFile().getParentFile().exists()) {
//                    Files.createDirectories(path.toFile().getParentFile().toPath());
//                }
//                ObjectMapper.BoundInstance configMapper = ObjectMapper.forObject(config);
//                HoconConfigurationLoader hcl = HoconConfigurationLoader.builder().setPath(path).build();
//                SimpleConfigurationNode scn = SimpleConfigurationNode.root();
//                configMapper.serialize(scn);
//                hcl.save(scn);
//            } catch (Exception e) {
//                throw new RuntimeException("Could not write file. ", e);
//            }
//        }
//	    private RootConfig loadConfig(Path path) {
//            try {
//                logger.info("Loading config...");
//                ObjectMapper<RootConfig> mapper = ObjectMapper.forClass(RootConfig.class);
//                HoconConfigurationLoader hcl = HoconConfigurationLoader.builder().setPath(path).build();
//                return mapper.bind(new RootConfig()).populate(hcl.load());
//            } catch (Exception e) {
//                throw new RuntimeException("Could not load file " + path, e);
//            }
//        }
//        
//        private RootConfig rootConfig;
//        
//        @Inject
//        private Logger logger;
//
//        @Inject
//        @ConfigDir(sharedRoot = false)
//        private Path root;
//
//
//        @Inject
//        @DefaultConfig(sharedRoot = false)
//        private ConfigurationLoader<CommentedConfigurationNode> configManager;
//
//        
//	    @Inject
//	    @DefaultConfig(sharedRoot =false)
//	    private Path defaultConfig;
//
//	    @Inject
//	    @ConfigDir(sharedRoot = false)
//	    
//	    private Path privateConfigDir;
//	    ServerBossBar bossBarA;
//	    ServerBossBar bossBarB;
//	    ServerBossBar bossBarC;
//	    String description1 = "empty";
//	    HashMap<String, ServerBossBar> bossbarmapA = new HashMap<>();
//	    HashMap<String, ServerBossBar> bossbarmapB = new HashMap<>();
//	    HashMap<String, ServerBossBar> bossbarmapC = new HashMap<>();
//	    int time;
//	    HashMap<String, Boolean> plottime = new HashMap<>();
//	    HashMap<String, Long> plottimeupdate = new HashMap<>();
//	    HashMap<String, Long> plotTeleport = new HashMap<>();
//	    public Optional<User> getUser(UUID owner) {
//	       Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
//	        return userStorage.get().get(owner);
//	    }
//	    killMeNow bar = new killMeNow();
//	    CommandSpec plotidtoggle = CommandSpec.builder()
//	    	    .description(Text.of("Toggle for Plot ID"))
//	    	    .permission("DarwinPlotID.Toggle")
//	    	    .executor(new TogglePlotID())
//	    	    .build();
//		public class TogglePlotID implements CommandExecutor {
//		    @Override
//		    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
//		    	Player player = (Player) src;
//		    	 File file = new File(root.toFile(), "toggled.conf");
//		    	if (toggledID.contains(player.getUniqueId())){
//		    	toggledID.remove(player.getUniqueId());
//		    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Bar Preference to ", TextColors.RED, "on"));
//		        }
//		    	else {
//		    		toggledID.add(player.getUniqueId());
//			    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Bar Preference to ", TextColors.RED, "off"));
//		    	}
//		    	
//	            Toggled toggled = new Toggled();
//	            RootConfig config = new RootConfig();
//	            toggled.setToggledID(toggledID);
//	            toggled.setToggledMem(toggledMembers);
//	            config.getCategories().add(toggled);
//	            saveConfig(config, file.toPath());
//		     return CommandResult.success();
//		    }
//		}
//		
//	    CommandSpec plotmemtoggle = CommandSpec.builder()
//	    	    .description(Text.of("Toggle for Plot members"))
//	    	    .permission("DarwinPlotID.Toggle")
//	    	    .executor(new TogglePlotMembers())
//	    	    .build();
//		public class TogglePlotMembers implements CommandExecutor {
//		    @Override
//		    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
//		    	File file = new File(root.toFile(), "toggled.conf");
//		    	Player player = (Player) src;
//		    	if (toggledMembers.contains(player.getUniqueId())){
//		    	toggledMembers.remove(player.getUniqueId());
//		    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Members Preference to ", TextColors.RED, "on"));
//		        }
//		    	else {
//		    		toggledMembers.add(player.getUniqueId());
//			    	player.sendMessage(Text.of(TextColors.WHITE , "Updating PlotID Members Preference to ", TextColors.RED, "off"));
//		    	}
//		    	
//	            Toggled toggled = new Toggled();
//	            RootConfig config = new RootConfig();
//	            toggled.setToggledID(toggledID);
//	            toggled.setToggledMem(toggledMembers);
//	            config.getCategories().add(toggled);
//	            saveConfig(config, file.toPath());
//		     return CommandResult.success();
//		    }
//		}
//	    public class teleport {
//	    	@Listener
//	    	public void onTeleport(MoveEntityEvent.Teleport event, @First Player player2) { 
//	    		Player player = Sponge.getServer().getPlayer(player2.getName()).get();
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null && PlotPlayer.wrap(player).getCurrentPlot().getOwners().isEmpty()) {
//	    			return;
//	    		}
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//	    			//do time stuff
//	    			if (!plottime.containsKey(player.getName())) {
//	    			if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.PRICE).isPresent()) {
//	    				//Sponge.getCommandManager().process(player, "ptime set " + PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.PRICE).get().intValue());
//	    				Task task = Task.builder().execute(new task())
//	    	    	            .delayTicks(20)
//	    	    	            .name("Update time task").submit(plugin);
//	    				plottimeupdate.put(player.getName(), (long) Sponge.getServer().getRunningTimeTicks());
//	    				plottime.put(player.getName(), true);
//	    			}
//	    		}
//	    		}
//	    		else {
//	    			if (plottime.containsKey(player.getName())) {
//	    			Sponge.getCommandManager().process(player, "ptime reset");
//	    			plottime.remove(player.getName());
//	    			}
//	    	    }
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//		    	    if (toggledID.contains(player.getUniqueId())) {
//		    	    	if (bossbarmapA.containsKey(player.getName())) {
//			    	    	bossBarA = bossbarmapA.get(player.getName());
//			    	    	bossBarA.removePlayer(player);
//		    	    	}
//		    	      	if (bossbarmapB.containsKey(player.getName())) {
//			    	    	bossBarB = bossbarmapB.get(player.getName());
//			    	    	bossBarB.removePlayer(player);
//		    	    	}
//		    	      	
//		    	      	bossbarmapA.remove(player.getName(), bossBarB);
//		    	      	bossbarmapB.remove(player.getName(), bossBarB);
//		    	   	return;
//		    	    }
//		    	    else { 
//		    	    	if (toggledMembers.contains(player.getUniqueId())) {
//		    	      	if (bossbarmapB.containsKey(player.getName())) {
//			    	    	bossBarB = bossbarmapB.get(player.getName());
//			    	    	bossBarB.removePlayer(player);
//		    	    	}
//		    	      	bossbarmapB.remove(player.getName(), bossBarB);
//		    	    }
//		    	    }
//	    	    	if (bossbarmapA.containsKey(player.getName())) {
//		    	    	bossBarA = bossbarmapA.get(player.getName());
//		    	    	bossBarA.removePlayer(player);
//		    	    	bossbarmapA.remove(player.getName());
//	    	    	}
//	    	      	if (bossbarmapB.containsKey(player.getName())) {
//		    	    	bossBarB = bossbarmapB.get(player.getName());
//		    	    	bossBarB.removePlayer(player);
//		    	    	bossbarmapB.remove(player.getName());
//	    	    	}
//	    	      	System.out.println(bossbarmapA);
//	    		PlotId ID = PlotPlayer.wrap(player).getCurrentPlot().getId();
//	    	    Set<UUID> owner = PlotPlayer.wrap(player).getCurrentPlot().getOwners();
//	    		String actualowner;
//				try {
//					actualowner = getUser(owner.iterator().next()).get().getName();
//				} catch (Exception e) {
//					// TODO: handle exception
//					actualowner = "Unowned";
//				}
//				String worldname = PlotPlayer.wrap(player).getCurrentPlot().getWorldName();
//
//	    		HashSet<UUID> trusted = PlotPlayer.wrap(player).getCurrentPlot().getTrusted();
//	    		Object[] trustedArray = trusted.toArray();
//	    		String firstTrusted = null, secondTrusted = null, thirdTrusted = null;
//	    		int somenumber = 0;
//	    		if (trustedArray.length > 0 && trustedArray[0] != null) {
//	    			if (getUser(UUID.fromString(trustedArray[0].toString())).isPresent()) {
//		    			firstTrusted = getUser(UUID.fromString(trustedArray[0].toString())).get().getName();
//		    			somenumber += 1;
//		    		//	System.out.println(firstTrusted);
//	    			}
//	    		}
//	       		if (trustedArray.length > 1 && trustedArray[1] != null) {
//	       			if (getUser(UUID.fromString(trustedArray[1].toString())).isPresent()) {
//	       				secondTrusted = getUser(UUID.fromString(trustedArray[1].toString())).get().getName();
//	       				somenumber += 1;
//	       				//System.out.println(secondTrusted);
//	       			}
//	       			
//	    		}
//	    		com.google.common.base.Optional<String> description = PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION);
//	    		Text IDM = Text.of(TextColors.DARK_AQUA, "Plot ID : ", TextColors.AQUA, worldname,";", TextColors.AQUA, ID, TextColors.WHITE ," |-=-| ");
//	    		Text OwnerName = Text.of(TextColors.DARK_AQUA,"Owner : ",TextColors.AQUA, actualowner);
//	    		if (!PlotPlayer.wrap(player).getCurrentPlot().getAlias().isEmpty()) {
//	    			IDM = Text.of(PlotPlayer.wrap(player).getCurrentPlot().getAlias().replaceAll("&", "§"), TextColors.WHITE," |-=-| ");
//	    			//System.out.println(PlotPlayer.wrap(player).getCurrentPlot().getAlias());
//	    		}
//	    		String members = null;
//	    		Boolean hasmembers = false;
//	    		if (firstTrusted != null) {
//	    			hasmembers = true;
//	    			members = firstTrusted;
//	    		}
//	    		if (somenumber == 2) {
//	    			if (trusted.size() > 2) {
//	    				members = firstTrusted + ", " + secondTrusted + " and " + (trusted.size() - 2) + " others";
//	    			}
//	    			else {
//		    			members = firstTrusted + ", " + secondTrusted;
//	    			}
//	    		}
//	    	     if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION).isPresent())
//	    	       {
//		        	  description1 = description.get();
//	    	       }
//	    	    bossBarA = ServerBossBar.builder()
//	    	             .name(Text.of(
//	    	                   TextColors.DARK_AQUA, IDM, OwnerName
//	    	              ))
//	    	               .percent(1f)
//	    	               .color(BossBarColors.WHITE)
//	    	               .overlay(BossBarOverlays.PROGRESS)
//	    	                .build();
//	    	       bossBarB = ServerBossBar.builder()
//	    	               .name(Text.of(
//	    	                  TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Members : ", TextColors.AQUA, members)
//	    	              ))
//	    	               .percent(1f)
//	    	               .color(BossBarColors.BLUE)
//	    	              .overlay(BossBarOverlays.PROGRESS)
//	    	               .build();
//	    	       bossBarC = ServerBossBar.builder()
//	    	               .name(Text.of(
//	    	                  TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Description : " ,TextColors.AQUA, description1)
//	    	              ))
//	    	               .percent(1f)
//	    	               .color(BossBarColors.WHITE)
//	    	              .overlay(BossBarOverlays.PROGRESS)
//	    	               .build();
//	    	       System.out.println(bossbarmapA);
//		    	   if (!bossbarmapA.containsKey(player.getName())) {
//		    	    	if (actualowner != "Unowned") 
//		    	    	{
//		    	        bossBarA.addPlayer(player);
//		    	    	}
//		    	        if (hasmembers == true)
//		    	        {
//		    	        	if (!toggledMembers.contains(player.getUniqueId())) {
//		    	        		   bossBarB.addPlayer(player);
//		    	        	}
//		    	        }
//		    	        if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION).isPresent())
//			    	       {
//		    	        	player.sendMessage(Text.of(TextColors.DARK_AQUA, "Plot Description : " ,TextColors.AQUA, description1));
//		    	      		Double X, Y, Z;
//		      				X = player.getLocation().getX();
//		      				Y = player.getLocation().getY();
//		      				Z = player.getLocation().getZ();
//		    	        	player.playSound(SoundTypes.BLOCK_NOTE_PLING, new Vector3d(X, Y, Z), 1, 1, 1);
//			    	       }
//		    	        bossbarmapA.put(player.getName(), bossBarA);
//		    	        bossbarmapB.put(player.getName(), bossBarB);
//		   	    }
//	    		}
//	    		else if (bossbarmapA.containsKey(player.getName())) {
//		    	    	bossBarA = bossbarmapA.get(player.getName());
//		    	    	bossBarB = bossbarmapB.get(player.getName());
//		     	    	if (bossbarmapA.containsKey(player.getName())) {
//			    	    	bossBarA = bossbarmapA.get(player.getName());
//			    	    	bossBarA.removePlayer(player);
//		    	    	}
//		    	      	if (bossbarmapB.containsKey(player.getName())) {
//			    	    	bossBarB = bossbarmapB.get(player.getName());
//			    	    	bossBarB.removePlayer(player);
//		    	    	}
//		    	      	
//				    	 bossbarmapA.remove(player.getName(), bossBarA);
//				    	 bossbarmapB.remove(player.getName(), bossBarB);
//		    	}
//	    	}
//	    }
//	    public class plotCheck {	
//	    	@Listener
//	    	public void onMove(MoveEntityEvent event, @First Player player) {
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null && PlotPlayer.wrap(player).getCurrentPlot().getOwners().isEmpty()) {
//	    			return;
//	    		}
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//	    			//do time stuff
//	    			if (!plottime.containsKey(player.getName())) {
//	    			if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.PRICE).isPresent()) {
//	    				//Sponge.getCommandManager().process(player, "ptime set " + PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.PRICE).get().intValue());
//	    				Task task = Task.builder().execute(new task())
//	    	    	            .delayTicks(20)
//	    	    	            .name("Update time task").submit(plugin);
//	    				plottimeupdate.put(player.getName(), (long) Sponge.getServer().getRunningTimeTicks());
//	    				plottime.put(player.getName(), true);
//	    			}
//	    		}
//	    		}
//	    		else {
//	    			if (plottime.containsKey(player.getName())) {
//	    			Sponge.getCommandManager().process(player, "ptime reset");
//	    			plottime.remove(player.getName());
//	    			}
//	    	    }
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//		    	    if (toggledID.contains(player.getUniqueId())) {
//		    	    	if (bossbarmapA.containsKey(player.getName())) {
//			    	    	bossBarA = bossbarmapA.get(player.getName());
//			    	    	bossBarA.removePlayer(player);
//		    	    	}
//		    	      	if (bossbarmapB.containsKey(player.getName())) {
//			    	    	bossBarB = bossbarmapB.get(player.getName());
//			    	    	bossBarB.removePlayer(player);
//		    	    	}
//		    	      	
//		    	      	bossbarmapA.remove(player.getName(), bossBarB);
//		    	      	bossbarmapB.remove(player.getName(), bossBarB);
//		    	   	return;
//		    	    }
//		    	    else { 
//		    	    	if (toggledMembers.contains(player.getUniqueId())) {
//		    	      	if (bossbarmapB.containsKey(player.getName())) {
//			    	    	bossBarB = bossbarmapB.get(player.getName());
//			    	    	bossBarB.removePlayer(player);
//		    	    	}
//		    	      	bossbarmapB.remove(player.getName(), bossBarB);
//		    	    }
//		    	    }
//
//	    		PlotId ID = PlotPlayer.wrap(player).getCurrentPlot().getId();
//	    	    Set<UUID> owner = PlotPlayer.wrap(player).getCurrentPlot().getOwners();
//	    		String actualowner;
//				try {
//					actualowner = getUser(owner.iterator().next()).get().getName();
//				} catch (Exception e) {
//					// TODO: handle exception
//					actualowner = "Unowned";
//				}
//				String worldname = PlotPlayer.wrap(player).getCurrentPlot().getWorldName();
//
//	    		HashSet<UUID> trusted = PlotPlayer.wrap(player).getCurrentPlot().getTrusted();
//	    		Object[] trustedArray = trusted.toArray();
//	    		String firstTrusted = null, secondTrusted = null, thirdTrusted = null;
//	    		int somenumber = 0;
//	    		if (trustedArray.length > 0 && trustedArray[0] != null) {
//	    			if (getUser(UUID.fromString(trustedArray[0].toString())).isPresent()) {
//		    			firstTrusted = getUser(UUID.fromString(trustedArray[0].toString())).get().getName();
//		    			somenumber += 1;
//		    		//	System.out.println(firstTrusted);
//	    			}
//	    		}
//	       		if (trustedArray.length > 1 && trustedArray[1] != null) {
//	       			if (getUser(UUID.fromString(trustedArray[1].toString())).isPresent()) {
//	       				secondTrusted = getUser(UUID.fromString(trustedArray[1].toString())).get().getName();
//	       				somenumber += 1;
//	       				//System.out.println(secondTrusted);
//	       			}
//	       			
//	    		}
//	    		com.google.common.base.Optional<String> description = PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION);
//	    		Text IDM = Text.of(TextColors.DARK_AQUA, "Plot ID : ", TextColors.AQUA, worldname,";", TextColors.AQUA, ID, TextColors.WHITE ," |-=-| ");
//	    		Text OwnerName = Text.of(TextColors.DARK_AQUA,"Owner : ",TextColors.AQUA, actualowner);
//	    		if (!PlotPlayer.wrap(player).getCurrentPlot().getAlias().isEmpty()) {
//	    			IDM = Text.of(PlotPlayer.wrap(player).getCurrentPlot().getAlias().replaceAll("&", "§"), TextColors.WHITE," |-=-| ");
//	    			//System.out.println(PlotPlayer.wrap(player).getCurrentPlot().getAlias());
//	    		}
//	    		String members = null;
//	    		Boolean hasmembers = false;
//	    		if (firstTrusted != null) {
//	    			hasmembers = true;
//	    			members = firstTrusted;
//	    		}
//	    		if (somenumber == 2) {
//	    			if (trusted.size() > 2) {
//	    				members = firstTrusted + ", " + secondTrusted + " and " + (trusted.size() - 2) + " others";
//	    			}
//	    			else {
//		    			members = firstTrusted + ", " + secondTrusted;
//	    			}
//	    		}
//	    	     if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION).isPresent())
//	    	       {
//		        	  description1 = description.get();
//	    	       }
//	    	    bossBarA = ServerBossBar.builder()
//	    	             .name(Text.of(
//	    	                   TextColors.DARK_AQUA, IDM, OwnerName
//	    	              ))
//	    	               .percent(1f)
//	    	               .color(BossBarColors.WHITE)
//	    	               .overlay(BossBarOverlays.PROGRESS)
//	    	                .build();
//	    	       bossBarB = ServerBossBar.builder()
//	    	               .name(Text.of(
//	    	                  TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Members : ", TextColors.AQUA, members)
//	    	              ))
//	    	               .percent(1f)
//	    	               .color(BossBarColors.BLUE)
//	    	              .overlay(BossBarOverlays.PROGRESS)
//	    	               .build();
//	    	       bossBarC = ServerBossBar.builder()
//	    	               .name(Text.of(
//	    	                  TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Description : " ,TextColors.AQUA, description1)
//	    	              ))
//	    	               .percent(1f)
//	    	               .color(BossBarColors.WHITE)
//	    	              .overlay(BossBarOverlays.PROGRESS)
//	    	               .build();
//		    	   if (!bossbarmapA.containsKey(player.getName())) {
//		    	    	if (actualowner != "Unowned") 
//		    	    	{
//		    	        bossBarA.addPlayer(player);
//		    	    	}
//		    	        if (hasmembers == true)
//		    	        {
//		    	        	if (!toggledMembers.contains(player.getUniqueId())) {
//		    	        		   bossBarB.addPlayer(player);
//		    	        	}
//		    	        }
//		    	        if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION).isPresent())
//			    	       {
//		    	        	player.sendMessage(Text.of(TextColors.DARK_AQUA, "Plot Description : " ,TextColors.AQUA, description1));
//		    	      		Double X, Y, Z;
//		      				X = player.getLocation().getX();
//		      				Y = player.getLocation().getY();
//		      				Z = player.getLocation().getZ();
//		    	        	player.playSound(SoundTypes.BLOCK_NOTE_PLING, new Vector3d(X, Y, Z), 1, 1, 1);
//			    	       }
//		    	        bossbarmapA.put(player.getName(), bossBarA);
//		    	        bossbarmapB.put(player.getName(), bossBarB);
//		   	    }
//	    		}
//	    		else if (bossbarmapA.containsKey(player.getName())) {
//		    	    	bossBarA = bossbarmapA.get(player.getName());
//		    	    	bossBarB = bossbarmapB.get(player.getName());
//		     	    	if (bossbarmapA.containsKey(player.getName())) {
//			    	    	bossBarA = bossbarmapA.get(player.getName());
//			    	    	bossBarA.removePlayer(player);
//		    	    	}
//		    	      	if (bossbarmapB.containsKey(player.getName())) {
//			    	    	bossBarB = bossbarmapB.get(player.getName());
//			    	    	bossBarB.removePlayer(player);
//		    	    	}
//		    	      	
//				    	 bossbarmapA.remove(player.getName(), bossBarA);
//				    	 bossbarmapB.remove(player.getName(), bossBarB);
//		    	}
//	}
//	    }
//	    public class killMeNow {
//	    public void updateBar(Player player) {
//	    	
//    	}
//	    }
//    	public class task implements Runnable{
//			public void run() {
//				// TODO Auto-generated method stub
//				 Long tasktime = (long) (Sponge.getServer().getRunningTimeTicks()) - 20;
//					for (Entry<String, Long> entry : plottimeupdate.entrySet()) {
//					    String key1 = entry.getKey();
//				   // System.out.println("Dunno what the fuck im doing tbh");
//					//System.out.println(key1);
//					Player player = null;
//				    if (plottimeupdate.get(key1) == tasktime);
//				    {
//				    	if (Sponge.getServer().getPlayer(key1).isPresent()) {
//				    	player = Sponge.getServer().getPlayer(key1).get();
//				    	}
//				    	else 
//				    	{
//				    		plottimeupdate.remove(key1);
//				    		return;
//				    	}
//				    //	System.out.println("WHY YOU NO WORK");
//				    	if (PlotPlayer.wrap(player).getCurrentPlot() != null && PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.PRICE).isPresent()) {
//				    	Sponge.getCommandManager().process(player, "ptime set " + PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.PRICE).get().intValue());
//				    	plottimeupdate.remove(key1);
//				    	}
//				    	else {
//				    			Sponge.getCommandManager().process(player, "ptime reset");
//				    			plottime.remove(player.getName());
//				    			plottimeupdate.remove(key1);
//				    	}
//						 
//	       }
//    	}
//    }
//    	} 	
//
//	}