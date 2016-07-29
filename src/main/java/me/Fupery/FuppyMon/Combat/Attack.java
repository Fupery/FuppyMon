package me.Fupery.FuppyMon.Combat;

import me.Fupery.FuppyMon.Entity.Monster;

public abstract class Attack {
    protected final int power;
    protected final int pp;
    protected final Type type;
    protected final int accuracy;
    protected final Effect[] effects;

    public Attack(int power, int pp, Type type, int accuracy, Effect... effects) {
        this.power = power;
        this.pp = pp;
        this.type = type;
        this.accuracy = accuracy;
        this.effects = effects;
    }

    /**
     * @param attacker The monster that is attacking
     * @param defender The monster that is being attacked
     * @return true if the attack is successful, false if it misses
     */
    public boolean attempt(Monster attacker, Monster defender) {
        //check miss
        double hitProbability = (attacker.getAccuracy() / 100) * (accuracy / defender.getEvasiveness());
        double attackRoll = attacker.getAttackRoll();
        if (attackRoll < hitProbability) {
            // TODO: 26/07/2016 SUCCESS
            //apply damage
            //attacker perform attack
            //attack hit particles
            //defender perform hurt
            return true;
        } else {
            // TODO: 26/07/2016 miss
            //attacker perform attack
            //defender perform dodge
            return false;
        }
    }
}
