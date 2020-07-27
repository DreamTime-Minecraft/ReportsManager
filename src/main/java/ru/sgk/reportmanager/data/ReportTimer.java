package ru.sgk.reportmanager.data;

import org.bukkit.scheduler.BukkitRunnable;
import ru.sgk.reportmanager.cmds.ReportCmd;
import ru.sgk.reportmanager.events.InventoryEvents;

public class ReportTimer extends BukkitRunnable {
    @Override
    public void run() {
        long time = System.currentTimeMillis();

        InventoryEvents.reporti.entrySet().removeIf((entry) -> time > (entry.getValue().time+(60*30)));
        ReportCmd.cooldown.entrySet().removeIf((entry) -> entry.getValue() <= time);
    }
}
