/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.erickweil.spigot.kcire;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author Usuario
 */
public class Comandos {
	// retorna true caso tenha havido o manuseio do comando
	public boolean onCommand(Player player, String cmd, String[] args,KcirePlugin plugin) {
		if(plugin.estaLogado(player))
		{
			
			switch(cmd)
			{
				case "r":
				case "tell":
					if((cmd.equals("tell") && args.length > 1) || (cmd.equals("r") && args.length > 0))
					{
						String Tell_name;
						if(cmd.equals("tell"))
						{
							Tell_name = args[0];
							player.setMetadata("testando.tell.r", new FixedMetadataValue(plugin, Tell_name));
						}
						else // cmd equals r
						{
							if (player.hasMetadata("testando.tell.r"))
							{
								Tell_name = player.getMetadata("testando.tell.r").get(0).asString();
							}
							else
							{
								player.sendMessage(ChatColor.RED+"Ningu�m para responder");
								return true;
							}
						}
						
						Player Tell_player = plugin.onlinePlayers.get(Tell_name);
						if(Tell_player != null)
						{
							String mensagem_enviar = ChatColor.DARK_GRAY+"(Mensagem de "+player.getDisplayName()+"):"+ChatColor.DARK_GREEN;
							String mensagem_chat = ChatColor.DARK_GRAY+"(Mensagem para "+Tell_name+"):"+ChatColor.DARK_GREEN;
							if(Tell_player == player)
							{
								mensagem_enviar = ChatColor.DARK_GRAY+"(Mensagem de voc� mesmo):"+ChatColor.DARK_GREEN;
							}
							for(int i=(cmd.equals("tell")? 1 : 0);i<args.length;i++)
							{
								mensagem_enviar += " "+args[i];
								mensagem_chat += " "+args[i];
							}
							if(Tell_player == player)
							{
								player.sendMessage(mensagem_enviar);
							}
							else
							{
								player.sendMessage(mensagem_chat);
								Tell_player.sendMessage(mensagem_enviar);								
							}
							
							Tell_player.setMetadata("testando.tell.r", new FixedMetadataValue(plugin, Tell_name));
						}
						else
						{
							player.sendMessage(ChatColor.RED+"Esse jogador n�o foi encontrado");
						}
					}
					else
					{
						player.sendMessage(ChatColor.RED+"Uso: /tell <nome-do-player> <mensagem>");
					}
					return true;
                case "herobrine":
                {
                	player.sendMessage(ChatColor.RED+"HEROBRINE FOI REMOVIDO");
                    //if(player.isOp())
                    //{
                        //plugin.herobrine();
                    //}
                }
				case "kit":
					if(args.length == 1)
					{
						String Selec_kit = args[0];
						
						if(Selec_kit.equals("op"))
						{
							if(player.isOp())
							{
								ItemStack varinha =  new ItemStack(Material.TOTEM_OF_UNDYING,64);
								varinha.addUnsafeEnchantment(Enchantment.KNOCKBACK, 20);
								{
									ItemMeta im = varinha.getItemMeta();
									im.setDisplayName(ChatColor.GREEN+ "Varinha Magica");
									ArrayList<String> lores = new ArrayList<String>();
									lores.add(ChatColor.DARK_PURPLE+ "lol vc � op");
									lores.add(ChatColor.DARK_PURPLE+ "vc pode "+ChatColor.GOLD+"TUDO");
									im.setLore(lores);
									im.addItemFlags();
									varinha.setItemMeta(im); // <---
								}
								
								ItemStack espada =  new ItemStack(Material.DIAMOND_SWORD,1);
								
								espada.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
								espada.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 5);
                                                                espada.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 3);
								espada.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                                                                espada.addUnsafeEnchantment(Enchantment.MENDING, 1);
								
								ItemStack arco =  new ItemStack(Material.BOW,1);
								
								arco.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 10);
								arco.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 5);
								arco.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
                                                                arco.addUnsafeEnchantment(Enchantment.MENDING, 1);
								
                                                                ItemStack flecha = new ItemStack(Material.TIPPED_ARROW);
                                                                PotionMeta meta = (PotionMeta) flecha.getItemMeta();
                                                                meta.setBasePotionData(new PotionData(PotionType.SLOWNESS));
                                                                flecha.setItemMeta(meta);
                                                                
								
								
								ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS,1);
								ItemStack pernas = new ItemStack(Material.DIAMOND_LEGGINGS,1);
								ItemStack peito = new ItemStack(Material.ELYTRA,1);
                                                                
                                                                peito.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                                                                peito.addUnsafeEnchantment(Enchantment.MENDING, 1);
                                                                
								ItemStack capacete = new ItemStack(Material.DIAMOND_HELMET,1);
								
                                                                capacete.addUnsafeEnchantment(Enchantment.OXYGEN, 3);
                                                                
								ItemMeta im = espada.getItemMeta();
								im.setDisplayName(ChatColor.GREEN+ "Super Espada");
								ArrayList<String> lores = new ArrayList<String>();
								lores.add(ChatColor.DARK_PURPLE+ "s� ops tem essa espada!");
								im.setLore(lores);
								im.addItemFlags();
								espada.setItemMeta(im); // <---
								
								bota.addUnsafeEnchantment(Enchantment.PROTECTION_FALL,10);
								bota.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER,2);
								
								Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
								enchantments.put(Enchantment.DURABILITY, 10);
								enchantments.put(Enchantment.PROTECTION_ENVIRONMENTAL, 10);
								enchantments.put(Enchantment.PROTECTION_EXPLOSIONS, 10);
								enchantments.put(Enchantment.PROTECTION_PROJECTILE, 10);
                                                                enchantments.put(Enchantment.PROTECTION_FIRE, 10);
                                                                enchantments.put(Enchantment.MENDING,1);
								
								
								bota.addUnsafeEnchantments(enchantments);
								pernas.addUnsafeEnchantments(enchantments);
								//peito.addUnsafeEnchantments(enchantments);
								capacete.addUnsafeEnchantments(enchantments);
								
								player.getEquipment().setItemInMainHand(espada);
								player.getEquipment().setItemInOffHand(varinha);
								player.getEquipment().setBoots(bota);
								player.getEquipment().setHelmet(capacete);
								player.getEquipment().setLeggings(pernas);
								player.getEquipment().setChestplate(peito);
								player.getInventory().addItem(arco,flecha);
								
								ItemStack maca = new ItemStack(Material.GOLDEN_APPLE,64,(short) 1);
								ItemStack beef = new ItemStack(Material.COOKED_BEEF,64);
								player.getInventory().addItem(maca);
								player.getInventory().addItem(beef);
                                                                
                                                                ItemStack fireworks = new ItemStack(Material.FIREWORK_ROCKET,64);
								player.getInventory().addItem(fireworks);
                                                                player.getInventory().addItem(fireworks);
                                                                
                                                                ItemStack crystal = new ItemStack(Material.END_CRYSTAL,64);
								player.getInventory().addItem(crystal);
								
								player.setMaxHealth(40.0);
								player.setHealth(40.0);
								player.setSaturation(20.0f);
								
								player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,20*3600,4,true,false), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING,20*3600,4,true,false), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*3600,2,true,false), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,20*3600,10,true,false), true);
								player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,20*3600,3,true,false), true);
                                                                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*3600,3,true,false), true);
                                                                
								player.setLevel(100);
								return true;
							}
							else
							{
								player.sendMessage(ChatColor.RED+"esse kit n?o existe!");
								return true;
							}
						}
						
						ConfigurationSection kits = plugin.getConfig().getConfigurationSection("kits");
						if(!kits.contains(Selec_kit))
						{
							player.sendMessage(ChatColor.RED+"esse kit n�o existe!");
							return true;		
						}
						ConfigurationSection kit = kits.getConfigurationSection(Selec_kit);
						long delay = kit.getInt("delay") * 1000;
						
						String time_metadataKey = "kit."+Selec_kit+".time";
						if(player.hasMetadata(time_metadataKey))
						{
							long last = player.getMetadata(time_metadataKey).get(0).asLong();
							if(last+delay > System.currentTimeMillis() )
							{
								player.sendMessage(ChatColor.RED+"aguarde para pedir o kit novamente!");
								return true;
							}
						}
						player.setMetadata(time_metadataKey, new FixedMetadataValue(plugin, System.currentTimeMillis() ));
						List<String> items = kit.getStringList("items");
						if(items.isEmpty())
						{
							player.sendMessage(ChatColor.RED+"kit vazio, contate o administrador do server, isso deve ser um problema");
							return true;
						}
						for(String s : items)
						{
							String[] item_quantia = s.split(" ");
							String item = item_quantia[0];
							int quantia = Integer.parseInt(item_quantia[1]);
							short data = 0;
							if(item.contains(":"))
							{
								String[] split = item.split(":");
								item = split[0];
								data = Short.parseShort(split[1]);
							}
							//ItemStack stack = new ItemStack(Material.getMaterial(item),quantia,data);
							Material m = Material.getMaterial(item);
							if( m != null) {
							ItemStack stack = new ItemStack(m, quantia);
							player.getInventory().addItem(stack);
							} else {
								System.out.println("Item "+item+" não encontrado");
							}
						}
						player.sendMessage(ChatColor.GREEN+"Aproveite seu kit \""+Selec_kit+"\"");
						
					}
					else
					{
						player.sendMessage(new String[]{
								ChatColor.RED+"Uso: /kit <nome-do-kit>",
								ChatColor.GREEN+"kits dispon�veis:"
								});
						ConfigurationSection kits = plugin.getConfig().getConfigurationSection("kits");
						Set<String> keys_set = kits.getKeys(false);
						String[] keys = new String[keys_set.size()];
						keys = keys_set.toArray(keys);
						for(int i=0;i<keys.length;i++)
						{
							player.sendMessage(ChatColor.GREEN+"    "+keys[i]);
						}
					}
				return true;
			}
		}
		else
		{
			switch(cmd)
			{
				case "login":
				{
					if(args.length == 1)
					{
						String usuario = player.getName();
						String senha = args[0];
						
						try {
							ResultSet rs = plugin.mydb.ExecQuery("SELECT nick,senha,nivel_acesso FROM login WHERE nick = '"+usuario+"' AND senha = '"+senha+"'");
							if(rs.next())
							{
								player.sendMessage(ChatColor.GREEN+"Agora voc� est� logado!");
								player.setMetadata("testando.logado", new FixedMetadataValue(plugin, true));
								plugin.mydb.CloseQuery();
								
								player.setGameMode(GameMode.SURVIVAL); // assim n vai bugar o login
								player.setFlying(false);
								player.setAllowFlight(false);
							}
							else
							{
								player.sendMessage(ChatColor.RED+"Usu�rio ou senha inv�lidos!");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							player.sendMessage(ChatColor.RED+"erro no mysql");
							e.printStackTrace();
						}
						
					}
					else
					{
						player.sendMessage(ChatColor.RED+"uso: /login <sua-senha>");
					}
				}
				return true;
				
				case "register":
				{
					if(args.length == 2)
					{
						String usuario = player.getName();
						String senha = args[0];
						String senhar = args[1];
						if(!senha.equals(senhar))
						{
							player.sendMessage(ChatColor.RED+"voc� digitou a senha errado na segunda vez!");
							return true;
						}
						if(senha.length() > 32)
						{
							player.sendMessage(ChatColor.RED+"escolha uma senha com menos de 32 caracteres");
							return true;	
						}
						
						try {
							ResultSet rs = plugin.mydb.ExecQuery("SELECT nick,senha,nivel_acesso FROM login WHERE nick = '"+usuario+"'");
							if(!rs.next())
							{								
								long id = plugin.mydb.Insert("INSERT INTO login(nick,senha,nivel_acesso) VALUES('"+usuario+"','"+senha+"',1)","login");
								//plugin.mydb.Exec("INSERT INTO skill(player_id) VALUES ("+id+")");
								player.sendMessage(ChatColor.GREEN+"registrado com Sucesso!");
								player.setMetadata("testando.logado", new FixedMetadataValue(plugin, true));
								
								player.setGameMode(GameMode.SURVIVAL); // assim n vai bugar o login
								player.setFlying(false);
								player.setAllowFlight(false);
								
								plugin.setPlayerId(player);
							}
							else
							{
								player.sendMessage(ChatColor.RED+"porqu� est� se registrando? voc� j� tem uma conta\n digite /login <sua-senha>");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							player.sendMessage(ChatColor.RED+"erro no mysql");
							e.printStackTrace();
						}
						
					}
					else
					{
						player.sendMessage(ChatColor.RED+"uso: /register <sua-senha> <repita-senha>");
					}
				}
				return true;
			}
		}
		return false;
	}
}
