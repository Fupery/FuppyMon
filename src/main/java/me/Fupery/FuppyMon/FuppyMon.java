package me.Fupery.FuppyMon;

import me.Fupery.FuppyMon.CustomMobs.Cubone;
import me.Fupery.FuppyMon.CustomMobs.Owl;
import me.Fupery.FuppyMon.CustomMobs.RedstoneGolem;
import me.Fupery.FuppyMon.CustomMobs.TestMob;
import me.Fupery.FuppyMon.Entity.CustomEntities.CustomEntityManager;
import me.Fupery.FuppyMon.Event.DynamicEntityInteractListener;
import me.Fupery.FuppyMon.Event.PlayerStatusListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FuppyMon extends JavaPlugin {

    private EntityManager manager;

    @Override
    public void onEnable() {
        CustomEntityManager.registerCustomEntities();
        manager = new EntityManager(this);
        manager.registerPet("Owl", new Owl().getFactory());
        manager.registerPet("Golem", new RedstoneGolem().getFactory());
        manager.registerPet("Test", new TestMob().getFactory());
        manager.registerPet("Cubone", new Cubone().getFactory());

        Bukkit.getPluginManager().registerEvents(new PlayerStatusListener(manager), this);
        Bukkit.getPluginManager().registerEvents(new DynamicEntityInteractListener(), this);

        getCommand("pet").setExecutor((sender, command, s, args) -> {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players may use this command!");
                return true;
            }
            Player player = (Player) sender;
            if (args.length > 0) {
                manager.spawn(args[0], player, player.getLocation());
            }
            return true;
        });
    }

    @Override
    public void onDisable() {
        manager.stop();
    }
}
