package ru.sgk.reportmanager.invs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.sgk.reportmanager.data.MySQLManager;
import ru.sgk.reportmanager.data.Report;

import java.util.ArrayList;
import java.util.List;

public class RepInvs {
    public static Inventory createInventory(ReportInvTypes invtype, Report report) {
        switch(invtype) {
            case REPORT1:
                Inventory invrep1 = Bukkit.createInventory(null, 27, "§cReports §8>> §6Отправка жалобы §e(Стадия №1)");
                createItemsForREPORT1(invrep1);
                return invrep1;
            case REPORT2:
                Inventory invrep2 = Bukkit.createInventory(null, 27, "§cReports §8>> §6Отправка жалобы §e(Стадия №2)");
                createItemsForREPORT2(invrep2);
                return invrep2;
            case REPORTS:
                Inventory invreps = Bukkit.createInventory(null, 54, "§cReports §8>> §6Все жалобы");
                createItemsForREPORTS(invreps);
                return invreps;
            case PUN_REPORT:
                break;
            case SPEC_REPORT:
                break;
            default:
                return Bukkit.createInventory(null, 9, "§cНеизвестное меню!");
        }
        return null;
    }

    private static void createItemsForREPORT1(Inventory inv) {
        ItemStack whatIsIt = new ItemStack(Material.COMPASS);
        ItemMeta metaWhatIsIt = whatIsIt.getItemMeta();
        metaWhatIsIt.setDisplayName("§fЧто это такое?");
        List<String> loreWhatIsIt = new ArrayList<>();
        loreWhatIsIt.add("§7");
        loreWhatIsIt.add("§8> §cReports §7- система жалоб игроков");
        loreWhatIsIt.add("§8> §7на других игроков по разным причинам.");
        loreWhatIsIt.add("§8> §7На жалобы реагирует персонал сервера,");
        loreWhatIsIt.add("§8> §7который решает, наказывать игрока или нет.");
        loreWhatIsIt.add("§0");
        loreWhatIsIt.add("§eСнизу предоставлены категории жалоб. Выберите нужную.");
        metaWhatIsIt.setLore(loreWhatIsIt);
        metaWhatIsIt.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        whatIsIt.setItemMeta(metaWhatIsIt);
        whatIsIt.addUnsafeEnchantment(Enchantment.LUCK, 666);
        inv.setItem(4, whatIsIt);

        ItemStack reportCHEAT = new ItemStack(Material.BEDROCK);
        ItemMeta metaReportCHEAT = reportCHEAT.getItemMeta();
        metaReportCHEAT.setDisplayName("§cЧиты");
        List<String> loreReportCHEAT = new ArrayList<>();
        loreReportCHEAT.add("§7");
        loreReportCHEAT.add("§8> §7Думаете, что игрок §cчитерит§7?");
        loreReportCHEAT.add("§8> §7Смело нажимайте на этот предмет!");
        loreReportCHEAT.add("§8> §7И выбирайте чит, который думаете, что он использует.");
        loreReportCHEAT.add("§0");
        metaReportCHEAT.setLore(loreReportCHEAT);
        reportCHEAT.setItemMeta(metaReportCHEAT);
        inv.setItem(11, reportCHEAT);

        ItemStack reportTEAM = new ItemStack(Material.STONE_SWORD);
        ItemMeta metaReportTEAM = reportTEAM.getItemMeta();
        metaReportTEAM.setDisplayName("§cПомеха в команде");
        List<String> loreReportTEAM = new ArrayList<>();
        loreReportTEAM.add("§7");
        loreReportTEAM.add("§8> §7Какой-то союзник мешает Вам?");
        loreReportTEAM.add("§8> §7Жмите сюда, чтобы пожаловаться на него!");
        loreReportTEAM.add("§0");
        metaReportTEAM.setLore(loreReportTEAM);
        reportTEAM.setItemMeta(metaReportTEAM);
        inv.setItem(15, reportTEAM);

        ItemStack reportSURVIVAL = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta metaReportSURVIVAL = reportSURVIVAL.getItemMeta();
        metaReportSURVIVAL.setDisplayName("§cГриферство на выживании");
        List<String> loreReportSURVIVAL = new ArrayList<>();
        loreReportSURVIVAL.add("§7");
        loreReportSURVIVAL.add("§8> §7Заметили, что кто-то §cгриферит");
        loreReportSURVIVAL.add("§8> §7или строит §cловушку§7?");
        loreReportSURVIVAL.add("§8> §7Срочно жмякайте сюда, чтобы отправить жалобу!");
        loreReportSURVIVAL.add("§0");
        metaReportSURVIVAL.setLore(loreReportSURVIVAL);
        reportSURVIVAL.setItemMeta(metaReportSURVIVAL);
        inv.setItem(12, reportSURVIVAL);

        ItemStack reportOTHER = new ItemStack(Material.BOOK);
        ItemMeta metaReportOTHER = reportOTHER.getItemMeta();
        metaReportOTHER.setDisplayName("§cДругое");
        List<String> loreReportOTHER = new ArrayList<>();
        loreReportOTHER.add("§7");
        loreReportOTHER.add("§8> §7Есть другая причина для жалобы?");
        loreReportOTHER.add("§8> §7Используйте полную команду:");
        loreReportOTHER.add("§8> §6/report <ник> <причина>");
        loreReportOTHER.add("§0");
        metaReportOTHER.setLore(loreReportOTHER);
        reportOTHER.setItemMeta(metaReportOTHER);
        inv.setItem(14, reportOTHER);
    }

    private static void createItemsForREPORT2(Inventory inv) {
        ItemStack whatIsIt = new ItemStack(Material.COMPASS);
        ItemMeta metaWhatIsIt = whatIsIt.getItemMeta();
        metaWhatIsIt.setDisplayName("§fЧто это такое?");
        List<String> loreWhatIsIt = new ArrayList<>();
        loreWhatIsIt.add("§7");
        loreWhatIsIt.add("§8> §7Пожалуйста, выберете, какой именно");
        loreWhatIsIt.add("§8> §7чит использует игрок.");
        loreWhatIsIt.add("§8> §7Нам так будет проще наблюдать");
        loreWhatIsIt.add("§8> §7за игроком.");
        loreWhatIsIt.add("§0");
        loreWhatIsIt.add("§eСнизу предоставлены категории читов. Выберите нужную.");
        metaWhatIsIt.setLore(loreWhatIsIt);
        metaWhatIsIt.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        whatIsIt.setItemMeta(metaWhatIsIt);
        whatIsIt.addUnsafeEnchantment(Enchantment.LUCK, 666);
        inv.setItem(4, whatIsIt);

        ItemStack killAura = new ItemStack(Material.IRON_SWORD);
        ItemMeta metaKillAura = killAura.getItemMeta();
        metaKillAura.setDisplayName("§cKillAura и подобные (AimBot, MobAura, ...)");
        killAura.setItemMeta(metaKillAura);
        inv.setItem(10, killAura);

        ItemStack antiKnockBack = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta metaAntiKnockBack = antiKnockBack.getItemMeta();
        metaAntiKnockBack.setDisplayName("§cAnti-KnockBack и подобные");
        antiKnockBack.setItemMeta(metaAntiKnockBack);
        inv.setItem(11, antiKnockBack);

        ItemStack fly = new ItemStack(Material.FEATHER);
        ItemMeta metaFly = fly.getItemMeta();
        metaFly.setDisplayName("§cФлай и подобные (Speed, LongJump, ...)");
        fly.setItemMeta(metaFly);
        inv.setItem(12, fly);

        Material wool = Material.valueOf("STONE");
        try {
            wool = Material.valueOf("WOOL");
        }catch (IllegalArgumentException e) {
            wool = Material.valueOf("WHITE_WOOL");
        }

        ItemStack build = new ItemStack(wool);
        ItemMeta metaBuild = build.getItemMeta();
        metaBuild.setDisplayName("§cAuto-Builder и подобные (Nuker, Scaffold, ...)");
        build.setItemMeta(metaBuild);
        inv.setItem(14, build);

        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta metaBow = bow.getItemMeta();
        metaBow.setDisplayName("§cAuto-Bow и подобные (Aim-Bot, FastBow, ...)");
        bow.setItemMeta(metaBow);
        inv.setItem(15, bow);

        ItemStack other = new ItemStack(Material.BOOK);
        ItemMeta metaOther = other.getItemMeta();
        metaOther.setDisplayName("§cДругое (Если ничего из списка не подходит)");
        other.setItemMeta(metaOther);
        inv.setItem(16, other);
    }

    private static void createItemsForREPORTS(Inventory inv) {
        List<Report> reportList = MySQLManager.Requests.getReports();

        if(reportList == null) return;

        for(int i = 0; i < 53; i++) {
            if(reportList.size() >= i) {
                Report rep = reportList.get(i);
                ItemStack report = new ItemStack(Material.PAPER);
                ItemMeta meta = report.getItemMeta();
                meta.setDisplayName("§c"+rep.getReportedPlayerName());
                List<String> lore = new ArrayList<>();
                lore.add("§0");
                lore.add("§8> §7Жалуется: §f"+rep.getReporterPlayerName());
                lore.add("§8> §7Причина: §f"+rep.getText());
                lore.add("§1");
                lore.add("§8> §7Кто ответил: §f"+ (rep.getResponder() == null ? "§cНикто" : rep.getResponder()));
                lore.add("§8> §7Ответ: §f"+ (rep.getRespond() == null ? "§cПусто" : rep.getRespond()));
                meta.setLore(lore);
                report.setItemMeta(meta);
                inv.setItem(i, report);
            } else {
                break;
            }
        }
    }

    private static void createItemsForSPEC(Inventory inv, Report rep) {
        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta metaInfo = info.getItemMeta();
        metaInfo.setDisplayName("§c"+rep.getReportedPlayerName());
        List<String> lore = new ArrayList<>();
        lore.add("§0");
        lore.add("§8> §7Жалуется: §f"+rep.getReporterPlayerName());
        lore.add("§8> §7Причина: §f"+rep.getText());
        lore.add("§1");
        lore.add("§8> §7Кто ответил: §f"+ (rep.getResponder() == null ? "§cНикто" : rep.getResponder()));
        lore.add("§8> §7Ответ: §f"+ (rep.getRespond() == null ? "§cПусто" : rep.getRespond()));
        metaInfo.setLore(lore);
        info.setItemMeta(metaInfo);
        inv.setItem(0, info);

        ItemStack whatIsIt = new ItemStack(Material.COMPASS);
        ItemMeta metaWhat = whatIsIt.getItemMeta();
        metaWhat.setDisplayName("§fЧто это такое?");
        List<String> loreWhat = new ArrayList<>();
        loreWhat.add("§0");
        loreWhat.add("§8> §7Вы открыли одну из жалоб игроков.");
        loreWhat.add("§8> §7Здесь Вы можете узнать о жалобе,");
        loreWhat.add("§8> §7выдать наказание или оставить комментарий к жалобе.");
        metaWhat.setLore(loreWhat);
        whatIsIt.setItemMeta(metaWhat);
        inv.setItem(8, whatIsIt);
    }
}
