package br.erickweil.kcire;

import org.bukkit.plugin.java.JavaPlugin;

public class KcirePlugin extends JavaPlugin{
	
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	System.out.println("KCIRE PLUGIN LIGOU");
    	
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	System.out.println("KCIRE PLUGIN DESLIGOU");
    }

}
