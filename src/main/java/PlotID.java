import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.Sponge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.Optional;
import org.spongepowered.api.command.spec.CommandSpec;

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

import org.spongepowered.api.entity.living.player.User;

	@Plugin(id = "plotidbossbar", name = "Darwin PlotID boss bar", version = "1.0", description = "Plot ID boss bar")
	public class PlotID {
	    @Listener
	    public void onServerFinishLoad(GameStartedServerEvent event) {

		// Hey! The server has started!
	        // Try instantiating your logger in here.
	        // (There's a guide for that)
	    	Sponge.getEventManager().registerListeners(this, new moveEvent());
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
        public static void saveConfig(RootConfig config, Path path) {
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
	    public static Optional<User> getUser(UUID owner) {
	       Optional<UserStorageService> userStorage = Sponge.getServiceManager().provide(UserStorageService.class);
	        return userStorage.get().get(owner);
	    }
	    CommandSpec plotmemtoggle = CommandSpec.builder()
	    	    .description(Text.of("Toggle for Plot members"))
	    	    .permission("DarwinPlotID.Toggle")
	    	    .executor(new ToggleExecutors.TogglePlotMembers(root))
	    	    .build();
	    CommandSpec plotidtoggle = CommandSpec.builder()
	    	    .description(Text.of("Toggle for Plot ID"))
	    	    .permission("DarwinPlotID.Toggle")
	    	    .executor(new ToggleExecutors.TogglePlotID(root))
	    	    .build();
	    
		CommandSpec toggle = CommandSpec.builder()
				.description(Text.of("Toggle main command"))
				.permission("DarwinPlotID.Toggle")
				.child(plotidtoggle, "id", "bar")   	  
				.child(plotmemtoggle, "member")
				.build();
		
}