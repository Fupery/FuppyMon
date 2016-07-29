package me.Fupery.FuppyMon.Entity.CustomEntities;

import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityTypes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Registers custom entities with nms. Custom entity classes must be added as a constant to be spawned.
 */
public enum CustomEntityManager {
    FlightlessBird("Chicken", 93, EntityFlightlessBird.class),
    Biped("Chicken", 93, EntityBiped.class);

    String name;
    int id;
    Class<? extends Entity> customClass;

    /**
     * @param name        The name of the Entity this custom entity extends
     * @param id          The id of the Entity this custom entity extends
     * @param customClass The custom entity class
     */
    CustomEntityManager(String name, int id, Class<? extends Entity> customClass) {
        this.name = name;
        this.id = id;
        this.customClass = customClass;
    }

    public static void registerCustomEntities() {
        for (CustomEntityManager e : values()) {
            e.register();
        }
    }

    private void register() {
        try {
            List<Map<?, ?>> entityTypes = new ArrayList<>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    entityTypes.add((Map<?, ?>) f.get(null));
                }
            }

            if (entityTypes.get(2).containsKey(id)) {
                entityTypes.get(0).remove(name);
                entityTypes.get(2).remove(id);
            }

            Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            method.setAccessible(true);
            method.invoke(null, customClass, name, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
