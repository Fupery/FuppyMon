package me.Fupery.FuppyMon.Entity;

import me.Fupery.FuppyMon.Entity.API.EntityFactory;
import me.Fupery.FuppyMon.Entity.Parts.EntityPart;

/**
 * Created by aidenhatcher on 26/07/2016.
 */
public class MonsterFactory extends EntityFactory {
    public MonsterFactory(MonsterAttributes attributes, EntityPart[] parts) {
        super(attributes, parts);
    }
}
