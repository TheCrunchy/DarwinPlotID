import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.intellectualcrafters.plot.flag.Flags;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotId;

public class moveEvents {
	@Listener
	public void onMove(MoveEntityEvent event, @First Player player2) { 
		check(event, player2);
	}

	@Listener
	public void onTeleport(MoveEntityEvent.Teleport event, @First Player player2) { 
		check(event, player2);

	}
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
			barP.setLastPlot("");
			PlotID.allPlayers.put(player.getUniqueId(), barP);
		}
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
			actualowner = PlotID.getUser(owner.iterator().next()).get().getName();
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
			barP.setLastPlot("");
			PlotID.allPlayers.put(player.getUniqueId(), barP);

			return;
		}
		String worldname = plot.getWorldName();
		HashSet<UUID> trusted = plot.getTrusted();
		Object[] trustedArray = trusted.toArray();
		String firstTrusted = null, secondTrusted = null, thirdTrusted = null;
		int somenumber = 0;
		if (trustedArray.length > 0 && trustedArray[0] != null) {
			if (PlotID.getUser(UUID.fromString(trustedArray[0].toString())).isPresent()) {
				firstTrusted = PlotID.getUser(UUID.fromString(trustedArray[0].toString())).get().getName();
				somenumber += 1;
				//	System.out.println(firstTrusted);
			}
		}
		if (trustedArray.length > 1 && trustedArray[1] != null) {
			if (PlotID.getUser(UUID.fromString(trustedArray[1].toString())).isPresent()) {
				secondTrusted = PlotID.getUser(UUID.fromString(trustedArray[1].toString())).get().getName();
				somenumber += 1;
				//System.out.println(secondTrusted);
			}

		}
		String members = null;
		Boolean hasmembers = false;
		if (firstTrusted != null) {
			hasmembers = true;
			members = firstTrusted;
		}
		if (somenumber == 2) {
			if (trusted.size() > 2) {
				members = firstTrusted + ", " + secondTrusted + " and " + (trusted.size() - 2) + " others";
			}
			else {
				members = firstTrusted + ", " + secondTrusted;
			}
		}

		if (trusted.contains("*")) {
			members = "Everyone";
		}
		Text IDM = Text.of(TextColors.DARK_AQUA, "Plot ID : ", TextColors.AQUA, worldname,";", TextColors.AQUA, ID, TextColors.WHITE ," |-=-| ");
		Text OwnerName = Text.of(TextColors.DARK_AQUA,"Owner : ",TextColors.AQUA, actualowner);

		if (!plot.getAlias().isEmpty()) {
			IDM = Text.of(plot.getAlias().replaceAll("&", "§"), TextColors.WHITE," |-=-| ");

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
						TextColors.DARK_AQUA, Text.of(TextColors.DARK_AQUA, "Members : ", TextColors.AQUA, members)
						))
				.percent(1f)
				.color(BossBarColors.BLUE)
				.overlay(BossBarOverlays.PROGRESS)
				.build();
		if (!barP.getLastPlot().equals(worldname + ";" + plot.getId())) {
			//  System.out.println("Updating");
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

			bossBarA.addPlayer(player);
			barP.setIDBar(bossBarA);
			if (!barP.getMembersBool() && trusted.size() > 0) {
				bossBarB.addPlayer(player);
				barP.setMemBar(bossBarB);
				barP.setLastPlot(worldname + ";" + plot.getId());
			}
			PlotID.allPlayers.put(player.getUniqueId(), barP);
		}
		else {
			if (bossBarA != barP.getIDBar()) {
				barP.setLastPlot(worldname + ";" + plot.getId());
				PlotID.allPlayers.put(player.getUniqueId(), barP);
			}
		}
	}
}

