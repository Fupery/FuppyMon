package me.Fupery.FuppyMon.CustomMobs;

import me.Fupery.FuppyMon.Animation.AnimationBuilder;
import me.Fupery.FuppyMon.Animation.EntityAnimation;
import me.Fupery.FuppyMon.Animation.EntityState;
import me.Fupery.FuppyMon.Entity.CustomEntities.EntityFlightlessBird;
import me.Fupery.FuppyMon.Entity.API.EntityAttributes;
import me.Fupery.FuppyMon.Entity.API.EntityFactory;
import me.Fupery.FuppyMon.Entity.Parts.BasicPart;
import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.Entity.Parts.ParticlePart;
import net.minecraft.server.v1_10_R1.EnumParticle;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public class RedstoneGolem {
    //Golem head is currently rendered with resource pack
    private BasicPart head = new BasicPart(0, 1.4, .5, 0, 0).setPlayerHead("lbdkd").setSmall(true);
    private BasicPart bod = new BasicPart(0, .6, 0, 180, 0).setItem(Material.ACACIA_FENCE_GATE);//resource pack
    private BasicPart left_shoulder = new BasicPart(.62, .6, 0, 90, 0).setItem(Material.ACACIA_STAIRS);//resource pack
    private BasicPart right_shoulder = new BasicPart(-.62, .6, 0, -90, 0).setItem(Material.ACACIA_STAIRS);//resource pack
    private ParticlePart jet = new ParticlePart(0, .5, 0).setParticle(EnumParticle.REDSTONE, .125f, 0.5f, 0.125f, 0, 10);

    private EntityAttributes attributes = new EntityAttributes(EntityFlightlessBird.class, .5f, .2f, 0.2f,
            Sound.ENTITY_MINECART_RIDING, Sound.ENTITY_IRONGOLEM_HURT, Sound.ENTITY_IRONGOLEM_DEATH);

    private EntityPart[] parts = new EntityPart[]{head, bod, left_shoulder, right_shoulder, jet};

    private final EntityFactory factory = new EntityFactory(attributes, parts);

    public RedstoneGolem() {
        factory.setUIDisplayEgg(EntityType.MAGMA_CUBE);
        factory.addAnimation(EntityState.IDLE, idle());
        factory.addAnimation(EntityState.WALK, move());
    }

    private EntityAnimation idle() {
        AnimationBuilder builder = new AnimationBuilder(parts);
        AnimationBuilder.KeyFrame frame1 = builder.newKeyFrame(10);
        frame1.setTranslation(0, 0, 0);

        AnimationBuilder.KeyFrame frame2 = builder.newKeyFrame(10);
        frame2.setTranslation(0, -.3, 0);
        return builder.buildAnimation();
    }

    private EntityAnimation move() {
        AnimationBuilder builder = new AnimationBuilder(parts);
        AnimationBuilder.KeyFrame frame = builder.newKeyFrame(1);
        return builder.buildAnimation();
    }

    public EntityFactory getFactory() {
        return factory;
    }
}
