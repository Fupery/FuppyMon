package me.Fupery.FuppyMon.CustomMobs;

import me.Fupery.FuppyMon.Combat.Type;
import me.Fupery.FuppyMon.Entity.Morphology.Biped;
import me.Fupery.FuppyMon.Entity.Parts.BasicPart;
import me.Fupery.FuppyMon.Entity.Parts.StandPart;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Cubone extends Biped {

    private BasicPart head, tum;
    private StandPart top, legs;

    public Cubone() {
        super(Type.GROUND, 100, 100, 100, 100);// TODO: 26/07/2016
        head = setHead("Cubone");
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = ((LeatherArmorMeta) chest.getItemMeta());
        meta.setColor(Color.fromRGB(187, 146, 84));
        chest.setItemMeta(meta);
//        setArmItem(new ItemStack(Material.STAINED_CLAY));
//        setBody(new ItemStack(Material.HARD_CLAY, 0, (byte) 15));
        top = setUpperBody(chest);
//        setLegItem(new ItemStack(Material.HARD_CLAY));
        legs = setLowerBody(chest);
        legs.setArms(true);
        tum = setTum(new ItemStack(Material.WOOD_STEP, 0, (byte) 2));
    }
}
