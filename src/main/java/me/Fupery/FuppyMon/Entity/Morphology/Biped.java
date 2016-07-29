package me.Fupery.FuppyMon.Entity.Morphology;

import me.Fupery.FuppyMon.Animation.AnimationBuilder;
import me.Fupery.FuppyMon.Animation.EntityAnimation;
import me.Fupery.FuppyMon.Combat.Type;
import me.Fupery.FuppyMon.Entity.CustomEntities.EntityBiped;
import me.Fupery.FuppyMon.Entity.Parts.BasicPart;
import me.Fupery.FuppyMon.Entity.Parts.EntityPart;
import me.Fupery.FuppyMon.Entity.Parts.StandPart;
import me.Fupery.FuppyMon.Pose.ArmourStandLimb;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class Biped extends Morphology {

    protected EntityPart head, tum;
    protected StandPart upperBody, lowerBody;

    public Biped(Type type, int baseHealth, int baseDamage, int baseAccuracy, int baseEvasiveness) {
        super(type, baseHealth, baseDamage, baseAccuracy, baseEvasiveness);
        setEntityType(EntityBiped.class);
    }

    @Override
    protected EntityPart[] getParts() {
        return new EntityPart[]{head, tum, upperBody, lowerBody};
    }

    public BasicPart setHead(String headName) {
        BasicPart head = new BasicPart(0, -.65, 0, 0, 0).setPlayerHead(headName);
        this.head = head;
        return head;
    }

    public StandPart setUpperBody(ItemStack chest) {
        StandPart part = new StandPart(0, -.1, .20, 180, 0).setSmall(true);
        part.setChest(chest);
        this.upperBody = part;
        return part;
    }

    public BasicPart setTum(ItemStack item) {
        BasicPart tum = new BasicPart(0, -.290, .03, 0, 0).setItem(item).setSmall(true);
        this.tum = tum;
        return tum;
    }

    public StandPart setLowerBody(ItemStack chest) {
        StandPart part = new StandPart(0, -1.3, -.04, 0, 0);
        part.setChest(chest);
        this.lowerBody = part;
        return part;
    }

    @Override
    protected EntityAnimation getIdleAnimation() {
        AnimationBuilder builder = new AnimationBuilder(getParts());
        AnimationBuilder.KeyFrame frame = builder.newKeyFrame(1);
        frame.setPartRotation(upperBody, ArmourStandLimb.BODY, -90, 0, 0);
        frame.setPartRotation(upperBody, ArmourStandLimb.LEFT_ARM, 60, 0, 30);
        frame.setPartRotation(upperBody, ArmourStandLimb.RIGHT_ARM, 60, 0, -30);

        frame.setPartRotation(lowerBody, ArmourStandLimb.BODY, 180, 180, 0);
        frame.setPartRotation(lowerBody, ArmourStandLimb.LEFT_ARM, 0, 180, 0);
        frame.setPartRotation(lowerBody, ArmourStandLimb.RIGHT_ARM, 0, 180, 0);

        frame.setRotation(tum, 90, 0, 0);
        return builder.buildAnimation();
    }

    @Override
    protected EntityAnimation getWalkAnimation() {
        AnimationBuilder builder = new AnimationBuilder(getParts());
        {//frame 1
            AnimationBuilder.KeyFrame frame = builder.newKeyFrame(4);
            frame.setPartRotation(upperBody, ArmourStandLimb.BODY, -90, 0, 0);
            frame.setPartRotation(upperBody, ArmourStandLimb.LEFT_ARM, 60, 0, 30);
            frame.setPartRotation(upperBody, ArmourStandLimb.RIGHT_ARM, 60, 0, -30);

            frame.setPartRotation(lowerBody, ArmourStandLimb.BODY, 180, 180, 0);
            frame.setPartRotation(lowerBody, ArmourStandLimb.LEFT_ARM, 60, 180, 0);
            frame.setPartRotation(lowerBody, ArmourStandLimb.RIGHT_ARM, -60, 180, 0);

            frame.setRotation(tum, 90, 0, 0);
        }
        {//frame 2
            AnimationBuilder.KeyFrame frame = builder.newKeyFrame(4);
            frame.setPartRotation(upperBody, ArmourStandLimb.BODY, -90, 0, 0);
            frame.setPartRotation(upperBody, ArmourStandLimb.LEFT_ARM, 60, 0, 30);
            frame.setPartRotation(upperBody, ArmourStandLimb.RIGHT_ARM, 60, 0, -30);

            frame.setPartRotation(lowerBody, ArmourStandLimb.BODY, 180, 180, 0 );
            frame.setPartRotation(lowerBody, ArmourStandLimb.LEFT_ARM, -60, 180, 0);
            frame.setPartRotation(lowerBody, ArmourStandLimb.RIGHT_ARM, 60, 180, 0);

            frame.setRotation(tum, 90, 0, 0);
        }
        return builder.buildAnimation();
    }

    @Override
    protected EntityAnimation getFallAnimation() {
        AnimationBuilder builder = new AnimationBuilder(getParts());
        AnimationBuilder.KeyFrame frame = builder.newKeyFrame(1);
        frame.setPartRotation(upperBody, ArmourStandLimb.BODY, -90, 0, 0);
        frame.setPartRotation(upperBody, ArmourStandLimb.LEFT_ARM, 90, 90, 20);
        frame.setPartRotation(upperBody, ArmourStandLimb.RIGHT_ARM, -90, -90, -20);

        frame.setPartRotation(lowerBody, ArmourStandLimb.BODY, 180, 180, 0);
        frame.setPartRotation(lowerBody, ArmourStandLimb.LEFT_ARM, 0, 180, 0);
        frame.setPartRotation(lowerBody, ArmourStandLimb.RIGHT_ARM, 0, 180, 0);

        frame.setRotation(tum, 90, 0, 0);
        return builder.buildAnimation();
    }
}
