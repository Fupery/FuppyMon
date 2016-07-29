package me.Fupery.FuppyMon.CustomMobs;

import me.Fupery.FuppyMon.Animation.AnimationBuilder;
import me.Fupery.FuppyMon.Animation.EntityAnimation;
import me.Fupery.FuppyMon.Animation.EntityState;
import me.Fupery.FuppyMon.Entity.CustomEntities.EntityFlightlessBird;
import me.Fupery.FuppyMon.Entity.API.EntityAttributes;
import me.Fupery.FuppyMon.Entity.API.EntityFactory;
import me.Fupery.FuppyMon.Entity.Parts.BasicPart;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public class TestMob {
    private BasicPart left_wing = new BasicPart(.6, -1.1, 0, 90, 0).setItem(Material.CARPET, (short) 6);
    private BasicPart right_wing = new BasicPart(-.6, -1.1, 0, -90, 0).setItem(Material.CARPET, (short) 6);

    private EntityAttributes attributes = new EntityAttributes(
            EntityFlightlessBird.class,
            .5f, .2f, .25d,
            Sound.ENTITY_BAT_AMBIENT, Sound.ENTITY_BAT_HURT, Sound.ENTITY_BAT_DEATH);

    private BasicPart[] parts = new BasicPart[]{left_wing, right_wing};

    private final EntityFactory factory = new EntityFactory(attributes, parts);

    public TestMob() {
        factory.setUIDisplayEgg(EntityType.VILLAGER);
        factory.addAnimation(EntityState.IDLE, flyingAnimation());
//        factory.addAnimation(EntityState.FALL, flyingAnimation());
//        factory.addAnimation(EntityState.SWIM, flyingAnimation());
    }

    public EntityFactory getFactory() {
        return factory;
    }

    private EntityAnimation idleAnimation() {
        AnimationBuilder builder = new AnimationBuilder(parts);
        AnimationBuilder.KeyFrame frame = builder.newKeyFrame(1);
        frame.setRotation(left_wing, 93, 180, 195);
        frame.setRotation(right_wing, 93, 180, 165);
        frame.setTranslation(left_wing, -.2, -.15, 0);
        frame.setTranslation(right_wing, .2, -.15, 0);
        return builder.buildAnimation();
    }

    private EntityAnimation flyingAnimation() {
        AnimationBuilder builder = new AnimationBuilder(parts);

        AnimationBuilder.KeyFrame frame1 = builder.newKeyFrame(4);
        frame1.setRotation(left_wing, 140, 0, 0);
        frame1.setRotation(right_wing, 140, 0, 0);
        frame1.setTranslation(left_wing, -.2, -.15, 0);
        frame1.setTranslation(right_wing, .2, -.15, 0);

        AnimationBuilder.KeyFrame frame2 = builder.newKeyFrame(4);
        frame2.setRotation(left_wing, 160, 0, 0);
        frame2.setRotation(right_wing, 160, 0, 0);
        frame2.setTranslation(left_wing, -.2, 0.5, 0);
        frame2.setTranslation(right_wing, .2, 0.5, 0);
        return builder.buildAnimation();
    }
}
