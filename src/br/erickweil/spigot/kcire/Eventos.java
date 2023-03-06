/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.erickweil.spigot.kcire;

import java.net.InetAddress;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.metadata.FixedMetadataValue;
/**
 *
 * @author Usuario
 */
public class Eventos implements Listener {
    KcirePlugin plugin;
	public Eventos(KcirePlugin plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}
    	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		player.sendMessage("Seja bem vindo "+player.getDisplayName()+"!");
		player.sendMessage("use /login para fazer login, ou /register se voc� ainda n�o tem uma conta");
		// assim engra?adinhos n?o v?o entrar na conta do operador
		//player.setOp(false); mas ? ruim q tem q dar op toda hora, com login n tem problema
		player.setGameMode(GameMode.SPECTATOR); // assim n vai bugar o login
		player.setAllowFlight(true);
		player.setFlying(true);
		//player.setMaxHealth(20.0);
		
		player.setMetadata("testando.logado", new FixedMetadataValue(plugin, false));
		
		plugin.onlinePlayers.put(player.getName(), player);
		plugin.setPlayerId(player);
	}
        
        @EventHandler
	public void onChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		String message = event.getMessage();
		String message_prefix = "";
		if(plugin.estaLogado(player))
		{
			if(player.isOp())
			{
				message_prefix = ChatColor.DARK_PURPLE+"[OP] "+ChatColor.GOLD+player.getDisplayName()+": "+ChatColor.GREEN;
			}
			else
			{
				if(player.hasPermission("testando.vip"))
				{
					message_prefix = ChatColor.DARK_PURPLE+"[VIP] "+ChatColor.GOLD+player.getDisplayName()+ChatColor.WHITE+": "+ChatColor.YELLOW;
				}
				else
				{
					message_prefix = ChatColor.WHITE+player.getDisplayName()+": "+ChatColor.YELLOW;
				}
			}
			event.setMessage(message);
			event.setFormat(message_prefix+"%2$s");
		}
		else
		{
                    // n�o deixa deslogados falarem nada.
			event.setCancelled(true);
		}
	}
        
        @EventHandler
	public void onPing(ServerListPingEvent event)
	{
		event.setMotd(ChatColor.BOLD+""+ChatColor.DARK_GREEN+"Venha jogar no Kcire Server!");
		InetAddress address = event.getAddress();
		System.out.println("Olhou o ping:"+	address.getHostAddress());
        }
        
}
