import java.sql.SQLException;
import java.util.UUID;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.entity.living.player.Player;

public class BarPlayer {
	private ServerBossBar IDBossBar;
	
	public ServerBossBar getIDBar() {
		return IDBossBar;
	}
	public void setIDBar(ServerBossBar bar) {
		IDBossBar = bar;
	}
	
	private ServerBossBar MemBossBar;
	
	public ServerBossBar getMemBar() {
		return MemBossBar;
	}
	public void setMemBar(ServerBossBar bar) {
		MemBossBar = bar;
	}
	
	private boolean BarBool = false;
	private boolean MembersBool = false;
	public void clearBars() {
		this.IDBossBar = null;
		this.MemBossBar = null;
	}
	public void setBarBool(Boolean bool) {
		this.BarBool = bool;
	}
	
	public void setMembersBool(Boolean bool) {
		this.MembersBool = bool;
	}
	
	public boolean getBarBool() {
		return BarBool;
	}
	
	public boolean getMembersBool() {
		return MembersBool;
	}
	
	private String lastPlot = "";
	public void setLastPlot(String string) {
		this.lastPlot = string;
	}
	public String getLastPlot() {
		return lastPlot;
	}
	
	public boolean hasPlotTime;
	
	public void setPlotTime(Boolean bool) {
		this.hasPlotTime = bool;
	}
	
	public boolean getPlotTime() {
		return hasPlotTime;
	}
	
	public BarPlayer(Player player) {

		if (PlotID.allPlayers.containsKey(player.getUniqueId())) {
		this.setIDBar(PlotID.allPlayers.get(player.getUniqueId()).getIDBar());
		this.setMemBar(PlotID.allPlayers.get(player.getUniqueId()).getMemBar());
		this.setBarBool(PlotID.allPlayers.get(player.getUniqueId()).getBarBool());
		this.setMembersBool(PlotID.allPlayers.get(player.getUniqueId()).getMembersBool());
		this.setLastPlot(PlotID.allPlayers.get(player.getUniqueId()).getLastPlot());
		this.setPlotTime(PlotID.allPlayers.get(player.getUniqueId()).getPlotTime());
		}
		else {
			if (PlotID.toggledID.contains(player.getUniqueId())) {
				this.setBarBool(true);
			}
			if (PlotID.toggledMembers.contains(player.getUniqueId())) {
				this.setMembersBool(true);
			}
		}
	}
}
