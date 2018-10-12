//import org.spongepowered.api.event.Listener;
//import org.spongepowered.api.event.entity.MoveEntityEvent;
//import org.spongepowered.api.event.filter.cause.First;
//import org.spongepowered.api.event.game.state.GameStartedServerEvent;
//import org.spongepowered.api.event.network.ClientConnectionEvent;
//import org.spongepowered.api.plugin.Plugin;
//import org.spongepowered.api.service.user.UserStorageService;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//import org.spongepowered.api.world.Location;
//import org.apache.commons.lang3.Validate;
//import org.spongepowered.api.Sponge;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.UUID;
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
//import java.nio.file.Path;
//import com.google.inject.Inject;
//import org.spongepowered.api.config.ConfigDir;
//import org.spongepowered.api.config.DefaultConfig;
//import ninja.leaping.configurate.commented.CommentedConfigurationNode;
//import ninja.leaping.configurate.loader.ConfigurationLoader;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.entity.living.player.User;
//
//	//@Plugin(id = "plotidbossbar", name = "Darwin PlotID boss bar", version = "1.0", description = "Plot ID boss bar and plot time")
//	public class javabakup {
//	    @Listener
//	    public void onServerFinishLoad(GameStartedServerEvent event) {
//
//		// Hey! The server has started!
//	        // Try instantiating your logger in here.
//	        // (There's a guide for that)
//	    	Sponge.getEventManager().registerListeners(this, new plotCheck());
//	    	Sponge.getEventManager().registerListeners(this, new teleport());
//	    	Sponge.getCommandManager().register(this, plotidtoggle, "toggleplotID");
//	    	Sponge.getCommandManager().register(this, plotmemtoggle, "toggleplotMembers");
//	    }
//	    public ArrayList <UUID> toggled = new ArrayList<>();
//	    public ArrayList <UUID> toggledmembers = new ArrayList<>();
//	    @Inject
//	    @DefaultConfig(sharedRoot =false)
//	    private Path defaultConfig;
//
//	    @Inject
//	    @DefaultConfig(sharedRoot = false)
//	    private ConfigurationLoader<CommentedConfigurationNode> configManager;
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
//
//	    HashMap<String, Boolean> plottime = new HashMap<>();
//	    
//	    public Optional<User> getUser(UUID owner) {
//	       Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
//	        return userStorage.get().get(owner);
//	    }
//	    CommandSpec plotidtoggle = CommandSpec.builder()
//	    	    .description(Text.of("Toggle for Plot ID"))
//	    	    .permission("DarwinPlotID.Toggle")
//	    	    .executor(new TogglePlotID())
//	    	    .build();
//		public class TogglePlotID implements CommandExecutor {
//		    @Override
//		    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
//		    	Player player = (Player) src;
//		    	if (toggled.contains(player.getUniqueId())){
//		    	toggled.remove(player.getUniqueId());
//		        }
//		    	else {
//		    		toggled.add(player.getUniqueId());
//		    	}
//		    	player.sendMessage(Text.of(TextColors.GOLD , "Updating PlotID Bar Preference"));
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
//		    	Player player = (Player) src;
//		    	if (toggledmembers.contains(player.getUniqueId())){
//		    	toggledmembers.remove(player.getUniqueId());
//		        }
//		    	else {
//		    		toggledmembers.add(player.getUniqueId());
//		    	}
//		    	player.sendMessage(Text.of(TextColors.GOLD , "Updating PlotID Bar Preference"));
//		     return CommandResult.success();
//		    }
//		}
//		
//
//	    public class teleport {
//	    	@Listener
//	    	public void onTeleport(MoveEntityEvent.Teleport event, @First Player player) {  		
//		    			if (bossbarmapA.containsKey(player.getName())) {
//		    				bossBarA = bossbarmapA.get(player.getName());
//		    				bossBarB = bossbarmapB.get(player.getName());
//				    	    	if (bossbarmapA.containsKey(player.getName())) {
//					    	    	bossBarA = bossbarmapA.get(player.getName());
//					    	    	bossBarA.removePlayer(player);
//					    	      	bossbarmapA.remove(player.getName(), bossBarB);
//				    	    	}
//				    	      	if (bossbarmapB.containsKey(player.getName())) {
//					    	    	bossBarB = bossbarmapB.get(player.getName());
//					    	    	bossBarB.removePlayer(player);
//					    	    	bossbarmapB.remove(player.getName(), bossBarB);
//				    	    	}
//    	
//		    				}
//		    			if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//			
//		    			}
//		    	  		if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//				    	    if (toggled.contains(player.getUniqueId())) {
//				    	    	if (bossbarmapA.containsKey(player.getName())) {
//					    	    	bossBarA = bossbarmapA.get(player.getName());
//					    	    	bossBarA.removePlayer(player);
//				    	    	}
//				    	      	if (bossbarmapB.containsKey(player.getName())) {
//					    	    	bossBarB = bossbarmapB.get(player.getName());
//					    	    	bossBarB.removePlayer(player);
//				    	    	}
//				    	      	
//				    	      	bossbarmapA.remove(player.getName(), bossBarB);
//				    	      	bossbarmapB.remove(player.getName(), bossBarB);
//				    	   	return;
//				    	    }
//				    	    else { 
//				    	    	if (toggledmembers.contains(player.getUniqueId())) {
//				    	      	if (bossbarmapB.containsKey(player.getName())) {
//					    	    	bossBarB = bossbarmapB.get(player.getName());
//					    	    	bossBarB.removePlayer(player);
//				    	    	}
//				    	      	bossbarmapB.remove(player.getName(), bossBarB);
//				    	    }
//				    	    }
//		    			PlotId ID = PlotPlayer.wrap(player).getCurrentPlot().getId();
//			    	    Set<UUID> owner = PlotPlayer.wrap(player).getCurrentPlot().getOwners();
//			    		String actualowner;
//						try {
//							actualowner = getUser(owner.iterator().next()).get().getName();
//						} catch (Exception e) {
//							// TODO: handle exception
//							actualowner = "Unowned";
//						}
//						String worldname = player.getWorld().getName();
//
//			    		HashSet<UUID> trusted = PlotPlayer.wrap(player).getCurrentPlot().getTrusted();
//			    		com.google.common.base.Optional<String> description = PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION);
//			    		Text IDM = Text.of(TextColors.DARK_AQUA, "Plot ID : ", TextColors.AQUA, worldname,";", TextColors.AQUA, ID, TextColors.WHITE ," |-=-| ");
//			    		Text OwnerName = Text.of(TextColors.DARK_AQUA,"Owner : ",TextColors.AQUA, actualowner);
//			    	     if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION).isPresent())
//			    	       {
//		  	        	  description1 = description.get();
//			    	       }
//			    	    bossBarA = ServerBossBar.builder()
//			    	             .name(Text.of(
//			    	                   TextColors.DARK_AQUA, IDM, OwnerName
//			    	              ))
//			    	               .percent(1f)
//			    	               .color(BossBarColors.WHITE)
//			    	               .overlay(BossBarOverlays.PROGRESS)
//			    	                .build();
//			    	       bossBarB = ServerBossBar.builder()
//			    	               .name(Text.of(
//			    	                  TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Members : ", TextColors.AQUA, trusted.size())
//			    	              ))
//			    	               .percent(1f)
//			    	               .color(BossBarColors.BLUE)
//			    	              .overlay(BossBarOverlays.PROGRESS)
//			    	               .build();
//			    	       bossBarC = ServerBossBar.builder()
//			    	               .name(Text.of(
//			    	                  TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Description : " ,TextColors.AQUA, description1)
//			    	              ))
//			    	               .percent(1f)
//			    	               .color(BossBarColors.WHITE)
//			    	              .overlay(BossBarOverlays.PROGRESS)
//			    	               .build();
//				    	   if (!bossbarmapA.containsKey(player.getName())) {
//				    	    	if (actualowner != "Unowned") 
//				    	    	{
//				    	        bossBarA.addPlayer(player);
//				    	    	}
//				    	        if (trusted.size() != 0)
//				    	        {
//				    	        bossBarB.addPlayer(player);
//				    	        }
//				    	        if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION).isPresent())
//					    	       {
//				    	        	player.sendMessage(Text.of(TextColors.DARK_AQUA, "Plot Description : " ,TextColors.AQUA, description1));
//				    	      		Double X, Y, Z;
//				      				X = player.getLocation().getX();
//				      				Y = player.getLocation().getY();
//				      				Z = player.getLocation().getZ();
//				    	        	player.playSound(SoundTypes.BLOCK_NOTE_PLING, new Vector3d(X, Y, Z), 1, 1, 1);
//					    	       }
//				    	        bossbarmapA.put(player.getName(), bossBarA);
//				    	        bossbarmapB.put(player.getName(), bossBarB);
//				   	    }
//			    		else if (bossbarmapA.containsKey(player.getName())) {
//				    	    	bossBarA = bossbarmapA.get(player.getName());
//				    	    	bossBarB = bossbarmapB.get(player.getName());
//				     	    	if (bossbarmapA.containsKey(player.getName())) {
//					    	    	bossBarA = bossbarmapA.get(player.getName());
//					    	    	bossBarA.removePlayer(player);
//				    	    	}
//				    	      	if (bossbarmapB.containsKey(player.getName())) {
//					    	    	bossBarB = bossbarmapB.get(player.getName());
//					    	    	bossBarB.removePlayer(player);
//				    	    	}
//				    	      	
//						    	 bossbarmapA.remove(player.getName(), bossBarA);
//						    	 bossbarmapB.remove(player.getName(), bossBarB);
//				    	}
//			    	}
//		    			}
//	    }			
//	    
//	    public void dostuff(Player player) {
//	    	
//	    }
//	    public class plotCheck {	
//	    	@Listener
//	    	public void onMove(MoveEntityEvent event, @First Player player) {
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//		    	    if (toggledmembers.contains(player.getUniqueId())) {
//		    	      	if (bossbarmapB.containsKey(player.getName())) {
//			    	    	bossBarB = bossbarmapB.get(player.getName());
//			    	    	bossBarB.removePlayer(player);
//		    	    	}
//		    	      	bossbarmapB.remove(player.getName(), bossBarB);
//		    	    }
//    			}
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//	    			//do time stuff
//	    			if (!plottime.containsKey(player.getName())) {
//	    			if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.PRICE).isPresent()) {
//	    				Sponge.getCommandManager().process(player, "ptime set " + PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.PRICE).get().intValue());
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
//	    		
//	    		if (PlotPlayer.wrap(player).getCurrentPlot() != null) {
//	    	 	    if (bossbarmapA.containsKey(player.getName())) {
//		    	    	bossBarA = bossbarmapA.get(player.getName());
//		    	    	bossBarB = bossbarmapB.get(player.getName());
//	    	 	    }
//		    	    if (toggled.contains(player.getUniqueId())) {
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
//	    		PlotId ID = PlotPlayer.wrap(player).getCurrentPlot().getId();
//	    	    Set<UUID> owner = PlotPlayer.wrap(player).getCurrentPlot().getOwners();
//	    		String actualowner;
//				try {
//					actualowner = getUser(owner.iterator().next()).get().getName();
//				} catch (Exception e) {
//					// TODO: handle exception
//					actualowner = "Unowned";
//				}
//				String worldname = player.getWorld().getName();
//
//	    		HashSet<UUID> trusted = PlotPlayer.wrap(player).getCurrentPlot().getTrusted();
//	    		com.google.common.base.Optional<String> description = PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION);
//	    		Text IDM = Text.of(TextColors.DARK_AQUA, "Plot ID : ", TextColors.AQUA, worldname,";", TextColors.AQUA, ID, TextColors.WHITE ," |-=-| ");
//	    		Text OwnerName = Text.of(TextColors.DARK_AQUA,"Owner : ",TextColors.AQUA, actualowner);
//	    	     if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION).isPresent())
//	    	       {
//  	        	  description1 = description.get();
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
//	    	                  TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Members : ", TextColors.AQUA, trusted.size())
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
//		    	        if (trusted.size() != 0)
//		    	        {
//		    	        bossBarB.addPlayer(player);
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
//	    	}
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
//	} 	    
