package ru.sgk.reportmanager.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.sgk.reportmanager.cmds.ReportCmd;

public class MainEvents implements Listener {

	/*@EventHandler
	public  void onPlayerJoin(PlayerJoinEvent e)
	{
		Runnable task = () ->
		{
			List<Report> playerReportList = MySQLManager.Requests.getPlayerReports(e.getPlayer().getName(), 1);
			for (Report report : playerReportList)
			{
				if (report.isResponded() && !report.isChecked())
				{
					Report.notifyPlayer(e.getPlayer());
				}
			}
		};
		new Thread(task).start();
	}*/

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		String name = e.getPlayer().getName();
		InventoryEvents.reporti.remove(name);
		ReportCmd.cooldown.remove(e.getPlayer().getUniqueId());
	}
}
