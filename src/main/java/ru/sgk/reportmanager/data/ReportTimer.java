package ru.sgk.reportmanager.data;

import org.bukkit.scheduler.BukkitRunnable;
import ru.sgk.reportmanager.cmds.ReportCmd;
import ru.sgk.reportmanager.events.InventoryEvents;

import java.util.Map;
import java.util.UUID;

public class ReportTimer extends BukkitRunnable {
    @Override
    public void run() {
        long time = System.currentTimeMillis();

        for (Map.Entry<String, Reporting> entry : InventoryEvents.reporti.entrySet()) {
            if(time >= entry.getValue().time+60*30) {
                InventoryEvents.reporti.remove(entry.getKey());
            }
        }

        for(UUID uuid : ReportCmd.cooldown.keySet()) {
            int cool = ReportCmd.cooldown.get(uuid);
            if(cool > 0) {
                ReportCmd.cooldown.put(uuid, cool-1);
            } else {
                ReportCmd.cooldown.remove(uuid);
            }
        }
    }
}
