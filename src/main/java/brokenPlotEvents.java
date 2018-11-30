//
//public class brokenPlotEvents {
//    public class plotLeave {	
//    	@Listener
//    	public void onMove(MoveEntityEvent event, @First Player player) {
//    	public void onLeave(PlayerLeavePlotEvent event) {
//    		Player player = event.getPlayer();
//	    	if (bossbarmapA.containsKey(player.getName())) {
//    	    	bossBarA = bossbarmapA.get(player.getName());
//    	    	bossBarA.removePlayer(player);
//	    	}
//	      	if (bossbarmapB.containsKey(player.getName())) {
//    	    	bossBarB = bossbarmapB.get(player.getName());
//    	    	bossBarB.removePlayer(player);
//	    	}
//	      	
//	      	bossbarmapA.remove(player.getName(), bossBarB);
//	      	bossbarmapB.remove(player.getName(), bossBarB);
//			if (plottime.containsKey(player.getName())) {
//    			Sponge.getCommandManager().process(player, "ptime reset");
//    			plottime.remove(player.getName());
//    			}
//    	}
//    }
//    public class plotEnter {	
//    	@Listener
//    	public void onMove(PlayerEnterPlotEvent event) {
//    		Player player = event.getPlayer();
//    		if (PlotPlayer.wrap(player).getCurrentPlot() != null && PlotPlayer.wrap(player).getCurrentPlot().getOwners().isEmpty()) {
//    			return;
//    		}
//@ -518,6 +370,11 @@ import org.spongepowered.api.entity.living.player.User;
//	    			members = firstTrusted + ", " + secondTrusted;
//    			}
//    		}
//    		
//    		if (trusted.contains("*")) {
//    			members = "Everyone";
//    		}
//    		HashSet<UUID> Members = PlotPlayer.wrap(player).getCurrentPlot().getMembers();
//    	     if (PlotPlayer.wrap(player).getCurrentPlot().getFlag(Flags.DESCRIPTION).isPresent())
//    	       {
//	        	  description1 = description.get();
//@ -546,7 +403,6 @@ import org.spongepowered.api.entity.living.player.User;
//    	               .color(BossBarColors.WHITE)
//    	              .overlay(BossBarOverlays.PROGRESS)
//    	               .build();
//	    	   if (!bossbarmapA.containsKey(player.getName())) {
//	    	    	if (actualowner != "Unowned") 
//	    	    	{
//	    	        bossBarA.addPlayer(player);
//@ -570,21 +426,6 @@ import org.spongepowered.api.entity.living.player.User;
//	    	        bossbarmapB.put(player.getName(), bossBarB);
//	   	    }
//    		}
//    		else if (bossbarmapA.containsKey(player.getName())) {
//	    	    	bossBarA = bossbarmapA.get(player.getName());
//	    	    	bossBarB = bossbarmapB.get(player.getName());
//	     	    	if (bossbarmapA.containsKey(player.getName())) {
//		    	    	bossBarA = bossbarmapA.get(player.getName());
//		    	    	bossBarA.removePlayer(player);
//	    	    	}
//	    	      	if (bossbarmapB.containsKey(player.getName())) {
//		    	    	bossBarB = bossbarmapB.get(player.getName());
//		    	    	bossBarB.removePlayer(player);
//	    	    	}
//	    	      	
//			    	 bossbarmapA.remove(player.getName(), bossBarA);
//			    	 bossbarmapB.remove(player.getName(), bossBarB);
//	    	}
//    	}
//    }
//}
//
//}
