package fr.azgardien.bang;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TabTask extends BukkitRunnable {

	private final Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
	
	@Override
	public void run() {
		Team team = null;
		if (BangController.getInstance().startTask) {
			ArrayList<Joueur> list = BangController.getInstance().getPlayers();
			for (Joueur j : list) {
				if (board.getTeam(""+j.getVie()) == null) {
					team = board.registerNewTeam(""+j.getVie());
				} else {
					team = board.getTeam(""+j.getVie());
					Player p = BangController.getInstance().getPlayerServer(j);
					team.addPlayer(p);
					p.setPlayerListName("§e" + p.getDisplayName() + " :  §c" +team.getDisplayName() + " pv" );
				}
			}
		}
	}

}
