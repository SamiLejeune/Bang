package fr.azgardien.bang;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.azgardien.roles.Personnage;

public class BangListeners implements Listener {

	private BangController controller;
	public BangListeners(BangController controller) {
		this.controller = controller;
		System.out.println(controller);
	}


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.getInventory().clear();
		event.setJoinMessage("§a"+player.getName() + " s'est connecté !");
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		Action action = event.getAction();
		ItemStack current = event.getItem();
		
		if(current == null) return;

		if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§bInteraction")) {
			Inventory interaction = this.controller.playerInventory(this.controller.getJoueur(player));
			player.openInventory(interaction);
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		ItemStack current = event.getCurrentItem();
		
		if(current == null) return;
		
		if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§bInteraction")) {
			Inventory interaction = this.controller.playerInventory(this.controller.getJoueur(player));
			player.openInventory(interaction);
		}
		
		if (inv.getName().equalsIgnoreCase("§8Interaction")) {
			event.setCancelled(true);
		}
		
	}
	
}
