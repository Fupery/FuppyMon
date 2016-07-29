package me.Fupery.FuppyMon.Combat;

/**
 * Created by aidenhatcher on 25/07/2016.
 */
public enum Type {
    BUG,
    DARK,
    DRAGON,
    ELECTRIC,
    FAIRY,
    FIGHTING,
    FIRE,
    FLYING,
    GHOST,
    GRASS,
    GROUND,
    ICE,
    NORMAL,
    POISON,
    PSYCHIC,
    ROCK,
    STEEL,
    WATER;

    static {
        BUG.setEffectiveness(
                isWeakAgainst(FIGHTING, FLYING, GHOST)
        );
    }

    private Effectiveness[] effectiveness;

    Type(Effectiveness... effectiveness) {
        this.effectiveness = effectiveness;
    }

    private void setEffectiveness(Effectiveness... effectiveness) {
        this.effectiveness = effectiveness;
    }

    public double getMultiplierAgainst(Type type) {
        Multiplier multiplier = Multiplier.X1;
        for (Effectiveness effect : effectiveness) {
            if (effect.type == type) multiplier = effect.multiplier;
        }
        return multiplier.getMultiplier();
    }
    private static Effectiveness effectAgainst(Type type, Multiplier multiplier) {
        return new Effectiveness(type, multiplier);
    }
    private static Effectiveness[] isWeakAgainst(Type... types) {
        Effectiveness[] effects = new Effectiveness[types.length];
        for (int i = 0; i < types.length; i++) {
            effects[i] = new Effectiveness(types[i], Multiplier.X_5);
        }
        return effects;
    }

    static class Effectiveness {
        private final Type type;
        private final Multiplier multiplier;

        public Effectiveness(Type type, Multiplier multiplier) {
            this.type = type;
            this.multiplier = multiplier;
        }
    }
    private final double X0 = 0, X_5 = 0.5, X1 = 1, X2 = 2;
    private enum Multiplier {
        X0, X_5, X1, X2;

        double getMultiplier() {
            switch (this) {
                case X0:
                    return 0;
                case X_5:
                    return 0.5;
                case X2:
                    return 2;
                default:
                    return 1;
            }
        }
    }
}
