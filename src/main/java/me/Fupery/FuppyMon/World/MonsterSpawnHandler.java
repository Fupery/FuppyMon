package me.Fupery.FuppyMon.World;

import me.Fupery.FuppyMon.Entity.Monster;
import me.Fupery.FuppyMon.Entity.SpawnReason;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import me.Fupery.FuppyMon.Combat.Type;


import java.util.List;
import java.util.Random;

public class MonsterSpawnHandler {
    private final Random random;

    public MonsterSpawnHandler() {
        this.random = new Random();
    }

    public void attemptSpawn() {
        Location loc = getNextRandomLocation();
        if (loc == null || !isValid(loc)) return;
        List<Type> types = getApplicableTypesFor(loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()));
        Random rand = new Random(types.size());
        Monster monster = null;
        while (monster == null) {
            monster = getNextRandomMonsterOfType(types.get(rand.nextInt()));
        }
        monster.spawn(loc, SpawnReason.NATURAL);
    }
    
    Location getNextRandomLocation() {
        return null;// TODO: 25/07/2016  
    }

    boolean isValid(Location location) {
        return false; // TODO: 25/07/2016  
    }

    List<Type> getApplicableTypesFor(Biome biome) {
        return null;// TODO: 25/07/2016
    }

    Monster getNextRandomMonsterOfType(Type type) {
        return null; // TODO: 25/07/2016
    }
}
