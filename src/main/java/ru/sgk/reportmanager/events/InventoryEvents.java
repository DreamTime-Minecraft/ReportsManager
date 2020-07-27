package ru.sgk.reportmanager.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.sgk.reportmanager.ReportManager;
import ru.sgk.reportmanager.data.MySQLManager;
import ru.sgk.reportmanager.data.Report;
import ru.sgk.reportmanager.data.Reporting;
import ru.sgk.reportmanager.invs.RepInvs;
import ru.sgk.reportmanager.invs.ReportInvTypes;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryEvents implements Listener {

    public static ConcurrentHashMap<String, Reporting> reporti = new ConcurrentHashMap<>(50,1f);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        int slot = e.getSlot();

        if(p.getOpenInventory().getTitle().startsWith("§cReports §8>>")) {
            e.setCancelled(true);
            if (p.getOpenInventory().getTitle().equalsIgnoreCase("§cReports §8>> §6Отправка жалобы §e(Стадия №1)")) {
                if(slot == 11) {
                    p.openInventory(RepInvs.createInventory(ReportInvTypes.REPORT2, null));
                } else if(slot == 15) {
                    sendReport(p, "Помеха в команде");
                } else if(slot == 12) {
                    sendReport(p, "Гриферство на выживании");
                } else if(slot == 14) {
                    p.closeInventory();
                    p.sendMessage("§fИспользуйте команду §c/report <ник> <причина>");
                }
            } else if (p.getOpenInventory().getTitle().equalsIgnoreCase("§cReports §8>> §6Отправка жалобы §e(Стадия №2)")) {
                if(slot == 10) {
                    sendReport(p, "Чит - КиллАура");
                } else if(slot == 11) {
                    sendReport(p, "Чит - АнтиКнокБек");
                } else if(slot == 12) {
                    sendReport(p, "Чит - Флай");
                } else if(slot == 14) {
                    sendReport(p, "Чит - Билд");
                } else if(slot == 15) {
                    sendReport(p, "Чит - Лук");
                } else if(slot == 16) {
                    sendReport(p, "Чит - Другое");
                }
            } else if (p.getOpenInventory().getTitle().startsWith("§cReports §8>> §6Все жалобы №")) {
                int page = Integer.parseInt(p.getOpenInventory().getTitle().substring(29));

                if(slot == 1) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Жалоб нет")) {
                        return;
                    }
                } else if(slot == 53) {
                    Inventory inv = Bukkit.createInventory(null, 54, "§cReports §8>> §6Все жалобы №"+ (page+1));
                    RepInvs.createItemsForREPORTS(inv, page+1);
                    p.openInventory(inv);
                    return;
                } else if(slot == 52) {
                    if(page > 1) {
                        Inventory inv = Bukkit.createInventory(null, 54, "§cReports §8>> §6Все жалобы №" + (page - 1));
                        RepInvs.createItemsForREPORTS(inv, page - 1);
                        p.openInventory(inv);
                        return;
                    } else {
                        return;
                    }
                }

                List<Report> reportList = MySQLManager.Requests.getReports(page, 52);

                if(reportList == null || reportList.size() < slot) {
                    return;
                }

                p.openInventory(RepInvs.createInventory(ReportInvTypes.SPEC_REPORT, reportList.get(slot)));

                /*if(slot < 52) {
                    try {
                        Report report = MySQLManager.Requests.getReports().get(slot);
                        if (report != null) {
                            p.openInventory(RepInvs.createInventory(ReportInvTypes.SPEC_REPORT, MySQLManager.Requests.getReports().get(slot)));
                        }
                    } catch (NullPointerException ex) { }
                }*/
            } else if (p.getOpenInventory().getTitle().startsWith("§cReports §8>> §6Выдача наказания для §e№")) {
                long id = Long.parseLong(p.getOpenInventory().getTitle().substring(41));
                String nickname = MySQLManager.Requests.getReport(id).getReportedPlayerName();
                if(nickname == null) {
                    p.sendMessage("§cПроизошла ошибка при получении ника игрока! Напишите об этом Администрации сервера!");
                }
                if(slot == 19) {
                    p.performCommand("ban "+nickname+" Использование постороннего ПО");
                } else if(slot == 28) {
                    p.performCommand("ban "+nickname+" 90d Гриферство");
                } else if(slot == 37) {
                    p.performCommand("ban "+nickname+" 3d Помехи в команде");
                } else if(slot == 21) {
                    p.performCommand("mute "+nickname+" 30m Маты/Оскорбления");
                } else if(slot == 30) {
                    p.performCommand("mute "+nickname+" 2h Пропоганда");
                } else if(slot == 39) {
                    p.performCommand("mute "+nickname+" 15m Спам/Флуд");
                } else if(slot == 23) {
                    p.performCommand("kick "+nickname+" Помеха в игре");
                }
                p.performCommand("reports show "+id);
            } else if (p.getOpenInventory().getTitle().startsWith("§cReports §8>> §6Жалоба №")) {
                long id = Long.parseLong(p.getOpenInventory().getTitle().substring(25));
                if(slot == 49) {
                    p.openInventory(RepInvs.createInventory(ReportInvTypes.PUN_REPORT, MySQLManager.Requests.getReport(id)));
                } else if(slot == 45) {
                    MySQLManager.Requests.setResponded(id, true);
                    p.sendMessage("§aЖалоба помечена решённой!");
                    p.performCommand("reports");
                }
            }
        }
    }

    private void sendReport(Player p, String reason) {
        String name = p.getName();
        if(reporti.containsKey(name)) {
            Reporting rep = reporti.get(name);
            long id = ReportManager.sendReport(name, rep.name, reason);
            p.closeInventory();
            p.sendMessage("§aВаша жалоба на игрока §2" + rep.name + " §aбудет рассмотрена модераторами в ближайшее время! §8[id жалобы: " + id + "]");
            reporti.remove(p.getName());
        } else {
            p.closeInventory();
            p.sendMessage("§cВаше время на жалобу истекло! Пожалуйста, выбирайте причину быстрее.");
        }
    }
}
