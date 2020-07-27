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
                Inventory invpun = Bukkit.createInventory(null, 54, "§cReports §8>> §6Выдача наказания для §e№"+report.getId());
                createItemsForPunish(invpun, report);
                return invpun;
            case SPEC_REPORT:
                Inventory invspec = Bukkit.createInventory(null, 54, "§cReports §8>> §6Жалоба №"+report.getId());
                createItemsForSPEC(invspec, report);
                return invspec;
            default:
                return Bukkit.createInventory(null, 9, "§cНеизвестное меню!");
        }
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

        if(reportList == null) {
            return;
        }

        for(int i = 0; i < 53; i++) {
            if(reportList.size() > i) {
                //Report rep = reportList.get(i);
                Report rep = reportList.get(i);
                ItemStack item = new ItemStack(Material.PAPER);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName("§8[№"+rep.getId()+"] §c"+rep.getReportedPlayerName());
                List<String> lore = new ArrayList<>();
                lore.add("§0");
                lore.add("§8> §7Жалуется: §f"+rep.getReporterPlayerName());
                lore.add("§8> §7Причина: §f"+rep.getText());
                lore.add("§1");
                lore.add("§8> §7Кто ответил: §f"+ rep.getResponder());
                lore.add("§8> §7Ответ: §f"+ rep.getRespond());
                meta.setLore(lore);
                item.setItemMeta(meta);
                inv.setItem(i, item);

                /*for(Report report : reportList) {
                    if(!report.isResponded()) {
                        ItemStack item = new ItemStack(Material.PAPER);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName("§c"+report.getReportedPlayerName());
                        List<String> lore = new ArrayList<>();
                        lore.add("§0");
                        lore.add("§8> §7Жалуется: §f"+report.getReporterPlayerName());
                        lore.add("§8> §7Причина: §f"+report.getText());
                        lore.add("§1");
                        lore.add("§8> §7Кто ответил: §f"+ (report.getResponder() == null ? "§cНикто" : report.getResponder()));
                        lore.add("§8> §7Ответ: §f"+ (report.getRespond() == null ? "§cПусто" : report.getRespond()));
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        inv.setItem(i, item);
                        System.out.println("Set item "+i+" for report "+report.getId());
                    }
                }*/
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
        metaInfo.setLore(lore);
        info.setItemMeta(metaInfo);
        inv.setItem(0, info);

        ItemStack answer = new ItemStack(Material.BOOK);
        ItemMeta metaAnswer = answer.getItemMeta();
        metaAnswer.setDisplayName("§fОтвет на жалобу");
        List<String> lorea = new ArrayList<>();
        lorea.add("§0");
        lorea.add("§8> §7Кто ответил: §f"+ rep.getResponder());
        lorea.add("§8> §7Ответ: §f"+ rep.getRespond());
        lorea.add("§1");
        lorea.add("§fКак ответить:");
        lorea.add("§7/reports answer "+rep.getId()+" <текст>");
        lorea.add("§2");
        metaAnswer.setLore(lorea);
        answer.setItemMeta(metaAnswer);
        inv.setItem(4, answer);

        ItemStack whatIsIt = new ItemStack(Material.COMPASS);
        ItemMeta metaWhat = whatIsIt.getItemMeta();
        metaWhat.setDisplayName("§fЧто это такое?");
        List<String> loreWhat = new ArrayList<>();
        loreWhat.add("§0");
        loreWhat.add("§8> §7Вы открыли одну из жалоб игроков.");
        loreWhat.add("§8> §7Здесь Вы можете узнать о жалобе,");
        loreWhat.add("§8> §7выдать наказание или оставить комментарий к жалобе.");
        loreWhat.add("§1");
        metaWhat.setLore(loreWhat);
        whatIsIt.setItemMeta(metaWhat);
        inv.setItem(8, whatIsIt);

        ItemStack punish = new ItemStack(Material.BARRIER);
        ItemMeta metaPunish = punish.getItemMeta();
        metaPunish.setDisplayName("§cВыдать наказание");
        punish.setItemMeta(metaPunish);
        inv.setItem(49, punish);

        ItemStack close = new ItemStack(Material.TNT);
        ItemMeta metaClose = close.getItemMeta();
        metaClose.setDisplayName("§cЗакрыть жалобу");
        close.setItemMeta(metaClose);
        inv.setItem(45, close);
    }

    private static void createItemsForPunish(Inventory inv, Report report) {
        //Заголовки
        ItemStack ban = new ItemStack(Material.BEDROCK);
        ItemMeta banMeta = ban.getItemMeta();
        banMeta.setDisplayName("§cБаны");
        ban.setItemMeta(banMeta);
        inv.setItem(10, ban);

        ItemStack mute = new ItemStack(Material.OBSIDIAN);
        ItemMeta muteMeta = mute.getItemMeta();
        muteMeta.setDisplayName("§cМуты");
        mute.setItemMeta(muteMeta);
        inv.setItem(12, mute);

        ItemStack kick = new ItemStack(Material.NETHER_BRICK);
        ItemMeta kickMeta = kick.getItemMeta();
        kickMeta.setDisplayName("§cКики");
        kick.setItemMeta(kickMeta);
        inv.setItem(14, kick);

        ItemStack warn = new ItemStack(Material.NETHERRACK);
        ItemMeta warnMeta = warn.getItemMeta();
        warnMeta.setDisplayName("§cВарны");
        warn.setItemMeta(warnMeta);
        inv.setItem(16, warn);

        //Клик чтобы бан
        ItemStack banCheat = new ItemStack(Material.BARRIER);
        ItemMeta metaCheat = banCheat.getItemMeta();
        metaCheat.setDisplayName("§cЧиты");
        List<String> loreCheat = new ArrayList<>();
        loreCheat.add("§7/ban "+report.getReportedPlayerName()+" Использование постороннего ПО");
        metaCheat.setLore(loreCheat);
        banCheat.setItemMeta(metaCheat);
        inv.setItem(19, banCheat);

        ItemStack banGrief = new ItemStack(Material.BARRIER);
        ItemMeta metaGrief = banGrief.getItemMeta();
        metaGrief.setDisplayName("§cГриферство (90 дней)");
        List<String> loreGrief = new ArrayList<>();
        loreGrief.add("§7/ban "+report.getReportedPlayerName()+" 90d Гриферство");
        metaGrief.setLore(loreGrief);
        banGrief.setItemMeta(metaGrief);
        inv.setItem(28, banGrief);

        ItemStack banTeam = new ItemStack(Material.BARRIER);
        ItemMeta metaTeam = banTeam.getItemMeta();
        metaTeam.setDisplayName("§cПомехи в команде (3 дня)");
        List<String> loreTeam = new ArrayList<>();
        loreTeam.add("§7/ban "+report.getReportedPlayerName()+" 3d Помехи в команде");
        metaTeam.setLore(loreTeam);
        banTeam.setItemMeta(metaTeam);
        inv.setItem(37, banTeam);

        //Клик чтобы мут
        ItemStack muteSwear = new ItemStack(Material.STRUCTURE_VOID);
        ItemMeta metaSwear = muteSwear.getItemMeta();
        metaSwear.setDisplayName("§cМаты/Оскорбления (30 минут)");
        List<String> loreSwear = new ArrayList<>();
        loreSwear.add("§7/mute "+report.getReportedPlayerName()+" 30m Маты/Оскорбления");
        metaSwear.setLore(loreSwear);
        muteSwear.setItemMeta(metaSwear);
        inv.setItem(21, muteSwear);

        ItemStack mutePropoganda = new ItemStack(Material.STRUCTURE_VOID);
        ItemMeta metaPropoganda = mutePropoganda.getItemMeta();
        metaPropoganda.setDisplayName("§cПропоганда (3 часа)");
        List<String> lorePropoganda = new ArrayList<>();
        lorePropoganda.add("§7/mute "+report.getReportedPlayerName()+" 2h Пропоганда");
        metaPropoganda.setLore(lorePropoganda);
        mutePropoganda.setItemMeta(metaPropoganda);
        inv.setItem(30, mutePropoganda);

        ItemStack muteSpam = new ItemStack(Material.STRUCTURE_VOID);
        ItemMeta metaSpam = muteSpam.getItemMeta();
        metaSpam.setDisplayName("§cСпам/Флуд (15 минут)");
        List<String> loreSpam = new ArrayList<>();
        loreSpam.add("§7/mute "+report.getReportedPlayerName()+" 15m Спам/Флуд");
        metaSpam.setLore(loreSpam);
        muteSpam.setItemMeta(metaSpam);
        inv.setItem(39, muteSpam);

        //Клик чтобы кик
        ItemStack kickPomeha = new ItemStack(Material.BONE);
        ItemMeta metaPomeha = kickPomeha.getItemMeta();
        metaPomeha.setDisplayName("§cПомеха");
        List<String> lorePomeha = new ArrayList<>();
        lorePomeha.add("§7/kick "+report.getReportedPlayerName()+" Помеха в игре");
        metaPomeha.setLore(lorePomeha);
        kickPomeha.setItemMeta(metaPomeha);
        inv.setItem(23, kickPomeha);
    }
}
