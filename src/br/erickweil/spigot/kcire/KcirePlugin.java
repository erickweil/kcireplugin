/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.erickweil.spigot.kcire;

import br.erickweil.mysqlhelper.HelperDB;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Usuario
 */
public class KcirePlugin extends JavaPlugin {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    public HelperDB mydb;
    public HashMap<String, Player> onlinePlayers;
    
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
        getLogger().info("<---- Iniciando KcirePlugin ---->");
        // para salvar uma nova configura��o quando n�o houver nenhuma
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
                
        try {
            ConfigurationSection database = getConfig().getConfigurationSection("database");
            if(database != null)
            mydb = ConexaoBanco.getDB(database.getString("address"),database.getString("user"),database.getString("passw"),database.getString("database"));
            else
            getLogger().info("N�o p�de se conectar com o banco: n�o tem nada nas configura��es");
        } catch (SQLException ex) {
            Logger.getLogger(KcirePlugin.class.getName()).log(Level.SEVERE, null, ex);
        }
	onlinePlayers = new HashMap<>();	
        
        
        getServer().getPluginManager().registerEvents(new Eventos(this), this);
    }
    
    //public void herobrine()
    //{
    //    EntityTypes.spawnEntity(new EntityHerobrine(Bukkit.getWorld("world")), new Location(Bukkit.getWorld("world"), 100, 100, 100));
    //}
    
    
    @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			if(sender instanceof Player)
			{
				String cmd =command.getName().toLowerCase();
				Player player = (Player) sender;
				if(new Comandos().onCommand(player,cmd,args,this)) return true;
				//if(new BlockCommands().onCommand(player,cmd,args,this)) return true;
				//if(new EconomyCommands(this).onCommand(player,cmd,args)) return true;
				//if(new SkillCommands(this).onCommand(player,cmd,args)) return true;
			}
			return false;
			
		} catch (Exception e) {
			sender.sendMessage("Ops! ocorreu um erro, desculpe... erro:"+e.getMessage());
			e.printStackTrace(System.out);
			return false;
		}
	}
	
	public boolean estaLogado(Player player)
	{
		return player.hasMetadata("testando.logado") && player.getMetadata("testando.logado").get(0).asBoolean();
	}
        
       	public void setPlayerId(Player player)
	{
		int player_id;
		try {
			List<Map<String,Object>> result = mydb.getResult("SELECT id FROM login WHERE nick ='"+player.getName()+"'");
			player_id = result != null && result.size() == 1 ? (int)result.get(0).get("id") : -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player_id = -1;
		}
		player.setMetadata("testando.player_id", new FixedMetadataValue(this, player_id));
	}
	
	public int getPlayerId(Player player)
	{
		return player.hasMetadata("testando.player_id") ? player.getMetadata("testando.player_id").get(0).asInt() : -1;
	}
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        
        try {
                mydb.CloseConn();
        } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }
}
