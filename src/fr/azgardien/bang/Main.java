	package fr.azgardien.bang;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("Plugin Bang en route");
		BangController controller = BangController.getInstance();	
		BangController.getInstance().plugin = this;
		getCommand("start").setExecutor(controller);
		getCommand("choix").setExecutor(controller);
		getCommand("personnages").setExecutor(controller);
		getCommand("carlson").setExecutor(controller);
		getServer().getPluginManager().registerEvents(new BangListeners(controller,this), this);
		super.onEnable();
	}
	@Override
	public void onDisable() {
		if (BangController.getInstance().getPlayers() != null) {
			BangController.getInstance().tpFinish();
			for (Location l : BangController.getInstance().getSpotsFixe()) {
				BangController.getInstance().resetAllPlateforme(l);
				BangController.getInstance().resetPrison(l);
			}
			
		}
	}
}
