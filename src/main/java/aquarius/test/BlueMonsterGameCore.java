package aquarius.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;


public final class BlueMonsterGameCore extends JavaPlugin implements Listener {

    String aooniName;

    ScoreboardManager scoreboardManager;
    Scoreboard scoreboard;

    Random random = new Random();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("aooni")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {

            Player player = (Player) sender;
            player.sendMessage("You cannot this");
            return true;
        }
        if (command.getLabel().equalsIgnoreCase("aooni")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start")) {

                    new BukkitRunnable() {

                        int start = 3;


                        @Override
                        public void run() {

                            for (Player player : Bukkit.getOnlinePlayers()) {

                                if (start >= -1) {
                                    if (start == 3) {
                                        player.sendTitle(ChatColor.BLUE + "ーーー3ーーー", "", 1, 20, 1);
                                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 100, 1);
                                    }
                                    if (start == 2) {
                                        player.sendTitle(ChatColor.BLUE + "ーーー2ーーー", "", 1, 20, 1);
                                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 100, 1);
                                    }
                                    if (start == 1) {
                                        player.sendTitle(ChatColor.BLUE + "ーーー1ーーー", "", 1, 20, 1);
                                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 100, 1);
                                    }
                                    if (start == 0) {
                                        player.sendTitle(ChatColor.BLUE + "青鬼ごっこ", "スタート", 1, 100, 20);
                                        player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 100, 1);

                                        int randomValue = random.nextInt(Bukkit.getOnlinePlayers().size());
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            int index = ((List<Player>) Bukkit.getOnlinePlayers()).indexOf(p);
                                            if (randomValue == index) {
                                                aooniName = p.getDisplayName();

                                                scoreboardManager = Bukkit.getScoreboardManager();
                                                scoreboard = Objects.requireNonNull(scoreboardManager).getNewScoreboard();
                                                Objective objective = scoreboard.registerNewObjective("BlueMonsterGameCore", "Dummy");
                                                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                                                objective.setDisplayName(ChatColor.BLUE + "~~~~~~~~~~青鬼ごっこ~~~~~~~~~~");

                                                Team timer = scoreboard.registerNewTeam("oni");
                                                timer.addEntry(ChatColor.BLUE + "" + ChatColor.BOLD + "青鬼 : ");
                                                timer.setSuffix(aooniName + "");
                                                objective.getScore(ChatColor.BLUE + "" + ChatColor.BOLD + "青鬼 : ").setScore(0);

                                                p.sendMessage(ChatColor.BLUE + Objects.requireNonNull(p).getName() + "が青鬼になりました");

                                                for (Player player1 : Bukkit.getOnlinePlayers()) {
                                                    player1.setScoreboard(scoreboard);

                                                    start = 3;
                                                    cancel();
                                                }
                                            }
                                        }
                                    }


                                }
                            }
                            start--;

                        }
                    }.runTaskTimer(this, 0, 20);
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}