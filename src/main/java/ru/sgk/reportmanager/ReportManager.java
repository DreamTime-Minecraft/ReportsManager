package ru.sgk.reportmanager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sgk.reportmanager.bungee.Messenger;
import ru.sgk.reportmanager.cmds.ReportCmd;
import ru.sgk.reportmanager.cmds.ReportManagerCmd;
import ru.sgk.reportmanager.cmds.ReportsCmd;
import ru.sgk.reportmanager.data.Configuration;
import ru.sgk.reportmanager.data.MySQLManager;
import ru.sgk.reportmanager.data.Report;
import ru.sgk.reportmanager.data.ReportTimer;
import ru.sgk.reportmanager.events.InventoryEvents;
import ru.sgk.reportmanager.events.MainEvents;

import java.util.logging.Logger;

public class ReportManager extends JavaPlugin 
{
	private static Logger logger;
	private static String prefix = "§f[§cReport§fManager]";
	private static ReportManager instance;
	private static FileConfiguration config;

	@Override
    public void onEnable() 
    {
		instance = this;
    	
		logger = Bukkit.getLogger();
		
    	config = Configuration.load("config.yml");

    	getServer().getMessenger().registerIncomingPluginChannel(instance, "BungeeCord", new Messenger());
    	getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
    	
    	getCommand("report").setExecutor(new ReportCmd(config));
    	getCommand("reports").setExecutor(new ReportsCmd(config));
    	getCommand("reportmanager").setExecutor(new ReportManagerCmd(config));
    	dbConnect();
    	getServer().getPluginManager().registerEvents(new MainEvents(), instance);
    	getServer().getPluginManager().registerEvents(new InventoryEvents(), instance);

		ReportTimer timer = new ReportTimer();
		timer.runTaskTimerAsynchronously(instance, 20, 20*60);

    	log("plugin was enabled");
    }
    
	public static ReportManager getInstance() 
	{
		return instance;
	}
	/**
	 * sends the message to console with plugin prefix
	 * @param message
	 */
	public static void log(String message)
    {
    	logger.info(prefix + " §r"+ message);
    }

    @Override
    public void onDisable() 
    {
    	MySQLManager.closeConnection();
		log("plugin was disabled");
	}

	/**
	 * @return the config
	 */
    @Override
	public FileConfiguration getConfig() {
		return config;
	}
    public static void dbConnect()
    {
    	String host = config.getString("database.host");
    	String database = config.getString("database.database");
    	String user = config.getString("database.user");
    	String password = config.getString("database.password", "");
    	MySQLManager.connect(host, database, user, password);
    	MySQLManager.Requests.createTable();
    }

    public static long sendReport(String sender, String reported, String reason) {
		long id = MySQLManager.Requests.sendReport(sender, reported, reason);
		Report.notifyAdmin(id);
		for(Player player : Bukkit.getOnlinePlayers()) {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Forward"); // So BungeeCord knows to forward it
			out.writeUTF("ALL");
			out.writeUTF("report.send");
			out.writeLong(id);

			player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
			break;
		}
		return id;
	}
}
