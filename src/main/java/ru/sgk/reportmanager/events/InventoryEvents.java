package ru.sgk.reportmanager.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.sgk.reportmanager.data.MySQLManager;
import ru.sgk.reportmanager.data.Report;
import ru.sgk.reportmanager.data.Reporting;
import ru.sgk.reportmanager.invs.RepInvs;
import ru.sgk.reportmanager.invs.ReportInvTypes;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryEvents implements Listener {

    public static ConcurrentHashMap<String, Reporting> reporti = new ConcurrentHashMap<>(50,1f);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        int slot = e.getSlot();

        if(p.getOpenInventory().getTitle().startsWith("§Reports §8>>")) {
            e.setCancelled(true);
            if (p.getOpenInventory().getTitle().equalsIgnoreCase("§cReports §8>> §6Отправка жалобы §e(Стадия №1)")) {
                if(slot == 11) {
                    p.openInventory(RepInvs.createInventory(ReportInvTypes.REPORT2, null));
                } else if(slot == 15) {
                    //ToDo помеха в команде
                } else if(slot == 12) {
                    //ToDo гриферство на выживании
                } else if(slot == 14) {
                    p.closeInventory();
                    p.sendMessage("§fИспользуйте команду §c/report <ник> custom <причина>");
                }
            } else if (p.getOpenInventory().getTitle().equalsIgnoreCase("§cReports §8>> §6Отправка жалобы §e(Стадия №2)")) {
                if(slot == 10) {
                    //ToDo killAura
                } else if(slot == 11) {
                    //ToDo AntiKnockBack
                } else if(slot == 12) {
                    //ToDo fly
                } else if(slot == 14) {
                    //ToDo build
                } else if(slot == 15) {
                    //ToDo bow
                } else if(slot == 16) {
                    //ToDo other
                }
            } else if (p.getOpenInventory().getTitle().equalsIgnoreCase("§cReports §8>> §6Все жалобы")) {
                if(slot < 53) {
                    p.openInventory(RepInvs.createInventory(ReportInvTypes.SPEC_REPORT, MySQLManager.Requests.getReports().get(slot)));
                }
            } else if (p.getOpenInventory().getTitle().startsWith("§cReports §8>> §6Выдача наказания для")) {
                if(slot == 19) {
                    //ToDo ban cheat
                } else if(slot == 28) {
                    //ToDo ban grief
                } else if(slot == 37) {
                    //ToDo ban team
                } else if(slot == 21) {
                    //ToDo mute swear
                } else if(slot == 30) {
                    //ToDo mute propoganda
                } else if(slot == 39) {
                    //ToDo mute spam
                } else if(slot == 23) {
                    //ToDo kick pomeha
                }
            } else if (p.getOpenInventory().getTitle().startsWith("§cReports §8>> §6Жалоба на")) {
                if(slot == 49) {
                    //ToDo punish
                }
            }
        }
    }
}
